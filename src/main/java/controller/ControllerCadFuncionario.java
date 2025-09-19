package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Funcionario;
import service.FuncionarioService;
import view.TelaBuscaFuncionario;
import view.TelaCadastroFuncionario;

public class ControllerCadFuncionario implements ActionListener {

    TelaCadastroFuncionario telaCadastroFuncionario;
    public static int codigo;

    public ControllerCadFuncionario(TelaCadastroFuncionario telaCadastroFuncionario) {

        this.telaCadastroFuncionario = telaCadastroFuncionario;

        this.telaCadastroFuncionario.getjButtonNovo().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonCancelar().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonGravar().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonBuscar().addActionListener(this);
        this.telaCadastroFuncionario.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroFuncionario.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroFuncionario.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroFuncionario.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadastroFuncionario.getjPanelDados(), true);
            this.telaCadastroFuncionario.getjTextFieldId().setEnabled(false);
            this.telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setEnabled(false);
            this.telaCadastroFuncionario.getjTextFieldNome().requestFocus();

        } else if (evento.getSource() == this.telaCadastroFuncionario.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroFuncionario.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadastroFuncionario.getjPanelDados(), false);

        } else if (evento.getSource() == this.telaCadastroFuncionario.getjButtonGravar()) {

            if (this.telaCadastroFuncionario.getjTextFieldNome().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "O Atributo Nome é Obrigatório....");
                this.telaCadastroFuncionario.getjTextFieldNome().requestFocus();
            } else {
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(this.telaCadastroFuncionario.getjTextFieldNome().getText());
                funcionario.setCpf(this.telaCadastroFuncionario.getjFormattedTextFieldCpf().getText());
                funcionario.setRg(this.telaCadastroFuncionario.getjTextFieldRg().getText());
                funcionario.setObs(this.telaCadastroFuncionario.getjTextFieldObs().getText());
                funcionario.setSexo(
                    this.telaCadastroFuncionario.getjComboBoxSexo().getSelectedItem().equals("Masculino") ? 'M' : 'F'
                );
                funcionario.setStatus(
                    this.telaCadastroFuncionario.getjComboBoxStatus().getSelectedItem().equals("Ativo") ? 'A' : 'I'
                );
                funcionario.setSenha(new String(this.telaCadastroFuncionario.getjPasswordFieldSenha().getPassword()));
                funcionario.setFone1(this.telaCadastroFuncionario.getjFormattedTextFieldFone1().getText());
                funcionario.setFone2(this.telaCadastroFuncionario.getjFormattedTextFieldFone2().getText());
                funcionario.setEmail(this.telaCadastroFuncionario.getjTextFieldEmail().getText());
                funcionario.setCep(this.telaCadastroFuncionario.getjFormattedTextFieldCep().getText());
                funcionario.setBairro(this.telaCadastroFuncionario.getjTextFieldBairro().getText());
                funcionario.setCidade(this.telaCadastroFuncionario.getjTextFieldCidade().getText());
                funcionario.setLogradouro(this.telaCadastroFuncionario.getjTextFieldLogradouro().getText());
                funcionario.setComplemento(this.telaCadastroFuncionario.getjTextFieldComplemento().getText());
                funcionario.setDataCadastro(this.telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().getText());

                if (this.telaCadastroFuncionario.getjTextFieldId().getText().trim().equalsIgnoreCase("")) {
                    funcionario.setStatus('A');
                    FuncionarioService.Criar(funcionario);
                } else {
                    funcionario.setId(Integer.parseInt(this.telaCadastroFuncionario.getjTextFieldId().getText()));
                    FuncionarioService.Atualizar(funcionario);
                }

                utilities.Utilities.ativaDesativa(this.telaCadastroFuncionario.getjPanelBotoes(), true);
                utilities.Utilities.limpaComponentes(this.telaCadastroFuncionario.getjPanelDados(), false);
            }

        } else if (evento.getSource() == this.telaCadastroFuncionario.getjButtonBuscar()) {

            codigo = 0;

            TelaBuscaFuncionario telaBuscaFuncionario = new TelaBuscaFuncionario(null, true);
            ControllerBuscaFuncionario controllerBuscaFuncionario = new ControllerBuscaFuncionario(telaBuscaFuncionario);
            telaBuscaFuncionario.setVisible(true);

            if (codigo != 0) {
                utilities.Utilities.ativaDesativa(this.telaCadastroFuncionario.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadastroFuncionario.getjPanelDados(), true);

                this.telaCadastroFuncionario.getjTextFieldId().setText(codigo + "");
                this.telaCadastroFuncionario.getjTextFieldId().setEnabled(false);


                Funcionario funcionario = FuncionarioService.Carregar(codigo);

                this.telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setText(funcionario.getDataCadastro());
                this.telaCadastroFuncionario.getjFormattedTextFieldDataCadastro().setEnabled(false);
                this.telaCadastroFuncionario.getjTextFieldNome().setText(funcionario.getNome());
                this.telaCadastroFuncionario.getjFormattedTextFieldCpf().setText(funcionario.getCpf());
                this.telaCadastroFuncionario.getjTextFieldRg().setText(funcionario.getRg());
                this.telaCadastroFuncionario.getjTextFieldObs().setText(funcionario.getObs());
                this.telaCadastroFuncionario.getjComboBoxSexo().setSelectedItem(
                    funcionario.getSexo() == 'M' ? "Masculino" : "Feminino"
                );
                this.telaCadastroFuncionario.getjComboBoxStatus().setSelectedItem(
                    funcionario.getStatus() == 'A' ? "Ativo" : "Inativo"
                );
                this.telaCadastroFuncionario.getjPasswordFieldSenha().setText(funcionario.getSenha());
                this.telaCadastroFuncionario.getjFormattedTextFieldFone1().setText(funcionario.getFone1());
                this.telaCadastroFuncionario.getjFormattedTextFieldFone2().setText(funcionario.getFone2());
                this.telaCadastroFuncionario.getjTextFieldEmail().setText(funcionario.getEmail());
                this.telaCadastroFuncionario.getjFormattedTextFieldCep().setText(funcionario.getCep());
                this.telaCadastroFuncionario.getjTextFieldBairro().setText(funcionario.getBairro());
                this.telaCadastroFuncionario.getjTextFieldCidade().setText(funcionario.getCidade());
                this.telaCadastroFuncionario.getjTextFieldLogradouro().setText(funcionario.getLogradouro());
                this.telaCadastroFuncionario.getjTextFieldComplemento().setText(funcionario.getComplemento());

                this.telaCadastroFuncionario.getjTextFieldNome().requestFocus();
            }

        } else if (evento.getSource() == this.telaCadastroFuncionario.getjButtonSair()) {
            this.telaCadastroFuncionario.dispose();
        }
    }
}