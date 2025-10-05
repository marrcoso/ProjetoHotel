package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Modelo;
import model.Veiculo;
import service.VeiculoService;
import view.TelaBuscaModelo;
import view.TelaBuscaVeiculo;
import view.TelaCadastroVeiculo;

public final class ControllerCadVeiculo implements ActionListener, InterfaceControllerCad<Veiculo> {

    private final TelaCadastroVeiculo telaCadastroVeiculo;
    private final VeiculoService veiculoService;
    private int codigoVeiculo;
    private int codigoModelo;
    private Modelo modeloRelacionado;

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {
        this.telaCadastroVeiculo = telaCadastroVeiculo;
        this.veiculoService = new VeiculoService();
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);
        this.telaCadastroVeiculo.getjFormattedTextFieldModelo().putClientProperty(utilities.Utilities.ALWAYS_DISABLED, true);
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCadastroVeiculo.getjButtonNovo().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonGravar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonSair().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonRelacionarModelo().addActionListener(this);
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
        if (source == telaCadastroVeiculo.getjButtonRelacionarModelo()) {
            handleRelacionarModelo();
        }
    }

    @Override
    public void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), true);
        this.telaCadastroVeiculo.getjTextFieldId().setEnabled(false);
        this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        this.telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroVeiculo.getjComboBoxStatus().setEnabled(false);
    }

    @Override
    public void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);
        this.modeloRelacionado = null;
    }

    @Override
    public boolean isFormularioValido() {
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
        if (modeloRelacionado == null) {
            JOptionPane.showMessageDialog(null, "Selecione um Modelo para o Veículo.");
            telaCadastroVeiculo.getjButtonRelacionarModelo().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
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

    @Override
    public Veiculo construirDoFormulario() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(telaCadastroVeiculo.getjTextFieldPlaca().getText());
        veiculo.setCor(telaCadastroVeiculo.getjTextFieldCor().getText());

        Object statusSelecionado = telaCadastroVeiculo.getjComboBoxStatus().getSelectedItem();
        veiculo.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        veiculo.setModelo(modeloRelacionado);

        return veiculo;
    }

    public void handleRelacionarModelo() {
        codigoModelo = 0;
        TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo, codigo -> this.codigoModelo = codigo);
        telaBuscaModelo.setVisible(true);

        if (codigoModelo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            this.telaCadastroVeiculo.getjTextFieldId().setEnabled(false);
            this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
            this.telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");
            this.telaCadastroVeiculo.getjComboBoxStatus().setEnabled(false);

            Modelo modelo;
            try {
                modelo = new service.ModeloService().Carregar(codigoModelo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.modeloRelacionado = modelo;
            telaCadastroVeiculo.getjFormattedTextFieldModelo().setText(getModeloFormat(modeloRelacionado));
        }
    }

    private String getModeloFormat(Modelo modelo) {
        if (modelo == null) {
            return "";
        }
        return String.format("%d - %s", modelo.getId(), modelo.getDescricao());
    }

    @Override
    public void handleBuscar() {
        codigoVeiculo = 0;
        TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaVeiculo controllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo, codigo -> this.codigoVeiculo = codigo);
        telaBuscaVeiculo.setVisible(true);

        if (codigoVeiculo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), true);
            telaCadastroVeiculo.getjTextFieldId().setText(String.valueOf(codigoVeiculo));
            telaCadastroVeiculo.getjTextFieldId().setEnabled(false);

            Veiculo veiculo;
            try {
                veiculo = new VeiculoService().Carregar(codigoVeiculo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroVeiculo.getjTextFieldPlaca().setText(veiculo.getPlaca());
            telaCadastroVeiculo.getjTextFieldCor().setText(veiculo.getCor());
            telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem(
                veiculo.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            this.modeloRelacionado = veiculo.getModelo();
            telaCadastroVeiculo.getjFormattedTextFieldModelo().setText(getModeloFormat(modeloRelacionado));
            telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        telaCadastroVeiculo.dispose();
    }
}