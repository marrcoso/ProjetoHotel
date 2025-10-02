package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Servico;
import service.ServicoService;
import view.TelaBuscaServico;

public class ControllerBuscaServico implements ActionListener, InterfaceControllerBusca {

    private final TelaBuscaServico telaBuscaServico;
    private final ServicoService servicoService;

    public ControllerBuscaServico(TelaBuscaServico telaBuscaServico) {
        this.telaBuscaServico = telaBuscaServico;
        this.servicoService = new ServicoService();
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaServico.getjButtonCarregar().addActionListener(this);
        this.telaBuscaServico.getjButtonFiltar().addActionListener(this);
        this.telaBuscaServico.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaServico.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaServico.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaServico.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaServico.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadServico.codigo = (int) telaBuscaServico.getjTableDados()
                .getValueAt(telaBuscaServico.getjTableDados().getSelectedRow(), 0);
            telaBuscaServico.dispose();
        }
    }

    private enum FiltroServico {
        ID, DESCRICAO;

        public static FiltroServico fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaServico.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaServico.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaServico.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaServico.getjTFFiltro().getText();

        FiltroServico filtro = FiltroServico.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID:
                    Servico servico = servicoService.Carregar(Integer.parseInt(filtroTexto));
                    if (servico != null) {
                        tabela.addRow(new Object[]{servico.getId(), servico.getDescricao(), servico.getObs(), servico.getStatus()});
                    }
                    break;
                case DESCRICAO:
                    List<Servico> listaPorDescricao = servicoService.Carregar("descricao", filtroTexto);
                    for (Servico s : listaPorDescricao) {
                        tabela.addRow(new Object[]{s.getId(), s.getDescricao(), s.getObs(), s.getStatus()});
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        this.telaBuscaServico.dispose();
    }
}
