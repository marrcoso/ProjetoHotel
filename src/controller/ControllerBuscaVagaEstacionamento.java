package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaBuscaVaga;

public class ControllerBuscaVagaEstacionamento implements ActionListener {

    TelaBuscaVaga telaBuscaVaga;

    public ControllerBuscaVagaEstacionamento(TelaBuscaVaga telaBuscaVaga) {

        this.telaBuscaVaga = telaBuscaVaga;

        this.telaBuscaVaga.getjButtonCarregar().addActionListener(this);
        this.telaBuscaVaga.getjButtonFiltar().addActionListener(this);
        this.telaBuscaVaga.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaVaga.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaVaga.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                JOptionPane.showMessageDialog(null, "Carregando Dados para Edição....");
            }
        } else if (evento.getSource() == this.telaBuscaVaga.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaVaga.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaVaga.getjCBFiltro().getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Filtrando por ID");

                } else if (this.telaBuscaVaga.getjCBFiltro().getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
                } else if (this.telaBuscaVaga.getjCBFiltro().getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Observação");
                } else if (this.telaBuscaVaga.getjCBFiltro().getSelectedIndex() == 3) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Metragem");
                }
            }
        } else if (evento.getSource() == this.telaBuscaVaga.getjButtonSair()) {
            this.telaBuscaVaga.dispose();
        }
    }
}
