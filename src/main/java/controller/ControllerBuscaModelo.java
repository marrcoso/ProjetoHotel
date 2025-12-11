package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Modelo;
import service.ModeloService;
import view.TelaBuscaModelo;

public final class ControllerBuscaModelo implements ActionListener, InterfaceControllerBusca<Modelo> {

    private final TelaBuscaModelo telaBuscaModelo;
    private final ModeloService modeloService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaModelo(TelaBuscaModelo telaBuscaModelo, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaModelo = telaBuscaModelo;
        this.modeloService = new ModeloService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaModelo.getjButtonCarregar().addActionListener(this);
        this.telaBuscaModelo.getjButtonFiltar().addActionListener(this);
        this.telaBuscaModelo.getjButtonSair().addActionListener(this);
        this.telaBuscaModelo.getjButtonAtivar().addActionListener(this);
        this.telaBuscaModelo.getjButtonInativar().addActionListener(this);
        this.telaBuscaModelo.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaModelo.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaModelo.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaModelo.getjTableDados().getValueAt(row, 3); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaModelo.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaModelo.getjButtonInativar().setEnabled(status == 'A');
        }
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
            return;
        }
        if (source == telaBuscaModelo.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaModelo.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaModelo.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaModelo.getjTableDados()
                .getValueAt(telaBuscaModelo.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
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

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Modelo modelo) {
        tabela.addRow(new Object[]{
            modelo.getId(),
            modelo.getDescricao(),
            modelo.getMarca().getId(),
            modelo.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<Modelo> listaModelos = modeloService.Carregar(atributo, valor);
        for (Modelo m : listaModelos) {
            adicionarLinhaTabela(tabela, m);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaModelo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaModelo.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaModelo.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaModelo.getjTFFiltro().getText();

        FiltroModelo filtro = FiltroModelo.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Modelo modelo = modeloService.Carregar(Integer.parseInt(filtroTexto));
                    if (modelo == null) {
                        JOptionPane.showMessageDialog(telaBuscaModelo, "Nenhum modelo encontrado com o ID informado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    adicionarLinhaTabela(tabela, modelo);
                    break;
                }
                case DESCRICAO: {
                    carregarPorAtributo("descricao", filtroTexto, tabela);
                    break;
                }
                case MARCA: {
                    carregarPorAtributo("marca.id", filtroTexto, tabela);
                    break;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaModelo.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaModelo.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaModelo.getjTableDados()
            .getValueAt(telaBuscaModelo.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaModelo.getjTableDados()
            .getValueAt(telaBuscaModelo.getjTableDados().getSelectedRow(), 3);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Modelo já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            modeloService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaModelo.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaModelo.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 3);
            telaBuscaModelo.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaModelo.getjButtonInativar().setEnabled(ativar);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
