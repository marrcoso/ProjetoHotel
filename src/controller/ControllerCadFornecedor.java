
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.TelaBuscaFornecedor;
import view.TelaCadastroFornecedor;

public class ControllerCadFornecedor implements ActionListener {

    TelaCadastroFornecedor telaCadastroFornecedor;

    public ControllerCadFornecedor(TelaCadastroFornecedor telaCadastroFornecedor) {

        this.telaCadastroFornecedor = telaCadastroFornecedor;

        this.telaCadastroFornecedor.getjButtonNovo().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonCancelar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonGravar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonBuscar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonSair().addActionListener(this);

        //Desenvolver as setagens de situação inicial dos componentes
        /*this.telaCadastroFornecedor.getjButtonNovo().setEnabled(true);
        this.telaCadastroFornecedor.getjButtonCancelar().setEnabled(false);
        this.telaCadastroFornecedor.getjButtonGravar().setEnabled(false);
        this.telaCadastroFornecedor.getjButtonBuscar().setEnabled(true);
        this.telaCadastroFornecedor.getjButtonSair().setEnabled(true);*/
        utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroFornecedor.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), true);
        } else if (evento.getSource() == this.telaCadastroFornecedor.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroFornecedor.getjButtonGravar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
        } else if (evento.getSource() == this.telaCadastroFornecedor.getjButtonBuscar()) {
            
            TelaBuscaFornecedor telaBuscaFornecedor = new TelaBuscaFornecedor(null, true);
            ControllerBuscaFornecedor controllerBuscaFornecedor = new ControllerBuscaFornecedor(telaBuscaFornecedor);
            telaBuscaFornecedor.setVisible(true);
            
        } else if (evento.getSource() == this.telaCadastroFornecedor.getjButtonSair()) {
            this.telaCadastroFornecedor.dispose();
        }
    }
}