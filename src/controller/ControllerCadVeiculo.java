
package controller;

import view.TelaCadastroVeiculo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerCadVeiculo implements ActionListener {

    TelaCadastroVeiculo telaCadastroVeiculo;

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {

        this.telaCadastroVeiculo = telaCadastroVeiculo;

        this.telaCadastroVeiculo.getjButtonNovo().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonGravar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroVeiculo.getjButtonNovo().setEnabled(true);
        this.telaCadastroVeiculo.getjButtonCancelar().setEnabled(false);
        this.telaCadastroVeiculo.getjButtonGravar().setEnabled(false);
        this.telaCadastroVeiculo.getjButtonBuscar().setEnabled(true);
        this.telaCadastroVeiculo.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroVeiculo.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), false);
        } else if (evento.getSource() == this.telaCadastroVeiculo.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        } else if (evento.getSource() == this.telaCadastroVeiculo.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        } else if (evento.getSource() == this.telaCadastroVeiculo.getjButtonBuscar()) {
        } else if (evento.getSource() == this.telaCadastroVeiculo.getjButtonSair()) {
            this.telaCadastroVeiculo.dispose();
        }
    }
}