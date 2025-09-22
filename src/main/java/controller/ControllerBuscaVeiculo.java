package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Veiculo;
import service.VeiculoService;
import view.TelaBuscaVeiculo;

public class ControllerBuscaVeiculo implements ActionListener, InterfaceControllerBusca {

    TelaBuscaVeiculo telaBuscaVeiculo;
    private final VeiculoService veiculoService;

    public ControllerBuscaVeiculo(TelaBuscaVeiculo telaBuscaVeiculo) {
        this.telaBuscaVeiculo = telaBuscaVeiculo;
        this.veiculoService = new VeiculoService();
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaVeiculo.getjButtonCarregar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonFiltar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaVeiculo.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaVeiculo.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaVeiculo.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaVeiculo.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadVeiculo.codigo = (int) telaBuscaVeiculo.getjTableDados()
                .getValueAt(telaBuscaVeiculo.getjTableDados().getSelectedRow(), 0);
            telaBuscaVeiculo.dispose();
        }
    }

    private enum FiltroVeiculo {
        ID, PLACA;

        public static FiltroVeiculo fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return PLACA;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaVeiculo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaVeiculo.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaVeiculo.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaVeiculo.getjTFFiltro().getText();

        FiltroVeiculo filtro = FiltroVeiculo.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID:
                    Veiculo veiculo = veiculoService.Carregar(Integer.parseInt(filtroTexto));
                    if (veiculo != null) {
                        tabela.addRow(new Object[]{veiculo.getId(), veiculo.getPlaca(), veiculo.getCor(), veiculo.getModelo(), veiculo.getStatus()});
                    }
                    break;
                case PLACA:
                    List<Veiculo> listaPorPlaca = veiculoService.Carregar("placa", filtroTexto);
                    for (Veiculo v : listaPorPlaca) {
                        tabela.addRow(new Object[]{v.getId(), v.getPlaca(), v.getCor(), v.getModelo(), v.getStatus()});
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        telaBuscaVeiculo.dispose();
    }
}
