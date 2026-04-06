package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.CopaQuarto;
import service.CopaQuartoService;
import view.TelaBuscaCopaQuarto;

public final class ControllerBuscaCopaQuarto implements ActionListener, InterfaceControllerBusca<CopaQuarto> {

    private final TelaBuscaCopaQuarto telaBusca;
    private final CopaQuartoService service;
    private final Consumer<Integer> callback;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ControllerBuscaCopaQuarto(TelaBuscaCopaQuarto telaBusca, Consumer<Integer> callback) {
        this.telaBusca = telaBusca;
        this.service = new CopaQuartoService();
        this.callback = callback;
        initListeners();
        carregarAbertos();
    }

    private enum FiltroCopa {
        ID, OBS, STATUS;

        public static FiltroCopa fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return OBS;
                case 2: return STATUS;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void initListeners() {
        this.telaBusca.getjButtonCarregar().addActionListener(this);
        this.telaBusca.getjButtonFiltar().addActionListener(this);
        this.telaBusca.getjButtonAvancarStatus().addActionListener(this);
        this.telaBusca.getjButtonCancelarPedido().addActionListener(this);
        this.telaBusca.getjButtonSair().addActionListener(this);

        this.telaBusca.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBusca.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void carregarAbertos() {
        DefaultTableModel model = (DefaultTableModel) telaBusca.getjTableDados().getModel();
        model.setRowCount(0);
        
        // Carrega Pendentes
        List<CopaQuarto> pendentes = service.Carregar("status", "P");
        for (CopaQuarto c : pendentes) {
            adicionarLinhaTabela(model, c);
        }
        
        // Carrega Em Processo
        List<CopaQuarto> emProcesso = service.Carregar("status", "E");
        for (CopaQuarto c : emProcesso) {
            adicionarLinhaTabela(model, c);
        }
    }

    private void handleSelecionarItem() {
        int row = telaBusca.getjTableDados().getSelectedRow();
        if (row == -1) return;
        
        String statusStr = (String) telaBusca.getjTableDados().getValueAt(row, 7);
        
        // P -> E -> F
        // C is terminal
        boolean canAvancar = statusStr.equals("Pendente") || statusStr.equals("Em Processo");
        // Permitir anular até mesmo os Finalizados
        boolean canCancelar = !statusStr.equals("Cancelado");
        
        telaBusca.getjButtonAvancarStatus().setEnabled(canAvancar);
        telaBusca.getjButtonCancelarPedido().setEnabled(canCancelar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == telaBusca.getjButtonCarregar()) handleCarregar();
        else if (source == telaBusca.getjButtonFiltar()) handleFiltrar();
        else if (source == telaBusca.getjButtonAvancarStatus()) handleStatusAvancar();
        else if (source == telaBusca.getjButtonCancelarPedido()) handleStatusCancelar();
        else if (source == telaBusca.getjButtonSair()) handleSair();
    }

    private void handleStatusAvancar() {
        int row = telaBusca.getjTableDados().getSelectedRow();
        if (row == -1) return;
        
        int id = (int) telaBusca.getjTableDados().getValueAt(row, 0);
        String statusAtual = (String) telaBusca.getjTableDados().getValueAt(row, 7);
        char novoStatus = ' ';
        
        if (statusAtual.equals("Pendente")) novoStatus = 'E';
        else if (statusAtual.equals("Em Processo")) novoStatus = 'F';
        
        if (novoStatus != ' ') {
            if (JOptionPane.showConfirmDialog(telaBusca, 
                    "Deseja avançar o status deste pedido?", "Confirmação", 
                    JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            service.mudarStatus(id, novoStatus);
            handleCarregar();
        }
    }

    private void handleStatusCancelar() {
        int row = telaBusca.getjTableDados().getSelectedRow();
        if (row == -1) return;
        
        if (JOptionPane.showConfirmDialog(telaBusca, 
                "Deseja realmente CANCELAR este pedido?", "Aviso", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) telaBusca.getjTableDados().getValueAt(row, 0);
        service.mudarStatus(id, 'C');
        handleCarregar();
    }

    @Override
    public void handleCarregar() {
        int row = telaBusca.getjTableDados().getSelectedRow();
        if (row != -1) {
            int id = (int) telaBusca.getjTableDados().getValueAt(row, 0);
            callback.accept(id);
            telaBusca.dispose();
        } else {
            JOptionPane.showMessageDialog(telaBusca, "Selecione um registro!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void handleFiltrar() {
        DefaultTableModel model = (DefaultTableModel) telaBusca.getjTableDados().getModel();
        model.setRowCount(0);

        String valor = telaBusca.getjTFFiltro().getText().trim();
        if (valor.isEmpty()) {
             List<CopaQuarto> todos = service.Carregar("", "");
             for (CopaQuarto c : todos) adicionarLinhaTabela(model, c);
             return;
        }

        try {
            FiltroCopa filtro = FiltroCopa.fromIndex(telaBusca.getjCBFiltro().getSelectedIndex());
            switch (filtro) {
                case ID: {
                    CopaQuarto copa = service.Carregar(Integer.parseInt(valor));
                    if (copa != null) {
                        adicionarLinhaTabela(model, copa);
                    }
                    break;
                }
                case OBS: {
                    carregarPorAtributo("obs", valor, model);
                    break;
                }
                case STATUS: {
                    carregarPorAtributo("status", valor.substring(0, 1).toUpperCase(), model);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaBusca, "Erro ao filtrar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBusca.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean colocarPendente) {
        if (!colocarPendente) handleStatusCancelar();
    }
    public void adicionarLinhaTabela(DefaultTableModel tabela, CopaQuarto item) {
        String statusExtenso;
        switch (item.getStatus()) {
            case 'P': statusExtenso = "Pendente"; break;
            case 'E': statusExtenso = "Em Processo"; break;
            case 'F': statusExtenso = "Finalizado"; break;
            case 'C': statusExtenso = "Cancelado"; break;
            default: statusExtenso = "Desconhecido";
        }

        float total = item.getQuantidade() * item.getValorUnitario();

        tabela.addRow(new Object[]{
            item.getId(),
            sdf.format(item.getDataHoraPedido()),
            item.getCheckQuarto().getQuarto().getIdentificacao(),
            item.getQuantidade(),
            item.getProduto().getDescricao(),
            String.format("%.2f", total),
            item.getObs(),
            statusExtenso
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<CopaQuarto> resultados = service.Carregar(atributo, valor);
        for (CopaQuarto c : resultados) {
            adicionarLinhaTabela(tabela, c);
        }
    }
}
