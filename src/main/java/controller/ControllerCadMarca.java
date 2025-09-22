package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;
import view.TelaCadastroMarca;

public class ControllerCadMarca implements ActionListener {

    TelaCadastroMarca telaCadastroMarca;
    public static int codigo;

    public ControllerCadMarca(TelaCadastroMarca telaCadastroMarca) {
        this.telaCadastroMarca = telaCadastroMarca;

        this.telaCadastroMarca.getjButtonNovo().addActionListener(this);
        this.telaCadastroMarca.getjButtonCancelar().addActionListener(this);
        this.telaCadastroMarca.getjButtonGravar().addActionListener(this);
        this.telaCadastroMarca.getjButtonBuscar().addActionListener(this);
        this.telaCadastroMarca.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroMarca.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroMarca.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroMarca.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroMarca.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroMarca.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), true);
        this.telaCadastroMarca.getjTextFieldId().setEnabled(false);
        this.telaCadastroMarca.getjTextFieldDescricao().requestFocus();
        telaCadastroMarca.getjTextFieldId().setEnabled(false);
        telaCadastroMarca.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroMarca.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroMarca.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroMarca.getjTextFieldDescricao().requestFocus();
            return false;
        }
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Marca marca = new Marca();
        marca.setDescricao(telaCadastroMarca.getjTextFieldDescricao().getText());

        marca.setStatus(
            telaCadastroMarca.getjComboBoxStatus().getSelectedItem().equals("Ativo") ? 'A' : 'I'
        );

        if (telaCadastroMarca.getjTextFieldId().getText().trim().isEmpty()) {
            MarcaService.Criar(marca);
        } else {
            marca.setId(Integer.parseInt(telaCadastroMarca.getjTextFieldId().getText()));
            MarcaService.Atualizar(marca);
        }

        utilities.Utilities.ativaDesativa(telaCadastroMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroMarca.getjPanelDados(), false);
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
        ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca);
        telaBuscaMarca.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroMarca.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroMarca.getjPanelDados(), true);

            telaCadastroMarca.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroMarca.getjTextFieldId().setEnabled(false);

            Marca marca = MarcaService.Carregar(codigo);

            telaCadastroMarca.getjTextFieldDescricao().setText(marca.getDescricao());
            telaCadastroMarca.getjComboBoxStatus().setSelectedItem(
                marca.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroMarca.getjTextFieldDescricao().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroMarca.dispose();
    }
}
