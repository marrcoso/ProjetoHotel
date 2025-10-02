package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.VagaEstacionamento;
import service.VagaEstacionamentoService;
import view.TelaBuscaVaga;

public class ControllerBuscaVagaEstacionamento implements ActionListener, InterfaceControllerBusca {

    private final TelaBuscaVaga telaBuscaVaga;
    private final VagaEstacionamentoService vagaService;

    public ControllerBuscaVagaEstacionamento(TelaBuscaVaga telaBuscaVaga) {
        this.telaBuscaVaga = telaBuscaVaga;
        this.vagaService = new VagaEstacionamentoService();
        initListeners();
    }

    private void initListeners() {
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

    private void handleCarregar() {
        if (telaBuscaVaga.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadVagaEstacionamento.codigo = (int) telaBuscaVaga.getjTableDados()
                .getValueAt(telaBuscaVaga.getjTableDados().getSelectedRow(), 0);
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

    private void handleFiltrar() {
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
                case ID:
                    VagaEstacionamento vaga = vagaService.Carregar(Integer.parseInt(filtroTexto));
                    if (vaga != null) {
                        tabela.addRow(new Object[]{vaga.getId(), vaga.getDescricao(), vaga.getObs(), vaga.getMetragemVaga(), vaga.getStatus()});
                    }
                    break;
                case DESCRICAO:
                    List<VagaEstacionamento> listaPorDescricao = vagaService.Carregar("descricao", filtroTexto);
                    for (VagaEstacionamento v : listaPorDescricao) {
                        tabela.addRow(new Object[]{v.getId(), v.getDescricao(), v.getObs(), v.getMetragemVaga(), v.getStatus()});
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaVaga, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        telaBuscaVaga.dispose();
    }
}
