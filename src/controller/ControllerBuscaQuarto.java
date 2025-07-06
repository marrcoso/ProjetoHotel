package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.buscas.TelaBuscaQuarto;

public class ControllerBuscaQuarto implements ActionListener {

    TelaBuscaQuarto telaBuscaQuarto;

    public ControllerBuscaQuarto(TelaBuscaQuarto telaBuscaQuarto) {

        this.telaBuscaQuarto = telaBuscaQuarto;

        this.telaBuscaQuarto.getjButtonCarregar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonFiltar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaQuarto.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaQuarto.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                JOptionPane.showMessageDialog(null, "Carregando Dados para Edição....");
            }
        } else if (evento.getSource() == this.telaBuscaQuarto.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaQuarto.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaQuarto.getjCBFiltro().getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Filtrando por ID");

                } else if (this.telaBuscaQuarto.getjCBFiltro().getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Nome");
                } else if (this.telaBuscaQuarto.getjCBFiltro().getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(null, "Filtrando por CPF");
                }
            }
        } else if (evento.getSource() == this.telaBuscaQuarto.getjButtonSair()) {
            this.telaBuscaQuarto.dispose();
        }
    }
}
