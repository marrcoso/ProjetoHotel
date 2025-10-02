package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;
import view.TelaCadastroFornecedor;

public class ControllerCadFornecedor implements ActionListener, InterfaceControllerCad<Fornecedor> {

    private final TelaCadastroFornecedor telaCadastroFornecedor;
    private final FornecedorService fornecedorService;
    public static int codigo;

    public ControllerCadFornecedor(TelaCadastroFornecedor telaCadastroFornecedor) {
        this.telaCadastroFornecedor = telaCadastroFornecedor;
        this.fornecedorService = new FornecedorService();
        utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);

        initListeners();
    }

    private void initListeners() {
        this.telaCadastroFornecedor.getjButtonNovo().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonCancelar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonGravar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonBuscar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroFornecedor.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroFornecedor.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroFornecedor.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroFornecedor.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroFornecedor.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), true);
        this.telaCadastroFornecedor.getjTextFieldId().setEnabled(false);
        this.telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
        this.telaCadastroFornecedor.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroFornecedor.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldNomeFantasia().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Nome é obrigatório.");
            telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoEmail(telaCadastroFornecedor.getjTextFieldEmail().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Email é inválido.");
            telaCadastroFornecedor.getjTextFieldEmail().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarFone(telaCadastroFornecedor.getjFormattedTextFieldFone1().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Fone1 é inválido.");
            telaCadastroFornecedor.getjFormattedTextFieldFone1().requestFocus();
            return false;
        }
        boolean fone2Preenchido = utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone2().getText()).length() > 0;
        if (fone2Preenchido && !utilities.ValidadorCampos.validarFone(telaCadastroFornecedor.getjFormattedTextFieldFone2().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Fone2 é inválido.");
            telaCadastroFornecedor.getjFormattedTextFieldFone2().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCep(telaCadastroFornecedor.getjFormattedTextFieldCep().getText())) {
            JOptionPane.showMessageDialog(null, "O campo CEP é obrigatório.");
            telaCadastroFornecedor.getjFormattedTextFieldCep().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldBairro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Bairro é obrigatório.");
            telaCadastroFornecedor.getjTextFieldBairro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldCidade().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Cidade é obrigatório.");
            telaCadastroFornecedor.getjTextFieldCidade().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldLogradouro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Logradouro é obrigatório.");
            telaCadastroFornecedor.getjTextFieldLogradouro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroFornecedor.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroFornecedor.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Fornecedor fornecedor = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroFornecedor.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                fornecedorService.Criar(fornecedor);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), false);
            return;
        }

        fornecedor.setId(Integer.parseInt(telaCadastroFornecedor.getjTextFieldId().getText()));
        try {
            fornecedorService.Atualizar(fornecedor);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), false);
    }

    private Fornecedor construirDoFormulario() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(telaCadastroFornecedor.getjTextFieldNomeFantasia().getText());
        fornecedor.setRazaoSocial(telaCadastroFornecedor.getjTextFieldRazaoSocial().getText());
        fornecedor.setCnpj(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCnpj().getText()));
        fornecedor.setInscricaoEstadual(telaCadastroFornecedor.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setFone1(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone1().getText()));
        fornecedor.setFone2(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone2().getText()));
        fornecedor.setEmail(telaCadastroFornecedor.getjTextFieldEmail().getText());
        fornecedor.setCep(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCep().getText()));
        fornecedor.setBairro(telaCadastroFornecedor.getjTextFieldBairro().getText());
        fornecedor.setCidade(telaCadastroFornecedor.getjTextFieldCidade().getText());
        fornecedor.setLogradouro(telaCadastroFornecedor.getjTextFieldLogradouro().getText());
        fornecedor.setComplemento(telaCadastroFornecedor.getjTextFieldComplemento().getText());
        fornecedor.setObs(telaCadastroFornecedor.getjTextFieldObs().getText());

        Object statusSelecionado = telaCadastroFornecedor.getjComboBoxStatus().getSelectedItem();
        fornecedor.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return fornecedor;
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaFornecedor telaBuscaFornecedor = new TelaBuscaFornecedor(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaFornecedor controllerBuscaFornecedor = new ControllerBuscaFornecedor(telaBuscaFornecedor);
        telaBuscaFornecedor.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), true);

            telaCadastroFornecedor.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroFornecedor.getjTextFieldId().setEnabled(false);

            Fornecedor fornecedor;
            try {
                fornecedor = new FornecedorService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroFornecedor.getjTextFieldNomeFantasia().setText(fornecedor.getNome());
            telaCadastroFornecedor.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
            telaCadastroFornecedor.getjFormattedTextFieldCnpj().setText(fornecedor.getCnpj());
            telaCadastroFornecedor.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
            telaCadastroFornecedor.getjFormattedTextFieldFone1().setText(utilities.Utilities.formatarFone(fornecedor.getFone1()));
            telaCadastroFornecedor.getjFormattedTextFieldFone2().setText(utilities.Utilities.formatarFone(fornecedor.getFone2()));
            telaCadastroFornecedor.getjTextFieldEmail().setText(fornecedor.getEmail());
            telaCadastroFornecedor.getjFormattedTextFieldCep().setText(utilities.Utilities.formatarCep(fornecedor.getCep()));
            telaCadastroFornecedor.getjTextFieldBairro().setText(fornecedor.getBairro());
            telaCadastroFornecedor.getjTextFieldCidade().setText(fornecedor.getCidade());
            telaCadastroFornecedor.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
            telaCadastroFornecedor.getjTextFieldComplemento().setText(fornecedor.getComplemento());
            telaCadastroFornecedor.getjTextFieldObs().setText(fornecedor.getObs());

            telaCadastroFornecedor.getjComboBoxStatus().setSelectedItem(
                fornecedor.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroFornecedor.dispose();
    }
}