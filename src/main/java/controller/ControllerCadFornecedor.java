package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;
import view.TelaCadastroFornecedor;

public class ControllerCadFornecedor implements ActionListener {

    TelaCadastroFornecedor telaCadastroFornecedor;
    public static int codigo;

    public ControllerCadFornecedor(TelaCadastroFornecedor telaCadastroFornecedor) {
        this.telaCadastroFornecedor = telaCadastroFornecedor;

        this.telaCadastroFornecedor.getjButtonNovo().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonCancelar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonGravar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonBuscar().addActionListener(this);
        this.telaCadastroFornecedor.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
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
        telaCadastroFornecedor.getjTextFieldId().setEnabled(false);
        telaCadastroFornecedor.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroFornecedor.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldNomeFantasia().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Nome Fantasia é obrigatório.");
            telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
            return false;
        }
        boolean cpfPreenchido = utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCpf().getText()).length() > 0;
        boolean cnpjPreenchido = utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCnpj().getText()).length() > 0;
        if (!cpfPreenchido && !cnpjPreenchido) {
            JOptionPane.showMessageDialog(null, "Preencha ao menos um dos campos: CPF ou CNPJ.");
            telaCadastroFornecedor.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }
        if (cpfPreenchido && !utilities.ValidadorCampos.validarCpf(telaCadastroFornecedor.getjFormattedTextFieldCpf().getText())) {
            JOptionPane.showMessageDialog(null, "O campo CPF é inválido.");
            telaCadastroFornecedor.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }
        if (cnpjPreenchido && !utilities.ValidadorCampos.validarCnpj(telaCadastroFornecedor.getjFormattedTextFieldCnpj().getText())) {
            JOptionPane.showMessageDialog(null, "O campo CNPJ é inválido.");
            telaCadastroFornecedor.getjFormattedTextFieldCnpj().requestFocus();
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
        if (!utilities.ValidadorCampos.validarCampoEmail(telaCadastroFornecedor.getjTextFieldEmail().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Email é inválido.");
            telaCadastroFornecedor.getjTextFieldEmail().requestFocus();
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
        if (!utilities.ValidadorCampos.validarSexo(telaCadastroFornecedor.getjComboBoxSexo().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Sexo válido.");
            telaCadastroFornecedor.getjComboBoxSexo().requestFocus();
            return false;
        }
        // Os campos complemento, fone2, contato e obs podem ser opcionais
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(telaCadastroFornecedor.getjTextFieldNomeFantasia().getText());
        fornecedor.setRazaoSocial(telaCadastroFornecedor.getjTextFieldRazaoSocial().getText());
        fornecedor.setCpf(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCpf().getText()));
        fornecedor.setCnpj(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCnpj().getText()));
        fornecedor.setRg(telaCadastroFornecedor.getjTextFieldRg().getText());
        fornecedor.setInscricaoEstadual(telaCadastroFornecedor.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setFone1(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone1().getText()));
        fornecedor.setFone2(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone2().getText()));
        fornecedor.setEmail(telaCadastroFornecedor.getjTextFieldEmail().getText());
        fornecedor.setCep(utilities.Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCep().getText()));
        fornecedor.setBairro(telaCadastroFornecedor.getjTextFieldBairro().getText());
        fornecedor.setCidade(telaCadastroFornecedor.getjTextFieldCidade().getText());
        fornecedor.setLogradouro(telaCadastroFornecedor.getjTextFieldLogradouro().getText());
        fornecedor.setComplemento(telaCadastroFornecedor.getjTextFieldComplemento().getText());
        fornecedor.setContato(telaCadastroFornecedor.getjTextFieldContato().getText());
        fornecedor.setObs(telaCadastroFornecedor.getjTextFieldObs().getText());

        fornecedor.setSexo(
            telaCadastroFornecedor.getjComboBoxSexo().getSelectedItem().equals("Masculino") ? 'M' : 'F'
        );
       
        fornecedor.setStatus(
            telaCadastroFornecedor.getjComboBoxStatus().getSelectedItem().equals("Ativo") ? 'A' : 'I'
        );

        if (telaCadastroFornecedor.getjTextFieldId().getText().trim().isEmpty()) {
            FornecedorService.Criar(fornecedor);
        } else {
            fornecedor.setId(Integer.parseInt(telaCadastroFornecedor.getjTextFieldId().getText()));
            FornecedorService.Atualizar(fornecedor);
        }

        utilities.Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), false);
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaFornecedor telaBuscaFornecedor = new TelaBuscaFornecedor(null, true);
        ControllerBuscaFornecedor controllerBuscaFornecedor = new ControllerBuscaFornecedor(telaBuscaFornecedor);
        telaBuscaFornecedor.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), true);

            telaCadastroFornecedor.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroFornecedor.getjTextFieldId().setEnabled(false);

            Fornecedor fornecedor = FornecedorService.Carregar(codigo);

            telaCadastroFornecedor.getjTextFieldNomeFantasia().setText(fornecedor.getNome());
            telaCadastroFornecedor.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
            telaCadastroFornecedor.getjFormattedTextFieldCpf().setText(utilities.Utilities.formatarCpf(fornecedor.getCpf()));
            telaCadastroFornecedor.getjFormattedTextFieldCnpj().setText(utilities.Utilities.formatarCnpj(fornecedor.getCnpj()));
            telaCadastroFornecedor.getjTextFieldRg().setText(fornecedor.getRg());
            telaCadastroFornecedor.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
            telaCadastroFornecedor.getjFormattedTextFieldFone1().setText(utilities.Utilities.formatarFone(fornecedor.getFone1()));
            telaCadastroFornecedor.getjFormattedTextFieldFone2().setText(utilities.Utilities.formatarFone(fornecedor.getFone2()));
            telaCadastroFornecedor.getjTextFieldEmail().setText(fornecedor.getEmail());
            telaCadastroFornecedor.getjFormattedTextFieldCep().setText(utilities.Utilities.formatarCep(fornecedor.getCep()));
            telaCadastroFornecedor.getjTextFieldBairro().setText(fornecedor.getBairro());
            telaCadastroFornecedor.getjTextFieldCidade().setText(fornecedor.getCidade());
            telaCadastroFornecedor.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
            telaCadastroFornecedor.getjTextFieldComplemento().setText(fornecedor.getComplemento());
            telaCadastroFornecedor.getjTextFieldContato().setText(fornecedor.getContato());
            telaCadastroFornecedor.getjTextFieldObs().setText(fornecedor.getObs());

            telaCadastroFornecedor.getjComboBoxSexo().setSelectedItem(
                fornecedor.getSexo() == 'M' ? "Masculino" : "Feminino"
            );
            
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
