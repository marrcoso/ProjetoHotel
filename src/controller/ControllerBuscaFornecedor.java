package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaBuscaFornecedor;

public class ControllerBuscaFornecedor implements ActionListener {

    TelaBuscaFornecedor telaBuscaFornecedor;

    public ControllerBuscaFornecedor(TelaBuscaFornecedor telaBuscaFornecedor) {

        this.telaBuscaFornecedor = telaBuscaFornecedor;

        this.telaBuscaFornecedor.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaFornecedor.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaFornecedor.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                JOptionPane.showMessageDialog(null, "Carregando Dados para Edição....");
            }
        } else if (evento.getSource() == this.telaBuscaFornecedor.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaFornecedor.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaFornecedor.getjCBFiltro().getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Filtrando por ID");

                } else if (this.telaBuscaFornecedor.getjCBFiltro().getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Nome");
                } else if (this.telaBuscaFornecedor.getjCBFiltro().getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(null, "Filtrando por CPF");
                }
            }
        } else if (evento.getSource() == this.telaBuscaFornecedor.getjButtonSair()) {
            this.telaBuscaFornecedor.dispose();
        }
    }
}
