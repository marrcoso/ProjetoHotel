
package controller;

import view.TelaCadastroMarca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.TelaBuscaMarca;

public class ControllerCadMarca implements ActionListener {

    TelaCadastroMarca telaCadastroMarca;

    public ControllerCadMarca(TelaCadastroMarca telaCadastroMarca) {

        this.telaCadastroMarca = telaCadastroMarca;

        this.telaCadastroMarca.getjButtonNovo().addActionListener(this);
        this.telaCadastroMarca.getjButtonCancelar().addActionListener(this);
        this.telaCadastroMarca.getjButtonGravar().addActionListener(this);
        this.telaCadastroMarca.getjButtonBuscar().addActionListener(this);
        this.telaCadastroMarca.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroMarca.getjButtonNovo().setEnabled(true);
        this.telaCadastroMarca.getjButtonCancelar().setEnabled(false);
        this.telaCadastroMarca.getjButtonGravar().setEnabled(false);
        this.telaCadastroMarca.getjButtonBuscar().setEnabled(true);
        this.telaCadastroMarca.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
        
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroMarca.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), true);
        } else if (evento.getSource() == this.telaCadastroMarca.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroMarca.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroMarca.getjButtonBuscar()) {
            
            TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
            ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca);
            telaBuscaMarca.setVisible(true);
            
        } else if (evento.getSource() == this.telaCadastroMarca.getjButtonSair()) {
            this.telaCadastroMarca.dispose();
        }
    }
}