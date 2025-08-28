
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.TelaCadastroServico;
import view.TelaBuscaServico;

public class ControllerCadServico implements ActionListener {

    TelaCadastroServico telaCadastroServico;

    public ControllerCadServico(TelaCadastroServico telaCadastroServico) {

        this.telaCadastroServico = telaCadastroServico;

        this.telaCadastroServico.getjButtonNovo().addActionListener(this);
        this.telaCadastroServico.getjButtonCancelar().addActionListener(this);
        this.telaCadastroServico.getjButtonGravar().addActionListener(this);
        this.telaCadastroServico.getjButtonBuscar().addActionListener(this);
        this.telaCadastroServico.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroServico.getjButtonNovo().setEnabled(true);
        this.telaCadastroServico.getjButtonCancelar().setEnabled(false);
        this.telaCadastroServico.getjButtonGravar().setEnabled(false);
        this.telaCadastroServico.getjButtonBuscar().setEnabled(true);
        this.telaCadastroServico.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroServico.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), true);
        } else if (evento.getSource() == this.telaCadastroServico.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroServico.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroServico.getjButtonBuscar()) {
            
            TelaBuscaServico telaBuscaServico = new TelaBuscaServico(null, true);
            ControllerBuscaServico controllerBuscaServico = new ControllerBuscaServico(telaBuscaServico);
            telaBuscaServico.setVisible(true);
            
        } else if (evento.getSource() == this.telaCadastroServico.getjButtonSair()) {
            this.telaCadastroServico.dispose();
        }
    }
}