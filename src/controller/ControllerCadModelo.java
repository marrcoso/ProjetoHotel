
package controller;

import view.TelaCadastroModelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.TelaBuscaModelo;

public class ControllerCadModelo implements ActionListener {

    TelaCadastroModelo telaCadastroModelo;

    public ControllerCadModelo(TelaCadastroModelo telaCadastroModelo) {

        this.telaCadastroModelo = telaCadastroModelo;

        this.telaCadastroModelo.getjButtonNovo().addActionListener(this);
        this.telaCadastroModelo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroModelo.getjButtonGravar().addActionListener(this);
        this.telaCadastroModelo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroModelo.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroModelo.getjButtonNovo().setEnabled(true);
        this.telaCadastroModelo.getjButtonCancelar().setEnabled(false);
        this.telaCadastroModelo.getjButtonGravar().setEnabled(false);
        this.telaCadastroModelo.getjButtonBuscar().setEnabled(true);
        this.telaCadastroModelo.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroModelo.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), true);
        } else if (evento.getSource() == this.telaCadastroModelo.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroModelo.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroModelo.getjButtonBuscar()) {
            
            TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
            ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo);
            telaBuscaModelo.setVisible(true);
            
        } else if (evento.getSource() == this.telaCadastroModelo.getjButtonSair()) {
            this.telaCadastroModelo.dispose();
        }
    }
}