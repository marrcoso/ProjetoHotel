package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Modelo;
import service.ModeloService;
import view.TelaBuscaModelo;
import view.TelaCadastroModelo;

public class ControllerCadModelo implements ActionListener {

    TelaCadastroModelo telaCadastroModelo;
    public static int codigo;

    public ControllerCadModelo(TelaCadastroModelo telaCadastroModelo) {
        this.telaCadastroModelo = telaCadastroModelo;

        this.telaCadastroModelo.getjButtonNovo().addActionListener(this);
        this.telaCadastroModelo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroModelo.getjButtonGravar().addActionListener(this);
        this.telaCadastroModelo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroModelo.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroModelo.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroModelo.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroModelo.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroModelo.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroModelo.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), true);
        this.telaCadastroModelo.getjTextFieldId().setEnabled(false);
        this.telaCadastroModelo.getjTextFieldDescricao().requestFocus();
        telaCadastroModelo.getjTextFieldId().setEnabled(false);
        telaCadastroModelo.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroModelo.getjComboBoxStatus().setEnabled(false);
        telaCadastroModelo.getjComboBoxMarca().setSelectedItem("Selecione uma marca");
        telaCadastroModelo.getjComboBoxMarca().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroModelo.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroModelo.getjTextFieldDescricao().requestFocus();
            return false;
        }
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Modelo modelo = new Modelo();
        modelo.setDescricao(telaCadastroModelo.getjTextFieldDescricao().getText());

        modelo.setStatus(
            telaCadastroModelo.getjComboBoxStatus().getSelectedItem().equals("Ativo") ? 'A' : 'I'
        );

        if (telaCadastroModelo.getjTextFieldId().getText().trim().isEmpty()) {
            ModeloService.Criar(modelo);
        } else {
            modelo.setId(Integer.parseInt(telaCadastroModelo.getjTextFieldId().getText()));
            ModeloService.Atualizar(modelo);
        }

        utilities.Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), false);
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
        ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo);
        telaBuscaModelo.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), true);

            telaCadastroModelo.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroModelo.getjTextFieldId().setEnabled(false);

            Modelo modelo = ModeloService.Carregar(codigo);

            telaCadastroModelo.getjTextFieldDescricao().setText(modelo.getDescricao());
            telaCadastroModelo.getjComboBoxStatus().setSelectedItem(
                modelo.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroModelo.getjTextFieldDescricao().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroModelo.dispose();
    }
}
