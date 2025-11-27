package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Quarto;
import service.QuartoService;
import utilities.Utilities;
import view.TelaBuscaQuarto;
import view.TelaCadastroQuarto;

public final class ControllerCadQuarto implements ActionListener, InterfaceControllerCad<Quarto> {

    private final TelaCadastroQuarto telaCadastroQuarto;
    private final QuartoService quartoService;
    private int codigo;

    public ControllerCadQuarto(TelaCadastroQuarto telaCadastroQuarto) {
        this.telaCadastroQuarto = telaCadastroQuarto;
        this.quartoService = new QuartoService();
        Utilities.setAlwaysDisabled(this.telaCadastroQuarto.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroQuarto.getjComboBoxStatus(), true);
        Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroQuarto.getjPanelDados(), false);

        initListeners();
    }

    @Override
    public void initListeners() {
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

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCadastroQuarto.getjPanelDados(), true);
        this.telaCadastroQuarto.getjTextFieldDescricao().requestFocus();
        this.telaCadastroQuarto.getjComboBoxStatus().setSelectedItem("Ativo");
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCadastroQuarto.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroQuarto.getjPanelDados(), false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroQuarto.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroQuarto.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoNumero(telaCadastroQuarto.getjFormattedTextFieldCapacidade().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Capacidade de Hóspedes é inválido.");
            telaCadastroQuarto.getjFormattedTextFieldCapacidade().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoNumero(telaCadastroQuarto.getjFormattedTextFieldMetragem().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Metragem é inválido.");
            telaCadastroQuarto.getjFormattedTextFieldMetragem().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoNumero(telaCadastroQuarto.getjFormattedTextFieldAndar().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Andar é inválido.");
            telaCadastroQuarto.getjFormattedTextFieldAndar().requestFocus();
            return false;
        }
        
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Quarto quarto = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroQuarto.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                quartoService.Criar(quarto);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Utilities.ativaDesativa(telaCadastroQuarto.getjPanelBotoes(), true);
            Utilities.limpaComponentes(telaCadastroQuarto.getjPanelDados(), false);
            return;
        }

        quarto.setId(Integer.parseInt(telaCadastroQuarto.getjTextFieldId().getText()));
        try {
            quartoService.Atualizar(quarto);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaCadastroQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(telaCadastroQuarto.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadastroQuarto.getjPanelDados(), false);
    }

    @Override
    public Quarto construirDoFormulario() {
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

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaQuarto telaBuscaQuarto = new TelaBuscaQuarto(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaQuarto controllerBuscaQuarto = new ControllerBuscaQuarto(telaBuscaQuarto, valor -> this.codigo = valor);
        telaBuscaQuarto.setVisible(true);

        if (codigo != 0) {
            Utilities.ativaDesativa(telaCadastroQuarto.getjPanelBotoes(), false);
            Utilities.limpaComponentes(telaCadastroQuarto.getjPanelDados(), true);

            telaCadastroQuarto.getjTextFieldId().setText(String.valueOf(codigo));

            Quarto quarto;
            try {
                quarto = new QuartoService().Carregar(codigo);
            } catch (RuntimeException ex) {
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

    @Override
    public void handleSair() {
        telaCadastroQuarto.dispose();
    }
}