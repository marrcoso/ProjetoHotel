package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Fornecedor;
import service.FornecedorService;
import utilities.Utilities;
import view.TelaBuscaFornecedor;
import view.TelaCadastroFornecedor;

public final class ControllerCadFornecedor implements ActionListener, InterfaceControllerCad<Fornecedor> {

    private final TelaCadastroFornecedor telaCadastroFornecedor;
    private final FornecedorService fornecedorService;
    private int codigo;

    public ControllerCadFornecedor(TelaCadastroFornecedor telaCadastroFornecedor) {
        this.telaCadastroFornecedor = telaCadastroFornecedor;
        this.fornecedorService = new FornecedorService();
        Utilities.setAlwaysDisabled(this.telaCadastroFornecedor.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroFornecedor.getjComboBoxStatus(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroFornecedor.getjFormattedTextFieldDataCadastro(), true);
        Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
        Utilities.permiteLimparFormattedField(this.telaCadastroFornecedor.getjFormattedTextFieldCpf());
        Utilities.permiteLimparFormattedField(this.telaCadastroFornecedor.getjFormattedTextFieldCep());
        Utilities.permiteLimparFormattedField(this.telaCadastroFornecedor.getjFormattedTextFieldCnpj());
        Utilities.permiteLimparFormattedField(this.telaCadastroFornecedor.getjFormattedTextFieldFone1());
        Utilities.permiteLimparFormattedField(this.telaCadastroFornecedor.getjFormattedTextFieldFone2());
        initListeners();
    }

    @Override
    public void initListeners() {
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

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), true);
        this.telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
        this.telaCadastroFornecedor.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroFornecedor.getjFormattedTextFieldDataCadastro().setText(Utilities.getDataHoje());
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCadastroFornecedor.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroFornecedor.getjPanelDados(), false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldNomeFantasia().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Nome Fantasia é obrigatório.");
            telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarSexo(telaCadastroFornecedor.getjComboBoxSexo().getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Selecione um Sexo válido.");
            telaCadastroFornecedor.getjComboBoxSexo().requestFocus();
            return false;
        }
        boolean cpfPreenchido = Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCpf().getText()).length() > 0;
        boolean cnpjPreenchido = Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCnpj().getText()).length() > 0;
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
        boolean fone2Preenchido = Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone2().getText()).length() > 0;
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
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldCidade().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Cidade é obrigatório.");
            telaCadastroFornecedor.getjTextFieldCidade().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldBairro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Bairro é obrigatório.");
            telaCadastroFornecedor.getjTextFieldBairro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroFornecedor.getjTextFieldLogradouro().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Logradouro é obrigatório.");
            telaCadastroFornecedor.getjTextFieldLogradouro().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroFornecedor.getjComboBoxStatus().getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroFornecedor.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
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
            Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), true);
            Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), false);
            return;
        }

        fornecedor.setId(Integer.parseInt(telaCadastroFornecedor.getjTextFieldId().getText()));
        try {
            fornecedorService.Atualizar(fornecedor);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), false);
    }

    @Override
    public Fornecedor construirDoFormulario() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(telaCadastroFornecedor.getjTextFieldNomeFantasia().getText());
        fornecedor.setRazaoSocial(telaCadastroFornecedor.getjTextFieldRazaoSocial().getText());
        fornecedor.setRg(telaCadastroFornecedor.getjTextFieldRg().getText());
        fornecedor.setCpf(Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCpf().getText()));
        fornecedor.setInscricaoEstadual(telaCadastroFornecedor.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setCnpj(Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCnpj().getText()));
        fornecedor.setFone1(Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone1().getText()));
        fornecedor.setFone2(Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldFone2().getText()));
        fornecedor.setEmail(telaCadastroFornecedor.getjTextFieldEmail().getText());
        fornecedor.setCep(Utilities.apenasNumeros(telaCadastroFornecedor.getjFormattedTextFieldCep().getText()));
        fornecedor.setCidade(telaCadastroFornecedor.getjTextFieldCidade().getText());
        fornecedor.setBairro(telaCadastroFornecedor.getjTextFieldBairro().getText());
        fornecedor.setLogradouro(telaCadastroFornecedor.getjTextFieldLogradouro().getText());
        fornecedor.setComplemento(telaCadastroFornecedor.getjTextFieldComplemento().getText());
        fornecedor.setObs(telaCadastroFornecedor.getjTextFieldObs().getText());
        fornecedor.setContato(telaCadastroFornecedor.getjTextFieldContato().getText());
        fornecedor.setDataCadastro(Utilities.formatarDataToSqlData(telaCadastroFornecedor.getjFormattedTextFieldDataCadastro().getText()));

        Object sexoSelecionado = telaCadastroFornecedor.getjComboBoxSexo().getSelectedItem();
        fornecedor.setSexo(
            sexoSelecionado != null && sexoSelecionado.equals("Masculino") ? 'M' : 'F'
        );

        Object statusSelecionado = telaCadastroFornecedor.getjComboBoxStatus().getSelectedItem();
        fornecedor.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return fornecedor;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaFornecedor telaBuscaFornecedor = new TelaBuscaFornecedor(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaFornecedor controllerBuscaFornecedor = new ControllerBuscaFornecedor(telaBuscaFornecedor, valor -> this.codigo = valor);
        telaBuscaFornecedor.setVisible(true);

        if (codigo != 0) {
            Utilities.ativaDesativa(telaCadastroFornecedor.getjPanelBotoes(), false);
            Utilities.limpaComponentes(telaCadastroFornecedor.getjPanelDados(), true);

            
            Fornecedor fornecedor;
            try {
                fornecedor = new FornecedorService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            telaCadastroFornecedor.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarDataFromSqlData(fornecedor.getDataCadastro()));
            telaCadastroFornecedor.getjTextFieldId().setText(String.valueOf(codigo));

            telaCadastroFornecedor.getjTextFieldNomeFantasia().setText(fornecedor.getNome());
            telaCadastroFornecedor.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
            telaCadastroFornecedor.getjFormattedTextFieldCpf().setText(Utilities.formatarCpf(fornecedor.getCpf()));
            telaCadastroFornecedor.getjFormattedTextFieldCnpj().setText(Utilities.formatarCnpj(fornecedor.getCnpj()));
            telaCadastroFornecedor.getjTextFieldRg().setText(fornecedor.getRg());
            telaCadastroFornecedor.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
            telaCadastroFornecedor.getjFormattedTextFieldFone1().setText(Utilities.formatarFone(fornecedor.getFone1()));
            telaCadastroFornecedor.getjFormattedTextFieldFone2().setText(Utilities.formatarFone(fornecedor.getFone2()));
            telaCadastroFornecedor.getjTextFieldEmail().setText(fornecedor.getEmail());
            telaCadastroFornecedor.getjFormattedTextFieldCep().setText(Utilities.formatarCep(fornecedor.getCep()));
            telaCadastroFornecedor.getjTextFieldBairro().setText(fornecedor.getBairro());
            telaCadastroFornecedor.getjTextFieldCidade().setText(fornecedor.getCidade());
            telaCadastroFornecedor.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
            telaCadastroFornecedor.getjTextFieldComplemento().setText(fornecedor.getComplemento());
            telaCadastroFornecedor.getjTextFieldObs().setText(fornecedor.getObs());
            telaCadastroFornecedor.getjTextFieldContato().setText(fornecedor.getContato());

            telaCadastroFornecedor.getjComboBoxStatus().setSelectedItem(
                fornecedor.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroFornecedor.getjComboBoxSexo().setSelectedItem(
                fornecedor.getSexo() == 'M' ? "Masculino" : "Feminino"
            );

            telaCadastroFornecedor.getjTextFieldNomeFantasia().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        telaCadastroFornecedor.dispose();
    }
}