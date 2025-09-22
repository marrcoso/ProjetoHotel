package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;

public class ControllerBuscaMarca implements ActionListener, InterfaceControllerBusca {

    TelaBuscaMarca telaBuscaMarca;
    private final MarcaService marcaService;

    public ControllerBuscaMarca(TelaBuscaMarca telaBuscaMarca) {
        this.telaBuscaMarca = telaBuscaMarca;
        this.marcaService = new MarcaService();
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaMarca.getjButtonCarregar().addActionListener(this);
        this.telaBuscaMarca.getjButtonFiltar().addActionListener(this);
        this.telaBuscaMarca.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaMarca.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaMarca.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaMarca.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaMarca.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadMarca.codigo = (int) telaBuscaMarca.getjTableDados()
                .getValueAt(telaBuscaMarca.getjTableDados().getSelectedRow(), 0);
            telaBuscaMarca.dispose();
        }
    }

    private enum FiltroMarca {
        ID, DESCRICAO;

        public static FiltroMarca fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaMarca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaMarca.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaMarca.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaMarca.getjTFFiltro().getText();

        FiltroMarca filtro = FiltroMarca.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID:
                    Marca marca = marcaService.Carregar(Integer.parseInt(filtroTexto));
                    if (marca != null) {
                        tabela.addRow(new Object[]{marca.getId(), marca.getDescricao(), marca.getStatus()});
                    }
                    break;
                case DESCRICAO:
                    List<Marca> listaPorDescricao = marcaService.Carregar("descricao", filtroTexto);
                    for (Marca m : listaPorDescricao) {
                        tabela.addRow(new Object[]{m.getId(), m.getDescricao(), m.getStatus()});
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaMarca, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        telaBuscaMarca.dispose();
    }
}
