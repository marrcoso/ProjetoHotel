package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Servico;
import service.ServicoService;
import view.TelaBuscaServico;
import view.TelaCadastroServico;

public final class ControllerCadServico implements ActionListener, InterfaceControllerCad<Servico> {

    private final TelaCadastroServico telaCadastroServico;
    private final ServicoService servicoService;
    private int codigo;

    public ControllerCadServico(TelaCadastroServico telaCadastroServico) {
        this.telaCadastroServico = telaCadastroServico;
        this.servicoService = new ServicoService();
        utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), false);

        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCadastroServico.getjButtonNovo().addActionListener(this);
        this.telaCadastroServico.getjButtonCancelar().addActionListener(this);
        this.telaCadastroServico.getjButtonGravar().addActionListener(this);
        this.telaCadastroServico.getjButtonBuscar().addActionListener(this);
        this.telaCadastroServico.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroServico.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroServico.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroServico.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroServico.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroServico.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), true);
        this.telaCadastroServico.getjTextFieldId().setEnabled(false);
        this.telaCadastroServico.getjTextFieldDescricao().requestFocus();
        this.telaCadastroServico.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroServico.getjComboBoxStatus().setEnabled(false);
    }

    @Override
    public void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroServico.getjPanelDados(), false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroServico.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroServico.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroServico.getjTextFieldObservacao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Observação é obrigatório.");
            telaCadastroServico.getjTextFieldObservacao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroServico.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroServico.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Servico servico = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroServico.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                servicoService.Criar(servico);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroServico.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroServico.getjPanelDados(), false);
            return;
        }

        servico.setId(Integer.parseInt(telaCadastroServico.getjTextFieldId().getText()));
        try {
            servicoService.Atualizar(servico);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroServico.getjPanelDados(), false);
    }

    @Override
    public Servico construirDoFormulario() {
        Servico servico = new Servico();
        servico.setDescricao(telaCadastroServico.getjTextFieldDescricao().getText());
        servico.setObs(telaCadastroServico.getjTextFieldObservacao().getText());

        Object statusSelecionado = telaCadastroServico.getjComboBoxStatus().getSelectedItem();
        servico.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return servico;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaServico telaBuscaServico = new TelaBuscaServico(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaServico controllerBuscaServico = new ControllerBuscaServico(telaBuscaServico, valor -> this.codigo = valor);
        telaBuscaServico.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroServico.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroServico.getjPanelDados(), true);

            telaCadastroServico.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroServico.getjTextFieldId().setEnabled(false);

            Servico servico;
            try {
                servico = new ServicoService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroServico.getjTextFieldDescricao().setText(servico.getDescricao());
            telaCadastroServico.getjTextFieldObservacao().setText(servico.getObs());
            telaCadastroServico.getjComboBoxStatus().setSelectedItem(
                servico.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroServico.getjTextFieldDescricao().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        this.telaCadastroServico.dispose();
    }
}