package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Fornecedor;
import view.TelaBuscaFornecedor;

public class ControllerBuscaFornecedor implements ActionListener {

    TelaBuscaFornecedor telaBuscaFornecedor;

    public ControllerBuscaFornecedor(TelaBuscaFornecedor telaBuscaFornecedor) {
        this.telaBuscaFornecedor = telaBuscaFornecedor;
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
        ID, NOME, CNPJ;

        public static FiltroFornecedor fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CNPJ;
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

        switch (filtro) {
            case ID:
                Fornecedor fornecedor = service.FornecedorService.Carregar(Integer.parseInt(filtroTexto));
                if (fornecedor != null) {
                    tabela.addRow(new Object[]{fornecedor.getId(), fornecedor.getNome(), fornecedor.getCnpj(), fornecedor.getStatus()});
                }
                break;
            case NOME:
                List<Fornecedor> listaPorNome = service.FornecedorService.Carregar("nome", filtroTexto);
                for (Fornecedor f : listaPorNome) {
                    tabela.addRow(new Object[]{f.getId(), f.getNome(), f.getCnpj(), f.getStatus()});
                }
                break;
            case CNPJ:
                List<Fornecedor> listaPorCnpj = service.FornecedorService.Carregar("cnpj", filtroTexto);
                for (Fornecedor f : listaPorCnpj) {
                    tabela.addRow(new Object[]{f.getId(), f.getNome(), f.getCnpj(), f.getStatus()});
                }
                break;
        }
    }

    private void handleSair() {
        telaBuscaFornecedor.dispose();
    }
}
