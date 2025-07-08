package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaBuscaServico;

public class ControllerBuscaServico implements ActionListener {

    TelaBuscaServico telaBuscaServico;

    public ControllerBuscaServico(TelaBuscaServico telaBuscaServico) {

        this.telaBuscaServico = telaBuscaServico;

        this.telaBuscaServico.getjButtonCarregar().addActionListener(this);
        this.telaBuscaServico.getjButtonFiltar().addActionListener(this);
        this.telaBuscaServico.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaServico.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaServico.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                JOptionPane.showMessageDialog(null, "Carregando Dados para Edição....");
            }
        } else if (evento.getSource() == this.telaBuscaServico.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaServico.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaServico.getjCBFiltro().getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Filtrando por ID");

                } else if (this.telaBuscaServico.getjCBFiltro().getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
                } else if (this.telaBuscaServico.getjCBFiltro().getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(null, "Filtrando por Observação");
                }
            }
        } else if (evento.getSource() == this.telaBuscaServico.getjButtonSair()) {
            this.telaBuscaServico.dispose();
        }
    }
}
