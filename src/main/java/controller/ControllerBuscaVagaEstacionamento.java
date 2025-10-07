package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.VagaEstacionamento;
import service.VagaEstacionamentoService;
import view.TelaBuscaVaga;

public final class ControllerBuscaVagaEstacionamento implements ActionListener, InterfaceControllerBusca<VagaEstacionamento> {

    private final TelaBuscaVaga telaBuscaVaga;
    private final VagaEstacionamentoService vagaService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaVagaEstacionamento(TelaBuscaVaga telaBuscaVaga, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaVaga = telaBuscaVaga;
        this.vagaService = new VagaEstacionamentoService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaVaga.getjButtonCarregar().addActionListener(this);
        this.telaBuscaVaga.getjButtonFiltar().addActionListener(this);
        this.telaBuscaVaga.getjButtonSair().addActionListener(this);
        this.telaBuscaVaga.getjButtonAtivar().addActionListener(this);
        this.telaBuscaVaga.getjButtonInativar().addActionListener(this);
        this.telaBuscaVaga.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaVaga.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaVaga.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaVaga.getjTableDados().getValueAt(row, 4); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaVaga.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaVaga.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaVaga.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaVaga.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaVaga.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaVaga.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaVaga.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaVaga.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaVaga.getjTableDados()
                .getValueAt(telaBuscaVaga.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaVaga.dispose();
        }
    }

    private enum FiltroVaga {
        ID, DESCRICAO, OBSERVACAO, METRAGEM;

        public static FiltroVaga fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                case 2: return OBSERVACAO;
                case 3: return METRAGEM;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, VagaEstacionamento vaga) {
        tabela.addRow(new Object[]{
            vaga.getId(),
            vaga.getDescricao(),
            vaga.getObs(),
            vaga.getMetragemVaga(),
            vaga.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<VagaEstacionamento> listaVagas = vagaService.Carregar(atributo, valor);
        for (VagaEstacionamento v : listaVagas) {
            adicionarLinhaTabela(tabela, v);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaVaga.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaVaga.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaVaga.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaVaga.getjTFFiltro().getText();

        FiltroVaga filtro = FiltroVaga.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    VagaEstacionamento vaga = vagaService.Carregar(Integer.parseInt(filtroTexto));
                    if (vaga != null) {
                        adicionarLinhaTabela(tabela, vaga);
                    }
                    break;
                }
                case DESCRICAO: {
                    carregarPorAtributo("descricao", filtroTexto, tabela);
                    break;
                }
                case OBSERVACAO: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
                case METRAGEM: {
                    carregarPorAtributo("metragem_vaga", filtroTexto, tabela);
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaVaga, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaVaga.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaVaga.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaVaga.getjTableDados()
            .getValueAt(telaBuscaVaga.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaVaga.getjTableDados()
            .getValueAt(telaBuscaVaga.getjTableDados().getSelectedRow(), 4);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("A Vaga já está %s.", ativar ? "Ativa" : "Inativa"));
                return;
            }

            vagaService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaVaga.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaVaga.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 4);
            telaBuscaVaga.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaVaga.getjButtonInativar().setEnabled(ativar);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaVaga, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
