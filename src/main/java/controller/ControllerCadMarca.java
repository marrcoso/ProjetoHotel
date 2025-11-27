package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Marca;
import service.MarcaService;
import utilities.Utilities;
import view.TelaBuscaMarca;
import view.TelaCadastroMarca;

public final class ControllerCadMarca implements ActionListener, InterfaceControllerCad<Marca> {

    private final TelaCadastroMarca telaCadastroMarca;
    private final MarcaService marcaService;
    private int codigo;

    public ControllerCadMarca(TelaCadastroMarca telaCadastroMarca) {
        this.telaCadastroMarca = telaCadastroMarca;
        this.marcaService = new MarcaService();
        Utilities.setAlwaysDisabled(this.telaCadastroMarca.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroMarca.getjComboBoxStatus(), true);
        Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCadastroMarca.getjButtonNovo().addActionListener(this);
        this.telaCadastroMarca.getjButtonCancelar().addActionListener(this);
        this.telaCadastroMarca.getjButtonGravar().addActionListener(this);
        this.telaCadastroMarca.getjButtonBuscar().addActionListener(this);
        this.telaCadastroMarca.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroMarca.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroMarca.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroMarca.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroMarca.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroMarca.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), true);
        this.telaCadastroMarca.getjTextFieldDescricao().requestFocus();
        this.telaCadastroMarca.getjComboBoxStatus().setSelectedItem("Ativo");
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCadastroMarca.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroMarca.getjPanelDados(), false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroMarca.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroMarca.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(telaCadastroMarca.getjComboBoxStatus().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Selecione um Status válido.");
            telaCadastroMarca.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Marca marca = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroMarca.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                marcaService.Criar(marca);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroMarca, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Utilities.ativaDesativa(telaCadastroMarca.getjPanelBotoes(), true);
            Utilities.limpaComponentes(telaCadastroMarca.getjPanelDados(), false);
            return;
        }

        marca.setId(Integer.parseInt(telaCadastroMarca.getjTextFieldId().getText()));
        try {
            marcaService.Atualizar(marca);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaCadastroMarca, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(telaCadastroMarca.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadastroMarca.getjPanelDados(), false);
    }

    @Override
    public Marca construirDoFormulario() {
        Marca marca = new Marca();
        marca.setDescricao(telaCadastroMarca.getjTextFieldDescricao().getText());

        Object statusSelecionado = telaCadastroMarca.getjComboBoxStatus().getSelectedItem();
        marca.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return marca;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca, marcaId -> this.codigo = marcaId);
        telaBuscaMarca.setVisible(true);

        if (codigo != 0) {
            Utilities.ativaDesativa(telaCadastroMarca.getjPanelBotoes(), false);
            Utilities.limpaComponentes(telaCadastroMarca.getjPanelDados(), true);

            telaCadastroMarca.getjTextFieldId().setText(String.valueOf(codigo));

            Marca marca;
            try {
                marca = new MarcaService().Carregar(codigo);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroMarca, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroMarca.getjTextFieldDescricao().setText(marca.getDescricao());
            telaCadastroMarca.getjComboBoxStatus().setSelectedItem(
                marca.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroMarca.getjTextFieldDescricao().requestFocus();
        }
    }

    @Override
    public void handleSair() {
        telaCadastroMarca.dispose();
    }
}