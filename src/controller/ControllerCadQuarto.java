
package controller;

import view.TelaCadastroQuarto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerCadQuarto implements ActionListener {

    TelaCadastroQuarto telaCadastroQuarto;

    public ControllerCadQuarto(TelaCadastroQuarto telaCadastroQuarto) {

        this.telaCadastroQuarto = telaCadastroQuarto;

        this.telaCadastroQuarto.getjButtonNovo().addActionListener(this);
        this.telaCadastroQuarto.getjButtonCancelar().addActionListener(this);
        this.telaCadastroQuarto.getjButtonGravar().addActionListener(this);
        this.telaCadastroQuarto.getjButtonBuscar().addActionListener(this);
        this.telaCadastroQuarto.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroQuarto.getjButtonNovo().setEnabled(true);
        this.telaCadastroQuarto.getjButtonCancelar().setEnabled(false);
        this.telaCadastroQuarto.getjButtonGravar().setEnabled(false);
        this.telaCadastroQuarto.getjButtonBuscar().setEnabled(true);
        this.telaCadastroQuarto.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroQuarto.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), false);
        } else if (evento.getSource() == this.telaCadastroQuarto.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
        } else if (evento.getSource() == this.telaCadastroQuarto.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
        } else if (evento.getSource() == this.telaCadastroQuarto.getjButtonBuscar()) {
        } else if (evento.getSource() == this.telaCadastroQuarto.getjButtonSair()) {
            this.telaCadastroQuarto.dispose();
        }
    }
}