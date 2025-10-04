package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;

public final class ControllerBuscaMarca implements ActionListener, InterfaceControllerBusca<Marca> {

    private final TelaBuscaMarca telaBuscaMarca;
    private final MarcaService marcaService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaMarca(TelaBuscaMarca telaBuscaMarca, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaMarca = telaBuscaMarca;
        this.marcaService = new MarcaService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
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

    @Override
    public void handleCarregar() {
        if (telaBuscaMarca.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaMarca.getjTableDados()
                .getValueAt(telaBuscaMarca.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
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

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Marca marca) {
        tabela.addRow(new Object[]{
            marca.getId(),
            marca.getDescricao(),
            marca.getStatus()
        });
    }

    @Override
    public void handleFiltrar() {
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
                case ID: {
                    Marca marca = marcaService.Carregar(Integer.parseInt(filtroTexto));
                    if (marca != null) {
                        adicionarLinhaTabela(tabela, marca);
                    }
                    break;
                }
                case DESCRICAO: {
                    List<Marca> listaPorDescricao = marcaService.Carregar("descricao", filtroTexto);
                    for (Marca marca : listaPorDescricao) {
                        adicionarLinhaTabela(tabela, marca);
                    }
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaMarca, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaMarca.dispose();
    }
}
