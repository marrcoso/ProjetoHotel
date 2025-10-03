package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Produto;
import service.ProdutoService;
import view.TelaBuscaProduto;
import view.TelaCadastroProduto;

public class ControllerCadProduto implements ActionListener, InterfaceControllerCad<Produto> {

    private final TelaCadastroProduto telaCadastroProduto;
    private final ProdutoService produtoService;
    public static int codigo;

    public ControllerCadProduto(TelaCadastroProduto telaCadastroProduto) {
        this.telaCadastroProduto = telaCadastroProduto;
        this.produtoService = new ProdutoService();
        utilities.Utilities.ativaDesativa(this.telaCadastroProduto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroProduto.getjPanelDados(), false);

        initListeners();
    }

    private void initListeners() {
        this.telaCadastroProduto.getjButtonNovo().addActionListener(this);
        this.telaCadastroProduto.getjButtonCancelar().addActionListener(this);
        this.telaCadastroProduto.getjButtonGravar().addActionListener(this);
        this.telaCadastroProduto.getjButtonBuscar().addActionListener(this);
        this.telaCadastroProduto.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCadastroProduto.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCadastroProduto.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCadastroProduto.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCadastroProduto.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCadastroProduto.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleNovo() {
        utilities.Utilities.ativaDesativa(this.telaCadastroProduto.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaCadastroProduto.getjPanelDados(), true);
        this.telaCadastroProduto.getjTextFieldId().setEnabled(false);
        this.telaCadastroProduto.getjTextFieldDescricao().requestFocus();
        this.telaCadastroProduto.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCadastroProduto.getjComboBoxStatus().setEnabled(false);
    }

    private void handleCancelar() {
        utilities.Utilities.ativaDesativa(this.telaCadastroProduto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroProduto.getjPanelDados(), false);
    }

    private boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroProduto.getjTextFieldDescricao().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório.");
            telaCadastroProduto.getjTextFieldDescricao().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoTexto(telaCadastroProduto.getjFormattedTextFieldValor().getText())) {
            JOptionPane.showMessageDialog(null, "O campo Valor é obrigatório.");
            telaCadastroProduto.getjFormattedTextFieldValor().requestFocus();
            return false;
        }
        return true;
    }

    private void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }
        Produto produto = construirDoFormulario();

        boolean isNovoCadastro = telaCadastroProduto.getjTextFieldId().getText().trim().isEmpty();

        if (isNovoCadastro) {
            try {
                produtoService.Criar(produto);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroProduto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            utilities.Utilities.ativaDesativa(telaCadastroProduto.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(telaCadastroProduto.getjPanelDados(), false);
            return;
        }

        produto.setId(Integer.parseInt(telaCadastroProduto.getjTextFieldId().getText()));
        try {
            produtoService.Atualizar(produto);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaCadastroProduto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        utilities.Utilities.ativaDesativa(telaCadastroProduto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(telaCadastroProduto.getjPanelDados(), false);
    }

    private Produto construirDoFormulario() {
        Produto produto = new Produto();
        produto.setDescricao(telaCadastroProduto.getjTextFieldDescricao().getText());
        produto.setObs(telaCadastroProduto.getjTextFieldObservacao().getText());
        produto.setValor(Float.parseFloat(telaCadastroProduto.getjFormattedTextFieldValor().getText()));

        Object statusSelecionado = telaCadastroProduto.getjComboBoxStatus().getSelectedItem();
        produto.setStatus(
            statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I'
        );

        return produto;
    }

    private void handleBuscar() {
        codigo = 0;
        TelaBuscaProduto telaBuscaProduto = new TelaBuscaProduto(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaProduto controllerBuscaProduto = new ControllerBuscaProduto(telaBuscaProduto);
        telaBuscaProduto.setVisible(true);

        if (codigo != 0) {
            utilities.Utilities.ativaDesativa(telaCadastroProduto.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(telaCadastroProduto.getjPanelDados(), true);

            telaCadastroProduto.getjTextFieldId().setText(String.valueOf(codigo));
            telaCadastroProduto.getjTextFieldId().setEnabled(false);

            Produto produto;
            try {
                produto = new ProdutoService().Carregar(codigo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(telaCadastroProduto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            telaCadastroProduto.getjTextFieldDescricao().setText(produto.getDescricao());
            telaCadastroProduto.getjTextFieldObservacao().setText(produto.getObs());
            telaCadastroProduto.getjFormattedTextFieldValor().setText(String.valueOf(produto.getValor()));
            telaCadastroProduto.getjComboBoxStatus().setSelectedItem(
                produto.getStatus() == 'A' ? "Ativo" : "Inativo"
            );

            telaCadastroProduto.getjTextFieldDescricao().requestFocus();
        }
    }

    private void handleSair() {
        telaCadastroProduto.dispose();
    }
}