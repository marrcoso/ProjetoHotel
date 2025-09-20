package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Hospede;
import service.HospedeService;
import view.TelaBuscaHospede;
import view.TelaCadastroHospede;

public class ControllerCadHospede implements ActionListener {

    TelaCadastroHospede telaCadastroHospede;
    public static int codigo;

    public ControllerCadHospede(TelaCadastroHospede telaCadastroHospede) {
        this.telaCadastroHospede = telaCadastroHospede;

        this.telaCadastroHospede.getjButtonNovo().addActionListener(this);
        this.telaCadastroHospede.getjButtonCancelar().addActionListener(this);
        this.telaCadastroHospede.getjButtonGravar().addActionListener(this);
        this.telaCadastroHospede.getjButtonBuscar().addActionListener(this);
        this.telaCadastroHospede.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroHospede.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroHospede.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroHospede.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroHospede.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroHospede.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), true);
        this.telaCadastroHospede.getjTextFieldId().setEnabled(false);
        this.telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
        telaCadastroHospede.getjTextFieldId().setEnabled(false);
        telaCadastroHospede.getjFormattedTextFieldDataCadastro().setText(utilities.Utilities.getDataHoje());
        telaCadastroHospede.getjFormattedTextFieldDataCadastro().setEnabled(false);
        telaCadastroHospede.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroHospede.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldNomeFantasia().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Nome Fantasia é obrigatório.");
            telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
            return false;
        }
        boolean cpfPreenchido = utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCpf().getText()).length() > 0;
        boolean cnpjPreenchido = utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCnpj().getText()).length() > 0;
        if (!cpfPreenchido && !cnpjPreenchido) {
            JOptionPane.showMessageDialog(null, "Preencha ao menos um dos campos: CPF ou CNPJ.");
            telaCadastroHospede.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }
        if (cpfPreenchido && !utilities.ValidadorCampos.validarCpf(telaCadastroHospede.getjFormattedTextFieldCpf().getText())) {
            JOptionPane.showMessageDialog(null, "O campo CPF é inválido.");
            telaCadastroHospede.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }
        if (cnpjPreenchido && !utilities.ValidadorCampos.validarCnpj(telaCadastroHospede.getjFormattedTextFieldCnpj().getText())) {
            JOptionPane.showMessageDialog(null, "O campo CNPJ é inválido.");
            telaCadastroHospede.getjFormattedTextFieldCnpj().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarFone(telaCadastroHospede.getjFormattedTextFieldFone1().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Fone1 é inválido.");
            telaCadastroHospede.getjFormattedTextFieldFone1().requestFocus();
            return false;
        }
        boolean fone2Preenchido = utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldFone2().getText()).length() > 0;
        if (fone2Preenchido && !utilities.ValidadorCampos.validarFone(telaCadastroHospede.getjFormattedTextFieldFone2().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Fone2 é inválido.");
            telaCadastroHospede.getjFormattedTextFieldFone2().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoEmail(telaCadastroHospede.getjTextFieldEmail().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Email é inválido.");
            telaCadastroHospede.getjTextFieldEmail().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCep(telaCadastroHospede.getjFormattedTextFieldCep().getText())) {
            JOptionPane.showMessageDialog(null, "O campo CEP é obrigatório.");
            telaCadastroHospede.getjFormattedTextFieldCep().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldBairro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Bairro é obrigatório.");
            telaCadastroHospede.getjTextFieldBairro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldCidade().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Cidade é obrigatório.");
            telaCadastroHospede.getjTextFieldCidade().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldLogradouro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Logradouro é obrigatório.");
            telaCadastroHospede.getjTextFieldLogradouro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarSexo(telaCadastroHospede.getjComboBoxSexo().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Sexo válido.");
            telaCadastroHospede.getjComboBoxSexo().requestFocus();
            return false;
        }
        // Os campos complemento, fone2, contato e obs podem ser opcionais
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Hospede hospede = new Hospede();
        hospede.setNome(telaCadastroHospede.getjTextFieldNomeFantasia().getText());
        hospede.setRazaoSocial(telaCadastroHospede.getjTextFieldRazaoSocial().getText());
        hospede.setCpf(utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCpf().getText()));
        hospede.setCnpj(utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCnpj().getText()));
        hospede.setRg(telaCadastroHospede.getjTextFieldRg().getText());
        hospede.setInscricaoEstadual(telaCadastroHospede.getjTextFieldInscricaoEstadual().getText());
        hospede.setFone1(utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldFone1().getText()));
        hospede.setFone2(utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldFone2().getText()));
        hospede.setEmail(telaCadastroHospede.getjTextFieldEmail().getText());
        hospede.setCep(utilities.Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCep().getText()));
        hospede.setBairro(telaCadastroHospede.getjTextFieldBairro().getText());
        hospede.setCidade(telaCadastroHospede.getjTextFieldCidade().getText());
        hospede.setLogradouro(telaCadastroHospede.getjTextFieldLogradouro().getText());
        hospede.setComplemento(telaCadastroHospede.getjTextFieldComplemento().getText());
        hospede.setContato(telaCadastroHospede.getjTextFieldContato().getText());
        hospede.setObs(telaCadastroHospede.getjTextFieldObs().getText());

        hospede.setSexo(
            telaCadastroHospede.getjComboBoxSexo().getSelectedItem().equals("Masculino") ? 'M' : 'F'
        );
       
        hospede.setStatus(
            telaCadastroHospede.getjComboBoxStatus().getSelectedItem().equals("Ativo") ? 'A' : 'I'
        );

        if (telaCadastroHospede.getjTextFieldId().getText().trim().isEmpty()) {
            HospedeService.Criar(hospede);
        } else {
            hospede.setId(Integer.parseInt(telaCadastroHospede.getjTextFieldId().getText()));
            HospedeService.Atualizar(hospede);
        }

        utilities.Utilities.ativaDesativa(telaCadastroHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroHospede.getjPanelDados(), false);
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaHospede telaBuscaHospede = new TelaBuscaHospede(null, true);
        ControllerBuscaHospede controllerBuscaHospede = new ControllerBuscaHospede(telaBuscaHospede);
        telaBuscaHospede.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroHospede.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroHospede.getjPanelDados(), true);

            telaCadastroHospede.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroHospede.getjTextFieldId().setEnabled(false);

            Hospede hospede = HospedeService.Carregar(codigo);

            telaCadastroHospede.getjFormattedTextFieldDataCadastro().setText(utilities.Utilities.formatarDataFromSqlData(hospede.getDataCadastro()));
            telaCadastroHospede.getjFormattedTextFieldDataCadastro().setEnabled(false);
            telaCadastroHospede.getjTextFieldNomeFantasia().setText(hospede.getNome());
            telaCadastroHospede.getjTextFieldRazaoSocial().setText(hospede.getRazaoSocial());
            telaCadastroHospede.getjFormattedTextFieldCpf().setText(utilities.Utilities.formatarCpf(hospede.getCpf()));
            telaCadastroHospede.getjFormattedTextFieldCnpj().setText(utilities.Utilities.formatarCnpj(hospede.getCnpj()));
            telaCadastroHospede.getjTextFieldRg().setText(hospede.getRg());
            telaCadastroHospede.getjTextFieldInscricaoEstadual().setText(hospede.getInscricaoEstadual());
            telaCadastroHospede.getjFormattedTextFieldFone1().setText(utilities.Utilities.formatarFone(hospede.getFone1()));
            telaCadastroHospede.getjFormattedTextFieldFone2().setText(utilities.Utilities.formatarFone(hospede.getFone2()));
            telaCadastroHospede.getjTextFieldEmail().setText(hospede.getEmail());
            telaCadastroHospede.getjFormattedTextFieldCep().setText(utilities.Utilities.formatarCep(hospede.getCep()));
            telaCadastroHospede.getjTextFieldBairro().setText(hospede.getBairro());
            telaCadastroHospede.getjTextFieldCidade().setText(hospede.getCidade());
            telaCadastroHospede.getjTextFieldLogradouro().setText(hospede.getLogradouro());
            telaCadastroHospede.getjTextFieldComplemento().setText(hospede.getComplemento());
            telaCadastroHospede.getjTextFieldContato().setText(hospede.getContato());
            telaCadastroHospede.getjTextFieldObs().setText(hospede.getObs());
            telaCadastroHospede.getjFormattedTextFieldDataCadastro().setText(hospede.getDataCadastro());

            telaCadastroHospede.getjComboBoxSexo().setSelectedItem(
                hospede.getSexo() == 'M' ? "Masculino" : "Feminino"
            );
            
            telaCadastroHospede.getjComboBoxStatus().setSelectedItem(
                hospede.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroHospede.dispose();
    }
}
