package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Marca;
import model.Modelo;
import service.MarcaService;
import service.ModeloService;
import utilities.Utilities;
import view.TelaBuscaMarca;
import view.TelaBuscaModelo;
import view.TelaCadastroModelo;

public final class ControllerCadModelo implements ActionListener, InterfaceControllerCad<Modelo> {

    private final TelaCadastroModelo telaCadastroModelo;
    private final ModeloService modeloService;
    private int codigoModelo;
    private int codigoMarca;
    private Marca marcaRelacionada;

    public ControllerCadModelo(TelaCadastroModelo telaCadastroModelo) {
        this.telaCadastroModelo = telaCadastroModelo;
        this.modeloService = new ModeloService();
        Utilities.setAlwaysDisabled(this.telaCadastroModelo.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCadastroModelo.getjComboBoxStatus(), true);
        Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
        this.telaCadastroModelo.getjFormattedTextFieldMarca().putClientProperty(Utilities.ALWAYS_DISABLED, true);
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCadastroModelo.getjButtonNovo().addActionListener(this);
        this.telaCadastroModelo.getjButtonCancelar().addActionListener(this);
        this.telaCadastroModelo.getjButtonGravar().addActionListener(this);
        this.telaCadastroModelo.getjButtonBuscar().addActionListener(this);
        this.telaCadastroModelo.getjButtonSair().addActionListener(this);
        this.telaCadastroModelo.getjButtonRelacionarMarca().addActionListener(this);
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
        if (source == telaCadastroModelo.getjButtonRelacionarMarca()) {
            handleRelacionarMarca();
        }
    }

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), true);
        this.telaCadastroModelo.getjTextFieldDescricao().requestFocus();
        this.telaCadastroModelo.getjComboBoxStatus().setSelectedItem("Ativo");
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCadastroModelo.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroModelo.getjPanelDados(), false);
        this.marcaRelacionada = null;
    }

    @Override
    public boolean isFormularioValido() {
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
        if (marcaRelacionada == null) {
            JOptionPane.showMessageDialog(null, "É necessário relacionar uma marca ao produto.");
            telaCadastroModelo.getjButtonRelacionarMarca().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Modelo modelo = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroModelo.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                modeloService.Criar(modelo);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), true);
            Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), false);
            return;
        }

        modelo.setId(Integer.parseInt(telaCadastroModelo.getjTextFieldId().getText()));
        try {
            modeloService.Atualizar(modelo);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), false);
    }

    @Override
    public Modelo construirDoFormulario() {
        Modelo modelo = new Modelo();
        modelo.setDescricao(telaCadastroModelo.getjTextFieldDescricao().getText());
        Object statusSelecionado = telaCadastroModelo.getjComboBoxStatus().getSelectedItem();
        modelo.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
            );
        
        modelo.setMarca(marcaRelacionada);

        return modelo;
    }

    private void handleRelacionarMarca(){
        codigoMarca = 0;
        TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca, valor -> this.codigoMarca = valor);
        telaBuscaMarca.setVisible(true);

        if (codigoMarca != 0) {
            Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), false);
            this.telaCadastroModelo.getjTextFieldDescricao().requestFocus();

            Marca marca;
            try {
                marca = new MarcaService().Carregar(codigoMarca);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.marcaRelacionada = marca;

            telaCadastroModelo.getjFormattedTextFieldMarca().setText(getMarcaFormat(marcaRelacionada));
        }
    }

    private String getMarcaFormat(Marca marca) {
        if (marca == null) {
            return "";
        }
        return String.format("%d - %s", marca.getId(), marca.getDescricao());
    }

    @Override
    public void handleBuscar() {
        codigoModelo = 0;
        TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo, valor -> this.codigoModelo = valor);
        telaBuscaModelo.setVisible(true);

        if (codigoModelo != 0) {
            Utilities.ativaDesativa(telaCadastroModelo.getjPanelBotoes(), false);
            Utilities.limpaComponentes(telaCadastroModelo.getjPanelDados(), true);

            telaCadastroModelo.getjTextFieldId().setText(String.valueOf(codigoModelo));

            Modelo modelo;
            try {
                modelo = new ModeloService().Carregar(codigoModelo);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCadastroModelo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroModelo.getjTextFieldDescricao().setText(modelo.getDescricao());
            
            telaCadastroModelo.getjComboBoxStatus().setSelectedItem(
                modelo.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            this.marcaRelacionada = modelo.getMarca();
            telaCadastroModelo.getjFormattedTextFieldMarca().setText(getMarcaFormat(marcaRelacionada));
            telaCadastroModelo.getjTextFieldDescricao().requestFocus();
        }
    }

    
    @Override
    public void handleSair() {
        this.telaCadastroModelo.dispose();
    }
}