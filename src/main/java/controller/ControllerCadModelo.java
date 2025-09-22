package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Modelo;
import service.InterfaceService;
import service.ModeloService;
import view.TelaBuscaModelo;
import view.TelaCadastroModelo;

public class ControllerCadModelo implements ActionListener, InterfaceControllerCad<Modelo> {

    TelaCadastroModelo telaCadastroModelo;
    InterfaceService<Modelo> modeloService;
    public static int codigo;

    public ControllerCadModelo(TelaCadastroModelo telaCadastroModelo) {
        this.telaCadastroModelo = telaCadastroModelo;
        this.modeloService = new ModeloService();
        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);

        initListeners();
    }

    private void initListeners() {
        this.telaCadastroModelo.getjButtonNovo().addActionListener(this);
        this.telaCadastroModelo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroModelo.getjButtonGravar().addActionListener(this);
        this.telaCadastroModelo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroModelo.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroModelo.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroModelo.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroModelo.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroModelo.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroModelo.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), true);
        this.telaCadastroModelo.getjTextFieldId().setEnabled(false);
        this.telaCadastroModelo.getjTextFieldDescricao().requestFocus();
        this.telaCadastroModelo.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroModelo.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroModelo.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroModelo.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroModelo.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroModelo.getjComboBoxStatus().requestFocus();
            return false;
        }
        // TODO: Validar Marca
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Modelo modelo = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroModelo.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                modeloService.Criar(modelo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), false);
            return;
        }

        modelo.setId(Integer.parseInt(telaCadastroModelo.getjTextFieldId().getText()));
        try {
            modeloService.Atualizar(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), false);
    }

    private Modelo construirDoFormulario() {
        Modelo modelo = new Modelo();
        modelo.setDescricao(telaCadastroModelo.getjTextFieldDescricao().getText());
        Object statusSelecionado = telaCadastroModelo.getjComboBoxStatus().getSelectedItem();
        modelo.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
            );
        // TODO: Setar Marca

        return modelo;
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo);
        telaBuscaModelo.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), true);

            telaCadastroModelo.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroModelo.getjTextFieldId().setEnabled(false);

            Modelo modelo;
            try {
                modelo = new ModeloService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroModelo.getjTextFieldDescricao().setText(modelo.getDescricao());
            
            telaCadastroModelo.getjComboBoxStatus().setSelectedItem(
                modelo.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            // TODO: Obter Marca

            telaCadastroModelo.getjTextFieldDescricao().requestFocus();
        }
    }

    private void handleSair() {
        this.telaCadastroModelo.dispose();
    }
}