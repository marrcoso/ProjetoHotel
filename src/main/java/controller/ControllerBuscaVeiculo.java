package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Veiculo;
import view.TelaBuscaVeiculo;

public class ControllerBuscaVeiculo implements ActionListener {

    TelaBuscaVeiculo telaBuscaVeiculo;

    public ControllerBuscaVeiculo(TelaBuscaVeiculo telaBuscaVeiculo) {
        this.telaBuscaVeiculo = telaBuscaVeiculo;
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
        ID, PLACA, COR, MODELO;

        public static FiltroVeiculo fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return PLACA;
                case 2: return COR;
                case 3: return MODELO;
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

        switch (filtro) {
            case ID:
                Veiculo veiculo = service.VeiculoService.Carregar(Integer.parseInt(filtroTexto));
                if (veiculo != null) {
                    tabela.addRow(new Object[]{veiculo.getId(), veiculo.getPlaca(), veiculo.getCor(), veiculo.getModelo().getDescricao()});
                }
                break;
            case PLACA:
                List<Veiculo> listaPorPlaca = service.VeiculoService.Carregar("placa", filtroTexto);
                for (Veiculo v : listaPorPlaca) {
                    tabela.addRow(new Object[]{v.getId(), v.getPlaca(), v.getCor(), v.getModelo().getDescricao()});
                }
                break;
            case COR:
                List<Veiculo> listaPorCor = service.VeiculoService.Carregar("cor", filtroTexto);
                for (Veiculo v : listaPorCor) {
                    tabela.addRow(new Object[]{v.getId(), v.getPlaca(), v.getCor(), v.getModelo().getDescricao()});
                }
                break;
            case MODELO:
                List<Veiculo> listaPorModelo = service.VeiculoService.Carregar("modelo_id", filtroTexto);
                for (Veiculo v : listaPorModelo) {
                    tabela.addRow(new Object[]{v.getId(), v.getPlaca(), v.getCor(), v.getModelo().getDescricao()});
                }
                break;
        }
    }

    private void handleSair() {
        telaBuscaVeiculo.dispose();
    }
}
