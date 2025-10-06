package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Hospede;
import service.HospedeService;
import utilities.Utilities;
import view.TelaBuscaHospede;
import view.TelaCadastroHospede;

public final class ControllerCadHospede implements ActionListener, InterfaceControllerCad<Hospede> {

    private final TelaCadastroHospede telaCadastroHospede;
    private final HospedeService hospedeService;
    private int codigo;

    public ControllerCadHospede(TelaCadastroHospede telaCadastroHospede) {
        this.telaCadastroHospede = telaCadastroHospede;
        this.hospedeService = new HospedeService();
        Utilities.setAlwaysDisabled(this.telaCadastroHospede.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroHospede.getjComboBoxStatus(), true);
        Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
        Utilities.permiteLimparFormattedField(this.telaCadastroHospede.getjFormattedTextFieldCpf());
        Utilities.permiteLimparFormattedField(this.telaCadastroHospede.getjFormattedTextFieldCnpj());
        Utilities.permiteLimparFormattedField(this.telaCadastroHospede.getjFormattedTextFieldFone1());
        Utilities.permiteLimparFormattedField(this.telaCadastroHospede.getjFormattedTextFieldFone2());
        Utilities.permiteLimparFormattedField(this.telaCadastroHospede.getjFormattedTextFieldCep());
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCadastroHospede.getjButtonNovo().addActionListener(this);
        this.telaCadastroHospede.getjButtonCancelar().addActionListener(this);
        this.telaCadastroHospede.getjButtonGravar().addActionListener(this);
        this.telaCadastroHospede.getjButtonBuscar().addActionListener(this);
        this.telaCadastroHospede.getjButtonSair().addActionListener(this);
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

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), true);
        this.telaCadastroHospede.getjTextFieldId().setEnabled(false);
        this.telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
        telaCadastroHospede.getjTextFieldId().setEnabled(false);
        telaCadastroHospede.getjFormattedTextFieldDataCadastro().setText(Utilities.getDataHoje());
        telaCadastroHospede.getjFormattedTextFieldDataCadastro().setEnabled(false);
        telaCadastroHospede.getjComboBoxStatus().setSelectedItem("Ativo");
        telaCadastroHospede.getjComboBoxStatus().setEnabled(false);
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldNomeFantasia().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Nome Fantasia é obrigatório.");
            telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarSexo(telaCadastroHospede.getjComboBoxSexo().getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Selecione um Sexo válido.");
            telaCadastroHospede.getjComboBoxSexo().requestFocus();
            return false;
        }
        boolean cpfPreenchido = Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCpf().getText()).length() > 0;
        boolean cnpjPreenchido = Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCnpj().getText()).length() > 0;
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
        boolean fone2Preenchido = Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldFone2().getText()).length() > 0;
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
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldCidade().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Cidade é obrigatório.");
            telaCadastroHospede.getjTextFieldCidade().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldBairro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Bairro é obrigatório.");
            telaCadastroHospede.getjTextFieldBairro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroHospede.getjTextFieldLogradouro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Logradouro é obrigatório.");
            telaCadastroHospede.getjTextFieldLogradouro().requestFocus();
            return false;
        }

        if (!utilities.ValidadorCampos.validarStatus(telaCadastroHospede.getjComboBoxStatus().getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroHospede.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Hospede hospede = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroHospede.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                hospedeService.Criar(hospede);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroHospede, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Utilities.ativaDesativa(telaCadastroHospede.getjPanelBotoes(), true);
            Utilities.limpaComponentes(telaCadastroHospede.getjPanelDados(), false);
            return;
        }

        hospede.setId(Integer.parseInt(telaCadastroHospede.getjTextFieldId().getText()));
        try {
            hospedeService.Atualizar(hospede);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroHospede, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(telaCadastroHospede.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadastroHospede.getjPanelDados(), false);
    }

    @Override
    public Hospede construirDoFormulario() {
        Hospede hospede = new Hospede();
        hospede.setNome(telaCadastroHospede.getjTextFieldNomeFantasia().getText());
        hospede.setRazaoSocial(telaCadastroHospede.getjTextFieldRazaoSocial().getText());
        hospede.setCpf(Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCpf().getText()));
        hospede.setCnpj(Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCnpj().getText()));
        hospede.setRg(telaCadastroHospede.getjTextFieldRg().getText());
        hospede.setInscricaoEstadual(telaCadastroHospede.getjTextFieldInscricaoEstadual().getText());
        hospede.setFone1(Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldFone1().getText()));
        hospede.setFone2(Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldFone2().getText()));
        hospede.setEmail(telaCadastroHospede.getjTextFieldEmail().getText());
        hospede.setCep(Utilities.apenasNumeros(telaCadastroHospede.getjFormattedTextFieldCep().getText()));
        hospede.setBairro(telaCadastroHospede.getjTextFieldBairro().getText());
        hospede.setCidade(telaCadastroHospede.getjTextFieldCidade().getText());
        hospede.setLogradouro(telaCadastroHospede.getjTextFieldLogradouro().getText());
        hospede.setComplemento(telaCadastroHospede.getjTextFieldComplemento().getText());
        hospede.setContato(telaCadastroHospede.getjTextFieldContato().getText());
        hospede.setObs(telaCadastroHospede.getjTextFieldObs().getText());
        hospede.setDataCadastro(Utilities.formatarDataToSqlData(telaCadastroHospede.getjFormattedTextFieldDataCadastro().getText()));

        Object sexoSelecionado = telaCadastroHospede.getjComboBoxSexo().getSelectedItem();
        hospede.setSexo(
            sexoSelecionado != null && sexoSelecionado.equals("Masculino") ? 'M' : 'F'
        );

        Object statusSelecionado = telaCadastroHospede.getjComboBoxStatus().getSelectedItem();
        hospede.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return hospede;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaHospede telaBuscaHospede = new TelaBuscaHospede(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaHospede controllerBuscaHospede = new ControllerBuscaHospede(telaBuscaHospede, valor -> this.codigo = valor);
        telaBuscaHospede.setVisible(true);

        if (codigo != 0) {
            Utilities.ativaDesativa(telaCadastroHospede.getjPanelBotoes(), false);
            Utilities.limpaComponentes(telaCadastroHospede.getjPanelDados(), true);

            telaCadastroHospede.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroHospede.getjTextFieldId().setEnabled(false);

            Hospede hospede;
            try {
                hospede = new HospedeService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroHospede, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroHospede.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarDataFromSqlData(hospede.getDataCadastro()));
            telaCadastroHospede.getjFormattedTextFieldDataCadastro().setEnabled(false);
            telaCadastroHospede.getjTextFieldNomeFantasia().setText(hospede.getNome());
            telaCadastroHospede.getjTextFieldRazaoSocial().setText(hospede.getRazaoSocial());
            telaCadastroHospede.getjFormattedTextFieldCpf().setText(Utilities.formatarCpf(hospede.getCpf()));
            telaCadastroHospede.getjFormattedTextFieldCnpj().setText(Utilities.formatarCnpj(hospede.getCnpj()));
            telaCadastroHospede.getjTextFieldRg().setText(hospede.getRg());
            telaCadastroHospede.getjTextFieldInscricaoEstadual().setText(hospede.getInscricaoEstadual());
            telaCadastroHospede.getjFormattedTextFieldFone1().setText(Utilities.formatarFone(hospede.getFone1()));
            telaCadastroHospede.getjFormattedTextFieldFone2().setText(Utilities.formatarFone(hospede.getFone2()));
            telaCadastroHospede.getjTextFieldEmail().setText(hospede.getEmail());
            telaCadastroHospede.getjFormattedTextFieldCep().setText(Utilities.formatarCep(hospede.getCep()));
            telaCadastroHospede.getjTextFieldBairro().setText(hospede.getBairro());
            telaCadastroHospede.getjTextFieldCidade().setText(hospede.getCidade());
            telaCadastroHospede.getjTextFieldLogradouro().setText(hospede.getLogradouro());
            telaCadastroHospede.getjTextFieldComplemento().setText(hospede.getComplemento());
            telaCadastroHospede.getjTextFieldContato().setText(hospede.getContato());
            telaCadastroHospede.getjTextFieldObs().setText(hospede.getObs());

            telaCadastroHospede.getjComboBoxSexo().setSelectedItem(
                hospede.getSexo() == 'M' ? "Masculino" : "Feminino"
            );
            
            telaCadastroHospede.getjComboBoxStatus().setSelectedItem(
                hospede.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        telaCadastroHospede.dispose();
    }
}
