package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;

public class ControllerBuscaFornecedor implements ActionListener, InterfaceControllerBusca {

    TelaBuscaFornecedor telaBuscaFornecedor;
    private final FornecedorService fornecedorService;

    public ControllerBuscaFornecedor(TelaBuscaFornecedor telaBuscaFornecedor) {
        this.telaBuscaFornecedor = telaBuscaFornecedor;
        this.fornecedorService = new FornecedorService();
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaFornecedor.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaFornecedor.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaFornecedor.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaFornecedor.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaFornecedor.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadFornecedor.codigo = (int) telaBuscaFornecedor.getjTableDados()
                .getValueAt(telaBuscaFornecedor.getjTableDados().getSelectedRow(), 0);
            telaBuscaFornecedor.dispose();
        }
    }

    private enum FiltroFornecedor {
        ID, NOME, CPF;

        public static FiltroFornecedor fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CPF;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaFornecedor.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaFornecedor.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaFornecedor.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaFornecedor.getjTFFiltro().getText();

        FiltroFornecedor filtro = FiltroFornecedor.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID:
                    Fornecedor fornecedor = fornecedorService.Carregar(Integer.parseInt(filtroTexto));
                    if (fornecedor != null) {
                        tabela.addRow(new Object[]{fornecedor.getId(), fornecedor.getNome(), fornecedor.getCpf(), fornecedor.getStatus()});
                    }
                    break;
                case NOME:
                    List<Fornecedor> listaPorNome = fornecedorService.Carregar("nome", filtroTexto);
                    for (Fornecedor f : listaPorNome) {
                        tabela.addRow(new Object[]{f.getId(), f.getNome(), f.getCpf(), f.getStatus()});
                    }
                    break;
                case CPF:
                    List<Fornecedor> listaPorCpf = fornecedorService.Carregar("cpf", filtroTexto);
                    for (Fornecedor f : listaPorCpf) {
                        tabela.addRow(new Object[]{f.getId(), f.getNome(), f.getCpf(), f.getStatus()});
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        this.telaBuscaFornecedor.dispose();
    }
}
