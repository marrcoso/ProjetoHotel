package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.buscas.TelaBuscaProduto;

public class ControllerBuscaProduto implements ActionListener {

    TelaBuscaProduto telaBuscaProduto;

    public ControllerBuscaProduto(TelaBuscaProduto telaBuscaProduto) {

        this.telaBuscaProduto = telaBuscaProduto;

        this.telaBuscaProduto.getjButtonCarregar().addActionListener(this);
        this.telaBuscaProduto.getjButtonFiltar().addActionListener(this);
        this.telaBuscaProduto.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaProduto.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaProduto.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                JOptionPane.showMessageDialog(null, "Carregando Dados para Edição....");
            }
        } else if (evento.getSource() == this.telaBuscaProduto.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaProduto.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaProduto.getjCBFiltro().getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Filtrando por ID");

                } else if (this.telaBuscaProduto.getjCBFiltro().getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Nome");
                } else if (this.telaBuscaProduto.getjCBFiltro().getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(null, "Filtrando por CPF");
                }
            }
        } else if (evento.getSource() == this.telaBuscaProduto.getjButtonSair()) {
            this.telaBuscaProduto.dispose();
        }
    }
}
