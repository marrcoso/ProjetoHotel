package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Fornecedor;
import model.Funcionario;
import model.Hospede;
import model.Modelo;
import model.Pessoa;
import model.Veiculo;
import service.FornecedorService;
import service.FuncionarioService;
import service.HospedeService;
import service.VeiculoService;
import utilities.Utilities;
import view.TelaBuscaFornecedor;
import view.TelaBuscaFuncionario;
import view.TelaBuscaHospede;
import view.TelaBuscaModelo;
import view.TelaBuscaVeiculo;
import view.TelaCadastroVeiculo;

public final class ControllerCadVeiculo implements ActionListener, InterfaceControllerCad<Veiculo> {

    private final TelaCadastroVeiculo telaCadastroVeiculo;
    private final VeiculoService veiculoService;
    private int codigoVeiculo;
    private int codigoModelo;
    private int codigoProprietario;
    private String tipoProprietarioSelecionado;
    private Modelo modeloRelacionado;
    private Pessoa proprietarioRelacionado;

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {
        this.telaCadastroVeiculo = telaCadastroVeiculo;
        this.veiculoService = new VeiculoService();
        Utilities.setAlwaysDisabled(this.telaCadastroVeiculo.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroVeiculo.getjComboBoxStatus(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroVeiculo.getjFormattedTextFieldModelo(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroVeiculo.getjFormattedTextFieldProprietario(), true);
        this.preencherTiposProprietario();
        Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);
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
        this.telaCadastroVeiculo.getjButtonRelacionarProprietario().addActionListener(this);
        telaCadastroVeiculo.getjComboBoxTipoProprietario().addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                handleTipoProprietario();
            }
        });        
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
            return;
        }
        if (source == telaCadastroVeiculo.getjButtonRelacionarModelo()) {
            handleRelacionarModelo();
            return;
        }
        if (source == telaCadastroVeiculo.getjButtonRelacionarProprietario()) {
            handleRelacionarProprietario();
        }
    }

    public void preencherTiposProprietario() {
        telaCadastroVeiculo.getjComboBoxTipoProprietario().removeAllItems();
        telaCadastroVeiculo.getjComboBoxTipoProprietario().addItem(Hospede.TIPO);
        telaCadastroVeiculo.getjComboBoxTipoProprietario().addItem(Funcionario.TIPO);
        telaCadastroVeiculo.getjComboBoxTipoProprietario().addItem(Fornecedor.TIPO);
    }

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), true);
        this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        this.telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCadastroVeiculo.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroVeiculo.getjPanelDados(), false);
        this.modeloRelacionado = null;
        this.proprietarioRelacionado = null;
    }

    @Override
    public boolean isFormularioValido() {
        if (proprietarioRelacionado == null) {
            JOptionPane.showMessageDialog(null, "Selecione um Proprietário para o Veículo.");
            telaCadastroVeiculo.getjButtonRelacionarProprietario().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarPlaca(telaCadastroVeiculo.getjTextFieldPlaca().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Placa é inválido (7 caracteres).");
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
            Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), true);
            Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), false);
            return;
        }

        veiculo.setId(Integer.parseInt(telaCadastroVeiculo.getjTextFieldId().getText()));
        try {
            veiculoService.Atualizar(veiculo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), false);
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
        veiculo.setProprietario(proprietarioRelacionado);

        return veiculo;
    }

    public void handleTipoProprietario() {
        String selectedItem = (String) telaCadastroVeiculo.getjComboBoxTipoProprietario().getSelectedItem();
        if (selectedItem == null || selectedItem.equals(tipoProprietarioSelecionado)) {
            return;
        }
        telaCadastroVeiculo.getjFormattedTextFieldProprietario().setText("");
        tipoProprietarioSelecionado = selectedItem;
        telaCadastroVeiculo.getjLabelProprietario().setText(selectedItem);
    }

    public void handleRelacionarProprietario() {
        if (tipoProprietarioSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Selecione o Tipo de Proprietário.");
            telaCadastroVeiculo.getjComboBoxTipoProprietario().requestFocus();
            return;
        }
        codigoProprietario = 0;

        switch (tipoProprietarioSelecionado) {
            case Hospede.TIPO:
                relacionarProprietarioTelaHospede();
                break;
            case Funcionario.TIPO:
                relacionarProprietarioTelaFuncionario();
                break;
            case Fornecedor.TIPO:
                relacionarProprietarioTelaFornecedor();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tipo de Proprietário inválido.");
                break;
        }
        
    }

    private void relacionarProprietarioTelaHospede() {
        
        TelaBuscaHospede telaBuscaProprietario = new TelaBuscaHospede(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaHospede controllerBuscaProprietario = new ControllerBuscaHospede(telaBuscaProprietario, codigo -> this.codigoProprietario = codigo);
        telaBuscaProprietario.setVisible(true);

        if (codigoProprietario != 0) {
            Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
            this.telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");

            Hospede proprietario;
            try {
                proprietario = new HospedeService().Carregar(codigoProprietario);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.proprietarioRelacionado = proprietario;
            telaCadastroVeiculo.getjFormattedTextFieldProprietario().setText(getProprietarioFormat(proprietarioRelacionado));
        }
    }

    private void relacionarProprietarioTelaFuncionario() {

        TelaBuscaFuncionario telaBuscaProprietario = new TelaBuscaFuncionario(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaFuncionario controllerBuscaProprietario = new ControllerBuscaFuncionario(telaBuscaProprietario, codigo -> this.codigoProprietario = codigo);
        telaBuscaProprietario.setVisible(true);

        if (codigoProprietario != 0) {
            Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
            this.telaCadastroVeiculo.getjComboBoxStatus().setSelectedItem("Ativo");

            Funcionario proprietario;
            try {
                proprietario = new FuncionarioService().Carregar(codigoProprietario);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.proprietarioRelacionado = proprietario;
            telaCadastroVeiculo.getjFormattedTextFieldProprietario().setText(getProprietarioFormat(proprietarioRelacionado));
        }
    }

    private void relacionarProprietarioTelaFornecedor() {
        
        TelaBuscaFornecedor telaBuscaProprietario = new TelaBuscaFornecedor(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaFornecedor controllerBuscaProprietario = new ControllerBuscaFornecedor(telaBuscaProprietario, codigo -> this.codigoProprietario = codigo);
        telaBuscaProprietario.setVisible(true);

        if (codigoProprietario != 0) {
            Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();

            Fornecedor proprietario;
            try {
                proprietario = new FornecedorService().Carregar(codigoProprietario);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.proprietarioRelacionado = proprietario;
            telaCadastroVeiculo.getjFormattedTextFieldProprietario().setText(getProprietarioFormat(proprietarioRelacionado));
        }
    }

    private String getProprietarioFormat(Pessoa proprietario) {
        if (proprietario == null) {
            return "";
        }
        return String.format("%d - %s", proprietario.getId(), proprietario.getNome());
    }

    public void handleRelacionarModelo() {
        codigoModelo = 0;
        TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo, codigo -> this.codigoModelo = codigo);
        telaBuscaModelo.setVisible(true);

        if (codigoModelo != 0) {
            Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            this.telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();

            Modelo modelo;
            try {
                modelo = new service.ModeloService().Carregar(codigoModelo);
            } catch (RuntimeException ex) {
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
            Utilities.ativaDesativa(telaCadastroVeiculo.getjPanelBotoes(), false);
            Utilities.limpaComponentes(telaCadastroVeiculo.getjPanelDados(), true);
            telaCadastroVeiculo.getjTextFieldId().setText(String.valueOf(codigoVeiculo));

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
            this.proprietarioRelacionado = veiculo.getProprietario();
            
            if (proprietarioRelacionado instanceof Hospede) {
                telaCadastroVeiculo.getjComboBoxTipoProprietario().setSelectedItem(Hospede.TIPO);
            } else if (proprietarioRelacionado instanceof Funcionario) {
                telaCadastroVeiculo.getjComboBoxTipoProprietario().setSelectedItem(Funcionario.TIPO);
            } else if (proprietarioRelacionado instanceof Fornecedor) {
                telaCadastroVeiculo.getjComboBoxTipoProprietario().setSelectedItem(Fornecedor.TIPO);
            }
            
            telaCadastroVeiculo.getjFormattedTextFieldProprietario().setText(getProprietarioFormat(proprietarioRelacionado));
            telaCadastroVeiculo.getjTextFieldPlaca().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        telaCadastroVeiculo.dispose();
    }
}