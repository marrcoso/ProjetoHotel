package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.VagaEstacionamento;
import service.VagaEstacionamentoService;
import view.TelaBuscaVaga;
import view.TelaCadastroVagaEstacionamento;

public final class ControllerCadVagaEstacionamento implements ActionListener, InterfaceControllerCad<VagaEstacionamento> {

    private final TelaCadastroVagaEstacionamento telaCadastroVagaEstacionamento;
    private final VagaEstacionamentoService vagaService;
    private int codigo;

    public ControllerCadVagaEstacionamento(TelaCadastroVagaEstacionamento telaCadastroVagaEstacionamento) {
        this.telaCadastroVagaEstacionamento = telaCadastroVagaEstacionamento;
        this.vagaService = new VagaEstacionamentoService();
        utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVagaEstacionamento.getjPanelDados(), false);

        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCadastroVagaEstacionamento.getjButtonNovo().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonCancelar().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonGravar().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonBuscar().addActionListener(this);
        this.telaCadastroVagaEstacionamento.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroVagaEstacionamento.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroVagaEstacionamento.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroVagaEstacionamento.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroVagaEstacionamento.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroVagaEstacionamento.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroVagaEstacionamento.getjPanelDados(), true);
        this.telaCadastroVagaEstacionamento.getjTextFieldId().setEnabled(false);
        this.telaCadastroVagaEstacionamento.getjTextFieldDescricao().requestFocus();
        this.telaCadastroVagaEstacionamento.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroVagaEstacionamento.getjComboBoxStatus().setEnabled(false);
    }

    @Override
    public void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroVagaEstacionamento.getjPanelDados(), false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroVagaEstacionamento.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroVagaEstacionamento.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroVagaEstacionamento.getjTextFieldObservacao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Observação é obrigatório.");
            telaCadastroVagaEstacionamento.getjTextFieldObservacao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroVagaEstacionamento.getjFormattedTextFieldMetragem().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Metragem é obrigatório.");
            telaCadastroVagaEstacionamento.getjFormattedTextFieldMetragem().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroVagaEstacionamento.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroVagaEstacionamento.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        VagaEstacionamento vaga = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroVagaEstacionamento.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                vagaService.Criar(vaga);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroVagaEstacionamento, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroVagaEstacionamento.getjPanelDados(), false);
            return;
        }

        vaga.setId(Integer.parseInt(telaCadastroVagaEstacionamento.getjTextFieldId().getText()));
        try {
            vagaService.Atualizar(vaga);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroVagaEstacionamento, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroVagaEstacionamento.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroVagaEstacionamento.getjPanelDados(), false);
    }

    @Override
    public VagaEstacionamento construirDoFormulario() {
        VagaEstacionamento vaga = new VagaEstacionamento();
        vaga.setDescricao(telaCadastroVagaEstacionamento.getjTextFieldDescricao().getText());
        vaga.setObs(telaCadastroVagaEstacionamento.getjTextFieldObservacao().getText());
        vaga.setMetragemVaga(Float.parseFloat(telaCadastroVagaEstacionamento.getjFormattedTextFieldMetragem().getText()));

        Object statusSelecionado = telaCadastroVagaEstacionamento.getjComboBoxStatus().getSelectedItem();
        vaga.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return vaga;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaVaga telaBuscaVaga = new TelaBuscaVaga(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaVagaEstacionamento controllerBuscaVagaEstacionamento = new ControllerBuscaVagaEstacionamento(telaBuscaVaga, valor -> this.codigo = valor);
        telaBuscaVaga.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroVagaEstacionamento.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroVagaEstacionamento.getjPanelDados(), true);

            telaCadastroVagaEstacionamento.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroVagaEstacionamento.getjTextFieldId().setEnabled(false);

            VagaEstacionamento vaga;
            try {
                vaga = new VagaEstacionamentoService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroVagaEstacionamento, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroVagaEstacionamento.getjTextFieldDescricao().setText(vaga.getDescricao());
            telaCadastroVagaEstacionamento.getjTextFieldObservacao().setText(vaga.getObs());
            telaCadastroVagaEstacionamento.getjFormattedTextFieldMetragem().setText(String.valueOf(vaga.getMetragemVaga()));
            telaCadastroVagaEstacionamento.getjComboBoxStatus().setSelectedItem(
                vaga.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroVagaEstacionamento.getjTextFieldDescricao().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        this.telaCadastroVagaEstacionamento.dispose();
    }
}