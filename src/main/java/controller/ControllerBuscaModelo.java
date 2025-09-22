package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Modelo;
import view.TelaBuscaModelo;

public class ControllerBuscaModelo implements ActionListener {

    TelaBuscaModelo telaBuscaModelo;

    public ControllerBuscaModelo(TelaBuscaModelo telaBuscaModelo) {
        this.telaBuscaModelo = telaBuscaModelo;
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaModelo.getjButtonCarregar().addActionListener(this);
        this.telaBuscaModelo.getjButtonFiltar().addActionListener(this);
        this.telaBuscaModelo.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaModelo.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaModelo.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaModelo.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaModelo.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadModelo.codigo = (int) telaBuscaModelo.getjTableDados()
                .getValueAt(telaBuscaModelo.getjTableDados().getSelectedRow(), 0);
            telaBuscaModelo.dispose();
        }
    }

    private enum FiltroModelo {
        ID, DESCRICAO, MARCA;

        public static FiltroModelo fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                case 2: return MARCA;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaModelo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaModelo.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaModelo.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaModelo.getjTFFiltro().getText();

        FiltroModelo filtro = FiltroModelo.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                Modelo modelo = service.ModeloService.Carregar(Integer.parseInt(filtroTexto));
                if (modelo != null) {
                    tabela.addRow(new Object[]{modelo.getId(), modelo.getDescricao(), modelo.getStatus()});
                }
                break;
            case DESCRICAO:
                List<Modelo> listaPorDescricao = service.ModeloService.Carregar("descricao", filtroTexto);
                for (Modelo m : listaPorDescricao) {
                    tabela.addRow(new Object[]{m.getId(), m.getDescricao(), m.getStatus()});
                }
                break;
            case MARCA:
                List<Modelo> listaPorMarca = service.ModeloService.Carregar("marca", filtroTexto);
                for (Modelo m : listaPorMarca) {
                    tabela.addRow(new Object[]{m.getId(), m.getDescricao(), m.getStatus()});
                }
                break;
        }
    }

    private void handleSair() {
        telaBuscaModelo.dispose();
    }
}
