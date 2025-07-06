
package controller;

import view.TelaCadastroVagaEstacionamento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.buscas.TelaBuscaVaga;

public class ControllerCadVagaEstacionamento implements ActionListener {

    TelaCadastroVagaEstacionamento telaCadastroVagaEstacionamento;

    public ControllerCadVagaEstacionamento(TelaCadastroVagaEstacionamento telaCadastroVagaEstacionamento) {

        this.telaCadastroVagaEstacionamento = telaCadastroVagaEstacionamento;

        this.telaCadastroVagaEstacionamento.getjButtonNovo().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonCancelar().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonGravar().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonBuscar().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroVagaEstacionamento.getjButtonNovo().setEnabled(true);
        this.telaCadastroVagaEstacionamento.getjButtonCancelar().setEnabled(false);
        this.telaCadastroVagaEstacionamento.getjButtonGravar().setEnabled(false);
        this.telaCadastroVagaEstacionamento.getjButtonBuscar().setEnabled(true);
        this.telaCadastroVagaEstacionamento.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroVagaEstacionamento.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), false);
        } else if (evento.getSource() == this.telaCadastroVagaEstacionamento.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
        } else if (evento.getSource() == this.telaCadastroVagaEstacionamento.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
        } else if (evento.getSource() == this.telaCadastroVagaEstacionamento.getjButtonBuscar()) {
            
            TelaBuscaVaga telaBuscaVaga = new TelaBuscaVaga(null, true);
            ControllerBuscaVagaEstacionamento controllerBuscaVagaEstacionamento = new ControllerBuscaVagaEstacionamento(telaBuscaVaga);
            telaBuscaVaga.setVisible(true);
            
        } else if (evento.getSource() == this.telaCadastroVagaEstacionamento.getjButtonSair()) {
            this.telaCadastroVagaEstacionamento.dispose();
        }
    }
}