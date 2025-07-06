package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.buscas.TelaBuscaHospede;

public class ControllerBuscaHospede implements ActionListener {

    TelaBuscaHospede telaBuscaHospede;

    public ControllerBuscaHospede(TelaBuscaHospede telaBuscaHospede) {

        this.telaBuscaHospede = telaBuscaHospede;

        this.telaBuscaHospede.getjButtonCarregar().addActionListener(this);
        this.telaBuscaHospede.getjButtonFiltar().addActionListener(this);
        this.telaBuscaHospede.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaHospede.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaHospede.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                JOptionPane.showMessageDialog(null, "Carregando Dados para Edição....");
            }
        } else if (evento.getSource() == this.telaBuscaHospede.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaHospede.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaHospede.getjCBFiltro().getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Filtrando por ID");

                } else if (this.telaBuscaHospede.getjCBFiltro().getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Nome");
                } else if (this.telaBuscaHospede.getjCBFiltro().getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(null, "Filtrando por CPF");
                }
            }
        } else if (evento.getSource() == this.telaBuscaHospede.getjButtonSair()) {
            this.telaBuscaHospede.dispose();
        }
    }
}
