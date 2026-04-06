package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.OrdemServico;
import service.OrdemServicoService;
import view.TelaBuscaOrdemServico;

public final class ControllerBuscaOrdemServico implements ActionListener, InterfaceControllerBusca<OrdemServico> {

    private final TelaBuscaOrdemServico telaBusca;
    private final OrdemServicoService service;
    private final Consumer<Integer> callback;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ControllerBuscaOrdemServico(TelaBuscaOrdemServico telaBusca, Consumer<Integer> callback) {
        this.telaBusca = telaBusca;
        this.service = new OrdemServicoService();
        this.callback = callback;
        initListeners();
        carregarAbertos();
    }

    private enum FiltroOS {
        ID, OBS, STATUS;

        public static FiltroOS fromIndex(int index) {
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
        
        List<OrdemServico> pendentes = service.Carregar("status", "P");
        for (OrdemServico o : pendentes) adicionarLinhaTabela(model, o);
        
        List<OrdemServico> emProcesso = service.Carregar("status", "E");
        for (OrdemServico o : emProcesso) adicionarLinhaTabela(model, o);
    }

    private void handleSelecionarItem() {
        int row = telaBusca.getjTableDados().getSelectedRow();
        if (row == -1) return;
        
        String statusStr = (String) telaBusca.getjTableDados().getValueAt(row, 7);
        
        boolean canAvancar = statusStr.equals("Pendente") || statusStr.equals("Em Processo");
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
                    "Deseja avançar o status desta OS?", "Confirmação", 
                    JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            service.mudarStatus(id, novoStatus);
            handleFiltrar(); // Refresh with current filters
        }
    }

    private void handleStatusCancelar() {
        int row = telaBusca.getjTableDados().getSelectedRow();
        if (row == -1) return;
        
        if (JOptionPane.showConfirmDialog(telaBusca, 
                "Deseja realmente CANCELAR esta OS?", "Aviso", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) telaBusca.getjTableDados().getValueAt(row, 0);
        service.mudarStatus(id, 'C');
        handleFiltrar();
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
             List<OrdemServico> todos = service.Listar();
             for (OrdemServico o : todos) adicionarLinhaTabela(model, o);
             return;
        }

        try {
            FiltroOS filtro = FiltroOS.fromIndex(telaBusca.getjCBFiltro().getSelectedIndex());
            switch (filtro) {
                case ID: {
                    OrdemServico o = service.Carregar(Integer.parseInt(valor));
                    if (o != null) adicionarLinhaTabela(model, o);
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

    public void adicionarLinhaTabela(DefaultTableModel tabela, OrdemServico item) {
        String statusExtenso;
        switch (item.getStatus()) {
            case 'P': statusExtenso = "Pendente"; break;
            case 'E': statusExtenso = "Em Processo"; break;
            case 'F': statusExtenso = "Finalizado"; break;
            case 'C': statusExtenso = "Cancelado"; break;
            default: statusExtenso = "Desconhecido";
        }

        tabela.addRow(new Object[]{
            item.getId(),
            sdf.format(item.getDataHoraCadastro()),
            (item.getCheck() != null ? item.getCheck().getId() : "N/A"),
            (item.getServico() != null ? item.getServico().getDescricao() : "N/A"),
            (item.getQuarto() != null ? item.getQuarto().getIdentificacao() : "-"),
            sdf.format(item.getDataHoraPrevistaInicio()),
            sdf.format(item.getDataHoraPrevistaTermino()),
            statusExtenso
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<OrdemServico> resultados = service.Carregar(atributo, valor);
        for (OrdemServico o : resultados) {
            adicionarLinhaTabela(tabela, o);
        }
    }
}
