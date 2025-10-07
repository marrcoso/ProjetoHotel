package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Quarto;
import service.QuartoService;
import view.TelaBuscaQuarto;

public final class ControllerBuscaQuarto implements ActionListener, InterfaceControllerBusca<Quarto> {

    private final TelaBuscaQuarto telaBuscaQuarto;
    private final QuartoService quartoService;
    private final Consumer<Integer> atualizaCodigo;
    

    public ControllerBuscaQuarto(TelaBuscaQuarto telaBuscaQuarto, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaQuarto = telaBuscaQuarto;
        this.quartoService = new QuartoService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaQuarto.getjButtonCarregar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonFiltar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonSair().addActionListener(this);
        this.telaBuscaQuarto.getjButtonAtivar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonInativar().addActionListener(this);
        this.telaBuscaQuarto.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaQuarto.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaQuarto.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaQuarto.getjTableDados().getValueAt(row, 8); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaQuarto.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaQuarto.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaQuarto.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaQuarto.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaQuarto.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaQuarto.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaQuarto.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaQuarto.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaQuarto.getjTableDados()
                .getValueAt(telaBuscaQuarto.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaQuarto.dispose();
        }
    }

    private enum FiltroQuarto {
        ID, DESCRICAO, CAPACIDADE_HOSPEDES, METRAGEM, IDENTIFICACAO, ANDAR, OBS;

        public static FiltroQuarto fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                case 2: return CAPACIDADE_HOSPEDES;
                case 3: return METRAGEM;
                case 4: return IDENTIFICACAO;
                case 5: return ANDAR;
                case 6: return OBS;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Quarto quarto) {
        tabela.addRow(new Object[]{
            quarto.getId(), quarto.getDescricao(), quarto.getCapacidadeHospedes(),
            quarto.getMetragem(), quarto.getIdentificacao(), quarto.getAndar(),
            quarto.isFlagAnimais(), quarto.getObs(), quarto.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<Quarto> listaQuartos = quartoService.Carregar(atributo, valor);
        for (Quarto q : listaQuartos) {
            adicionarLinhaTabela(tabela, q);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaQuarto.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaQuarto.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaQuarto.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaQuarto.getjTFFiltro().getText();

        FiltroQuarto filtro = FiltroQuarto.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Quarto quarto = quartoService.Carregar(Integer.parseInt(filtroTexto));
                    if (quarto != null) {
                        adicionarLinhaTabela(tabela, quarto);
                    }
                    break;
                }
                case DESCRICAO: {
                    carregarPorAtributo("descricao", filtroTexto, tabela);
                    break;
                }
                case CAPACIDADE_HOSPEDES: {
                    carregarPorAtributo("capacidade_hospedes", filtroTexto, tabela);
                    break;
                }
                case METRAGEM: {
                    carregarPorAtributo("metragem", filtroTexto, tabela);
                    break;
                }
                case IDENTIFICACAO: {
                    carregarPorAtributo("identificacao", filtroTexto, tabela);
                    break;
                }
                case ANDAR: {
                    carregarPorAtributo("andar", filtroTexto, tabela);
                    break;
                }
                case OBS: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaQuarto.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaQuarto.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaQuarto.getjTableDados()
            .getValueAt(telaBuscaQuarto.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaQuarto.getjTableDados()
            .getValueAt(telaBuscaQuarto.getjTableDados().getSelectedRow(), 8);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Quarto já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            quartoService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaQuarto.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaQuarto.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 8);
            telaBuscaQuarto.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaQuarto.getjButtonInativar().setEnabled(ativar);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
