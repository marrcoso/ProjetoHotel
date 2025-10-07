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
        this.telaBuscaMarca.getjButtonAtivar().addActionListener(this);
        this.telaBuscaMarca.getjButtonInativar().addActionListener(this);
        this.telaBuscaMarca.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaMarca.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaMarca.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaMarca.getjTableDados().getValueAt(row, 2); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaMarca.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaMarca.getjButtonInativar().setEnabled(status == 'A');
        }
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
            return;
        }
        if (source == telaBuscaMarca.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaMarca.getjButtonInativar()) {
            handleAtivarInativar(false);
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
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<Marca> listaMarcas = marcaService.Carregar(atributo, valor);
        for (Marca m : listaMarcas) {
            adicionarLinhaTabela(tabela, m);
        }
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
                    carregarPorAtributo("descricao", filtroTexto, tabela);
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

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaMarca.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaMarca.getjTableDados()
            .getValueAt(telaBuscaMarca.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaMarca.getjTableDados()
            .getValueAt(telaBuscaMarca.getjTableDados().getSelectedRow(), 2);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("A Marca já está %s.", ativar ? "Ativa" : "Inativa"));
                return;
            }

            marcaService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaMarca.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaMarca.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 2);
            telaBuscaMarca.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaMarca.getjButtonInativar().setEnabled(ativar);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaMarca, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
