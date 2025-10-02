package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Hospede;
import service.HospedeService;
import view.TelaBuscaHospede;

public class ControllerBuscaHospede implements ActionListener, InterfaceControllerBusca {

    private final TelaBuscaHospede telaBuscaHospede;
    private final HospedeService hospedeService;

    public ControllerBuscaHospede(TelaBuscaHospede telaBuscaHospede) {
        this.telaBuscaHospede = telaBuscaHospede;
        this.hospedeService = new HospedeService();
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaHospede.getjButtonCarregar().addActionListener(this);
        this.telaBuscaHospede.getjButtonFiltar().addActionListener(this);
        this.telaBuscaHospede.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaHospede.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaHospede.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaHospede.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaHospede.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadHospede.codigo = (int) telaBuscaHospede.getjTableDados()
                .getValueAt(telaBuscaHospede.getjTableDados().getSelectedRow(), 0);
            telaBuscaHospede.dispose();
        }
    }

    private enum FiltroHospede {
        ID, NOME, CPF;

        public static FiltroHospede fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CPF;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaHospede.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaHospede.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaHospede.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaHospede.getjTFFiltro().getText();

        FiltroHospede filtro = FiltroHospede.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID:
                    Hospede hospede = hospedeService.Carregar(Integer.parseInt(filtroTexto));
                    if (hospede != null) {
                        tabela.addRow(new Object[]{hospede.getId(), hospede.getNome(), hospede.getCpf(), hospede.getStatus()});
                    }
                    break;
                case NOME:
                    List<Hospede> listaPorNome = hospedeService.Carregar("nome", filtroTexto);
                    for (Hospede h : listaPorNome) {
                        tabela.addRow(new Object[]{h.getId(), h.getNome(), h.getCpf(), h.getStatus()});
                    }
                    break;
                case CPF:
                    List<Hospede> listaPorCpf = hospedeService.Carregar("cpf", filtroTexto);
                    for (Hospede h : listaPorCpf) {
                        tabela.addRow(new Object[]{h.getId(), h.getNome(), h.getCpf(), h.getStatus()});
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaHospede, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        telaBuscaHospede.dispose();
    }
}
