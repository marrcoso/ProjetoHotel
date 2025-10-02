package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Quarto;
import service.InterfaceService;
import service.QuartoService;
import view.TelaBuscaQuarto;
import view.TelaCadastroQuarto;

public class ControllerCadQuarto implements ActionListener, InterfaceControllerCad<Quarto> {

    TelaCadastroQuarto telaCadastroQuarto;
    InterfaceService<Quarto> quartoService;
    public static int codigo;

    public ControllerCadQuarto(TelaCadastroQuarto telaCadastroQuarto) {
        this.telaCadastroQuarto = telaCadastroQuarto;
        this.quartoService = new QuartoService();
        utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroQuarto.getjPanelDados(), false);

        initListeners();
    }

    private void initListeners() {
        this.telaCadastroQuarto.getjButtonNovo().addActionListener(this);
        this.telaCadastroQuarto.getjButtonCancelar().addActionListener(this);
        this.telaCadastroQuarto.getjButtonGravar().addActionListener(this);
        this.telaCadastroQuarto.getjButtonBuscar().addActionListener(this);
        this.telaCadastroQuarto.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroQuarto.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroQuarto.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroQuarto.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroQuarto.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroQuarto.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroQuarto.getjPanelDados(), true);
        this.telaCadastroQuarto.getjTextFieldId().setEnabled(false);
        this.telaCadastroQuarto.getjTextFieldDescricao().requestFocus();
        this.telaCadastroQuarto.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroQuarto.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroQuarto.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroQuarto.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroQuarto.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroQuarto.getjFormattedTextFieldAndar().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Andar é obrigatório.");
            telaCadastroQuarto.getjFormattedTextFieldAndar().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroQuarto.getjFormattedTextFieldMetragem().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Metragem é obrigatório.");
            telaCadastroQuarto.getjFormattedTextFieldMetragem().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroQuarto.getjFormattedTextFieldCapacidade().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Capacidade de Hóspedes é obrigatório.");
            telaCadastroQuarto.getjFormattedTextFieldCapacidade().requestFocus();
            return false;
        }
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Quarto quarto = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroQuarto.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                quartoService.Criar(quarto);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroQuarto.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroQuarto.getjPanelDados(), false);
            return;
        }

        quarto.setId(Integer.parseInt(telaCadastroQuarto.getjTextFieldId().getText()));
        try {
            quartoService.Atualizar(quarto);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroQuarto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroQuarto.getjPanelDados(), false);
    }

    private Quarto construirDoFormulario() {
        Quarto quarto = new Quarto();
        quarto.setDescricao(telaCadastroQuarto.getjTextFieldDescricao().getText());
        quarto.setAndar(Integer.parseInt(telaCadastroQuarto.getjFormattedTextFieldAndar().getText()));
        quarto.setMetragem(Float.parseFloat(telaCadastroQuarto.getjFormattedTextFieldMetragem().getText()));
        quarto.setCapacidadeHospedes(Integer.parseInt(telaCadastroQuarto.getjFormattedTextFieldCapacidade().getText()));
        quarto.setObs(telaCadastroQuarto.getjTextFieldObservacao().getText());
        quarto.setFlagAnimais(telaCadastroQuarto.getjCheckBoxFlagAnimais().isSelected());
        quarto.setIdentificacao(telaCadastroQuarto.getjTextFieldDescricaoidentificacao().getText());

        Object statusSelecionado = telaCadastroQuarto.getjComboBoxStatus().getSelectedItem();
        quarto.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return quarto;
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaQuarto telaBuscaQuarto = new TelaBuscaQuarto(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaQuarto controllerBuscaQuarto = new ControllerBuscaQuarto(telaBuscaQuarto);
        telaBuscaQuarto.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroQuarto.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroQuarto.getjPanelDados(), true);

            telaCadastroQuarto.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroQuarto.getjTextFieldId().setEnabled(false);

            Quarto quarto;
            try {
                quarto = new QuartoService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroQuarto.getjTextFieldDescricao().setText(quarto.getDescricao());
            telaCadastroQuarto.getjFormattedTextFieldAndar().setText(String.valueOf(quarto.getAndar()));
            telaCadastroQuarto.getjFormattedTextFieldMetragem().setText(String.valueOf(quarto.getMetragem()));
            telaCadastroQuarto.getjFormattedTextFieldCapacidade().setText(String.valueOf(quarto.getCapacidadeHospedes()));
            telaCadastroQuarto.getjTextFieldObservacao().setText(quarto.getObs());
            telaCadastroQuarto.getjTextFieldDescricaoidentificacao().setText(quarto.getIdentificacao());
            telaCadastroQuarto.getjCheckBoxFlagAnimais().setSelected(quarto.isFlagAnimais());

            telaCadastroQuarto.getjComboBoxStatus().setSelectedItem(
                quarto.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroQuarto.getjTextFieldDescricao().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroQuarto.dispose();
    }
}