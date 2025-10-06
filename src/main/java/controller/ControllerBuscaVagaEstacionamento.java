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
        ID, DESCRICAO;

        public static FiltroVaga fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
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
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaVaga, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaVaga.dispose();
    }
}
