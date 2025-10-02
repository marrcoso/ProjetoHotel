package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Veiculo;
import service.VeiculoService;
import view.TelaBuscaVeiculo;
import view.TelaCadastroVeiculo;

public class ControllerCadVeiculo implements ActionListener, InterfaceControllerCad<Veiculo> {

    private final TelaCadastroVeiculo telaCadastroVeiculo;
    private final VeiculoService veiculoService;
    public static int codigo;

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {
        this.telaCadastroVeiculo = telaCadastroVeiculo;
        this.veiculoService = new VeiculoService();
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);

        initListeners();
    }

    private void initListeners() {
        this.telaCadastroVeiculo.getjButtonNovo().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonGravar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonSair().addActionListener(this);
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
        this.telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroVeiculo.getjComboBoxStatus().setEnabled(false);
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
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroVeiculo.getjTextFieldCor().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Cor é obrigatório.");
            telaCadastroVeiculo.getjTextFieldCor().requestFocus();
            return false;
        }
        
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroVeiculo.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroVeiculo.getjComboBoxStatus().requestFocus();
            return false;
        }
        // TODO: Validar modelo
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Veiculo veiculo = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroVeiculo.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                veiculoService.Criar(veiculo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), false);
            return;
        }

        veiculo.setId(Integer.parseInt(telaCadastroVeiculo.getjTextFieldId().getText()));
        try {
            veiculoService.Atualizar(veiculo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), false);
    }

    private Veiculo construirDoFormulario() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(telaCadastroVeiculo.getjTextFieldPlaca().getText());
        veiculo.setCor(telaCadastroVeiculo.getjTextFieldCor().getText());

        Object statusSelecionado = telaCadastroVeiculo.getjComboBoxStatus().getSelectedItem();
        veiculo.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        //TODO: Setar modelo

        return veiculo;
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaVeiculo controllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo);
        telaBuscaVeiculo.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), true);

            telaCadastroVeiculo.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroVeiculo.getjTextFieldId().setEnabled(false);

            Veiculo veiculo;
            try {
                veiculo = new VeiculoService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroVeiculo.getjTextFieldPlaca().setText(veiculo.getPlaca());
            telaCadastroVeiculo.getjTextFieldCor().setText(veiculo.getCor());
            telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem(
                veiculo.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            //TODO: Obter Modelo

            telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroVeiculo.dispose();
    }
}