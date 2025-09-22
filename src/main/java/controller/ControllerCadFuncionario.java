package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Funcionario;
import service.FuncionarioService;
import service.InterfaceService;
import view.TelaBuscaFuncionario;
import view.TelaCadastroFuncionario;

public class ControllerCadFuncionario implements ActionListener, InterfaceControllerCad<Funcionario> {

    TelaCadastroFuncionario telaCadastroFuncionario;
    InterfaceService<Funcionario> funcionarioService;
    public static int codigo;

    public ControllerCadFuncionario(TelaCadastroFuncionario telaCadastroFuncionario) {
        this.telaCadastroFuncionario = telaCadastroFuncionario;
        this.funcionarioService = new FuncionarioService();
        utilities.Utilities.ativaDesativa(this.telaCadastroFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFuncionario.getjPanelDados(), false);

        initListeners();
    }

    private void initListeners() {
        this.telaCadastroFuncionario.getjButtonNovo().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonCancelar().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonGravar().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonBuscar().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroFuncionario.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroFuncionario.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroFuncionario.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroFuncionario.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroFuncionario.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(telaCadastroFuncionario.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(telaCadastroFuncionario.getjPanelDados(), true);
        telaCadastroFuncionario.getjTextFieldUsuario().requestFocus();
        telaCadastroFuncionario.getjTextFieldId().setEnabled(false);
        telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setText(utilities.Utilities.getDataHoje());
        telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setEnabled(false);
        telaCadastroFuncionario.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroFuncionario.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(telaCadastroFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroFuncionario.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFuncionario.getjTextFieldNome().getText())) {
            JOptionPane.showMessageDialog(null, "O Atributo Nome é Obrigatório.");
            telaCadastroFuncionario.getjTextFieldNome().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCpf(telaCadastroFuncionario.getjFormattedTextFieldCpf().getText())) {
            JOptionPane.showMessageDialog(null, "O Atributo CPF é Inválido.");
            telaCadastroFuncionario.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFuncionario.getjTextFieldRg().getText())) {
            JOptionPane.showMessageDialog(null, "O Atributo RG é Obrigatório.");
            telaCadastroFuncionario.getjTextFieldRg().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarSexo(telaCadastroFuncionario.getjComboBoxSexo().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "O atributo Sexo é Inválido.");
            telaCadastroFuncionario.getjComboBoxSexo().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroFuncionario.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "O atributo Status é Inválido.");
            telaCadastroFuncionario.getjComboBoxStatus().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoUsuario(telaCadastroFuncionario.getjTextFieldUsuario().getText())) {
            JOptionPane.showMessageDialog(null, "O atributo Usuário é Inválido.\n(De 5 a 20 caracteres. Apenas letras, números, '.' e '_' são permitidos)");
            telaCadastroFuncionario.getjTextFieldUsuario().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarSenha(telaCadastroFuncionario.getjPasswordFieldSenha().getPassword())) {
            JOptionPane.showMessageDialog(null, "A Senha é obrigatória.");
            telaCadastroFuncionario.getjPasswordFieldSenha().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarFone(telaCadastroFuncionario.getjFormattedTextFieldFone1().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Fone1 é inválido.");
            telaCadastroFuncionario.getjFormattedTextFieldFone1().requestFocus();
            return false;
        }
        boolean fone2Preenchido = utilities.Utilities.apenasNumeros(telaCadastroFuncionario.getjFormattedTextFieldFone2().getText()).length() > 0;
        if (fone2Preenchido && !utilities.ValidadorCampos.validarFone(telaCadastroFuncionario.getjFormattedTextFieldFone2().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Fone2 é inválido.");
            telaCadastroFuncionario.getjFormattedTextFieldFone2().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoEmail(telaCadastroFuncionario.getjTextFieldEmail().getText())) {
            JOptionPane.showMessageDialog(null, "O atributo Email é Inválido.");
            telaCadastroFuncionario.getjTextFieldEmail().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCep(telaCadastroFuncionario.getjFormattedTextFieldCep().getText())) {
            JOptionPane.showMessageDialog(null, "O CEP é obrigatório.");
            telaCadastroFuncionario.getjFormattedTextFieldCep().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFuncionario.getjTextFieldBairro().getText())) {
            JOptionPane.showMessageDialog(null, "O Bairro é obrigatório.");
            telaCadastroFuncionario.getjTextFieldBairro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFuncionario.getjTextFieldCidade().getText())) {
            JOptionPane.showMessageDialog(null, "A Cidade é obrigatória.");
            telaCadastroFuncionario.getjTextFieldCidade().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFuncionario.getjTextFieldLogradouro().getText())) {
            JOptionPane.showMessageDialog(null, "O Logradouro é obrigatório.");
            telaCadastroFuncionario.getjTextFieldLogradouro().requestFocus();
            return false;
        }

        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Funcionario funcionario = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroFuncionario.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                funcionarioService.Criar(funcionario);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroFuncionario, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroFuncionario.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroFuncionario.getjPanelDados(), false);
            return;
        }

        funcionario.setId(Integer.parseInt(telaCadastroFuncionario.getjTextFieldId().getText()));
        try {
            funcionarioService.Atualizar(funcionario);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroFuncionario, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroFuncionario.getjPanelDados(), false);
    }

    private Funcionario construirDoFormulario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(telaCadastroFuncionario.getjTextFieldNome().getText());
        funcionario.setCpf(utilities.Utilities.apenasNumeros(telaCadastroFuncionario.getjFormattedTextFieldCpf().getText()));
        funcionario.setRg(telaCadastroFuncionario.getjTextFieldRg().getText());
        funcionario.setObs(telaCadastroFuncionario.getjTextFieldObs().getText());

        Object sexoSelecionado = telaCadastroFuncionario.getjComboBoxSexo().getSelectedItem();
        funcionario.setSexo(
            sexoSelecionado != null && sexoSelecionado.equals("Masculino") ? 'M' : 'F'
        );

        Object statusSelecionado = telaCadastroFuncionario.getjComboBoxStatus().getSelectedItem();
        funcionario.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        funcionario.setUsuario(telaCadastroFuncionario.getjTextFieldUsuario().getText());
        funcionario.setSenha(new String(telaCadastroFuncionario.getjPasswordFieldSenha().getPassword()));
        funcionario.setFone1(utilities.Utilities.apenasNumeros(telaCadastroFuncionario.getjFormattedTextFieldFone1().getText()));
        funcionario.setFone2(utilities.Utilities.apenasNumeros(telaCadastroFuncionario.getjFormattedTextFieldFone2().getText()));
        funcionario.setEmail(telaCadastroFuncionario.getjTextFieldEmail().getText());
        funcionario.setCep(utilities.Utilities.apenasNumeros(telaCadastroFuncionario.getjFormattedTextFieldCep().getText()));
        funcionario.setBairro(telaCadastroFuncionario.getjTextFieldBairro().getText());
        funcionario.setCidade(telaCadastroFuncionario.getjTextFieldCidade().getText());
        funcionario.setLogradouro(telaCadastroFuncionario.getjTextFieldLogradouro().getText());
        funcionario.setComplemento(telaCadastroFuncionario.getjTextFieldComplemento().getText());

        return funcionario;
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaFuncionario telaBuscaFuncionario = new TelaBuscaFuncionario(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaFuncionario controllerBuscaFuncionario = new ControllerBuscaFuncionario(telaBuscaFuncionario);
        telaBuscaFuncionario.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroFuncionario.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroFuncionario.getjPanelDados(), true);

            telaCadastroFuncionario.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroFuncionario.getjTextFieldId().setEnabled(false);

            Funcionario funcionario;
            try {
                funcionario = funcionarioService.Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroFuncionario, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setText(utilities.Utilities.formatarDataFromSqlData(funcionario.getDataCadastro()));
            telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setEnabled(false);
            telaCadastroFuncionario.getjTextFieldNome().setText(funcionario.getNome());
            telaCadastroFuncionario.getjFormattedTextFieldCpf().setText(funcionario.getCpf());
            telaCadastroFuncionario.getjTextFieldRg().setText(funcionario.getRg());
            telaCadastroFuncionario.getjTextFieldObs().setText(funcionario.getObs());
            telaCadastroFuncionario.getjComboBoxSexo().setSelectedItem(
                    funcionario.getSexo() == 'M' ? "Masculino" : "Feminino"
            );
            telaCadastroFuncionario.getjComboBoxStatus().setSelectedItem(
                    funcionario.getStatus() == 'A' ? "Ativo" : "Inativo"
            );
            telaCadastroFuncionario.getjTextFieldUsuario().setText(funcionario.getUsuario());
            telaCadastroFuncionario.getjPasswordFieldSenha().setText(funcionario.getSenha());
            telaCadastroFuncionario.getjFormattedTextFieldFone1().setText(utilities.Utilities.formatarFone(funcionario.getFone1()));
            telaCadastroFuncionario.getjFormattedTextFieldFone2().setText(utilities.Utilities.formatarFone(funcionario.getFone2()));
            telaCadastroFuncionario.getjTextFieldEmail().setText(funcionario.getEmail());
            telaCadastroFuncionario.getjFormattedTextFieldCep().setText(utilities.Utilities.formatarCep(funcionario.getCep()));
            telaCadastroFuncionario.getjTextFieldBairro().setText(funcionario.getBairro());
            telaCadastroFuncionario.getjTextFieldCidade().setText(funcionario.getCidade());
            telaCadastroFuncionario.getjTextFieldLogradouro().setText(funcionario.getLogradouro());
            telaCadastroFuncionario.getjTextFieldComplemento().setText(funcionario.getComplemento());

            telaCadastroFuncionario.getjTextFieldNome().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroFuncionario.dispose();
    }
}
