package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Veiculo;
import service.VeiculoService;
import view.TelaBuscaVeiculo;
import view.TelaCadastroVeiculo;

public class ControllerCadVeiculo implements ActionListener {

    TelaCadastroVeiculo telaCadastroVeiculo;
    public static int codigo;

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {
        this.telaCadastroVeiculo = telaCadastroVeiculo;

        this.telaCadastroVeiculo.getjButtonNovo().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonGravar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroVeiculo.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroVeiculo.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroVeiculo.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroVeiculo.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroVeiculo.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), true);
        this.telaCadastroVeiculo.getjTextFieldId().setEnabled(false);
        this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        telaCadastroVeiculo.getjTextFieldCor().requestFocus();
        telaCadastroVeiculo.getjTextFieldId().setEnabled(false);
        telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroVeiculo.getjComboBoxStatus().setEnabled(false);
        telaCadastroVeiculo.getjComboBoxModelo().setSelectedItem("Selecione um modelo");
        telaCadastroVeiculo.getjComboBoxModelo().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroVeiculo.getjTextFieldPlaca().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Placa é obrigatório.");
            telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
            return false;
        }
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(telaCadastroVeiculo.getjTextFieldPlaca().getText());
        veiculo.setCor(telaCadastroVeiculo.getjTextFieldCor().getText());

        veiculo.setStatus(
            telaCadastroVeiculo.getjComboBoxStatus().getSelectedItem().equals("Ativo") ? 'A' : 'I'
        );

        if (telaCadastroVeiculo.getjTextFieldId().getText().trim().isEmpty()) {
            VeiculoService.Criar(veiculo);
        } else {
            veiculo.setId(Integer.parseInt(telaCadastroVeiculo.getjTextFieldId().getText()));
            VeiculoService.Atualizar(veiculo);
        }

        utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), false);
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
        ControllerBuscaVeiculo controllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo);
        telaBuscaVeiculo.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), true);

            telaCadastroVeiculo.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroVeiculo.getjTextFieldId().setEnabled(false);

            Veiculo veiculo = VeiculoService.Carregar(codigo);

            telaCadastroVeiculo.getjTextFieldPlaca().setText(veiculo.getPlaca());
            telaCadastroVeiculo.getjTextFieldCor().setText(veiculo.getCor());
            telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem(
                veiculo.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroVeiculo.dispose();
    }
}
