package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.CheckQuarto;
import model.CopaQuarto;
import model.DAO.CheckQuartoDAO;
import model.Produto;
import service.CopaQuartoService;
import service.ProdutoService;
import utilities.Utilities;
import utilities.ValidadorCampos;
import view.TelaBuscaCheckQuarto;
import view.TelaBuscaCopaQuarto;
import view.TelaBuscaProduto;
import view.TelaCadastroCopaQuarto;

public class ControllerCadCopaQuarto implements ActionListener, InterfaceControllerCad<CopaQuarto> {

    private final TelaCadastroCopaQuarto telaCadCopa;
    private final CopaQuartoService copaQuartoService;
    private final CheckQuartoDAO checkQuartoDAO;
    private final ProdutoService produtoService;

    private CheckQuarto checkQuartoSelecionado;
    private Produto produtoSelecionado;
    private int codigo;
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ControllerCadCopaQuarto(TelaCadastroCopaQuarto telaCadCopa) {
        this.telaCadCopa = telaCadCopa;
        this.copaQuartoService = new CopaQuartoService();
        this.checkQuartoDAO = new CheckQuartoDAO();
        this.produtoService = new ProdutoService();

        initListeners();
        Utilities.ativaDesativa(telaCadCopa.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadCopa.getjPanelDados(), false);
        
        // Configura comandos de ação para botões de busca interna
        this.telaCadCopa.getjButtonRelacionarQuarto().setActionCommand("1");
        this.telaCadCopa.getjButtonRelacionarProduto().setActionCommand("1");
    }

    @Override
    public void initListeners() {
        this.telaCadCopa.getjButtonNovo().addActionListener(this);
        this.telaCadCopa.getjButtonCancelar().addActionListener(this);
        this.telaCadCopa.getjButtonGravar().addActionListener(this);
        this.telaCadCopa.getjButtonBuscar().addActionListener(this);
        this.telaCadCopa.getjButtonSair().addActionListener(this);
        this.telaCadCopa.getjButtonRelacionarQuarto().addActionListener(this);
        this.telaCadCopa.getjButtonRelacionarProduto().addActionListener(this);

        this.telaCadCopa.getjTextFieldQuantidade().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { calculaTotal(); }
            @Override
            public void removeUpdate(DocumentEvent e) { calculaTotal(); }
            @Override
            public void changedUpdate(DocumentEvent e) { calculaTotal(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == telaCadCopa.getjButtonNovo()) {
            handleNovo();
        } else if (e.getSource() == telaCadCopa.getjButtonCancelar()) {
            handleCancelar();
        } else if (e.getSource() == telaCadCopa.getjButtonGravar()) {
            handleGravar();
        } else if (e.getSource() == telaCadCopa.getjButtonBuscar()) {
            handleBuscar();
        } else if (e.getSource() == telaCadCopa.getjButtonSair()) {
            handleSair();
        } else if (e.getSource() == telaCadCopa.getjButtonRelacionarQuarto()) {
            handleBuscarQuarto();
        } else if (e.getSource() == telaCadCopa.getjButtonRelacionarProduto()) {
            handleBuscarProduto();
        }
    }

    @Override
    public void handleNovo() {
        this.codigo = 0;
        Utilities.ativaDesativa(telaCadCopa.getjPanelBotoes(), false);
        Utilities.limpaComponentes(telaCadCopa.getjPanelDados(), true);
        telaCadCopa.getjTextFieldId().setText("");
        telaCadCopa.getjFormattedTextFieldCadastro().setText(sdf.format(new Date()));
        telaCadCopa.getjComboBoxStatus().setSelectedIndex(0); // Pendente
        checkQuartoSelecionado = null;
        produtoSelecionado = null;
    }

    @Override
    public void handleCancelar() {
        this.codigo = 0;
        Utilities.ativaDesativa(telaCadCopa.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCadCopa.getjPanelDados(), false);
        checkQuartoSelecionado = null;
        produtoSelecionado = null;
    }

    @Override
    public void handleSair() {
        telaCadCopa.dispose();
    }

    private void handleBuscarQuarto() {
        TelaBuscaCheckQuarto telaBusca = new TelaBuscaCheckQuarto(null, true);
        new ControllerBuscaCheckQuarto(telaBusca, id -> {
            checkQuartoSelecionado = checkQuartoDAO.Retrieve(id);
            telaCadCopa.getjFormattedTextFieldQuarto().setText(String.valueOf(id));
        });
        telaBusca.setVisible(true);
    }

    private void handleBuscarProduto() {
        TelaBuscaProduto telaBusca = new TelaBuscaProduto(null, true);
        new ControllerBuscaProduto(telaBusca, id -> {
            produtoSelecionado = produtoService.Carregar(id);
            telaCadCopa.getjFormattedTextFieldProduto().setText(String.valueOf(id));
            telaCadCopa.getjTextFieldValorUnitario().setText(String.format("%.2f", produtoSelecionado.getValor()));
            calculaTotal();
        });
        telaBusca.setVisible(true);
    }

    private void calculaTotal() {
        try {
            String qtdStr = telaCadCopa.getjTextFieldQuantidade().getText().replace(",", ".");
            String valorStr = telaCadCopa.getjTextFieldValorUnitario().getText().replace(",", ".");
            
            if (!qtdStr.isEmpty() && !valorStr.isEmpty()) {
                float qtd = Float.parseFloat(qtdStr);
                float valor = Float.parseFloat(valorStr);
                telaCadCopa.getjTextFieldTotal().setText(String.format("%.2f", qtd * valor));
            } else {
                telaCadCopa.getjTextFieldTotal().setText("0.00");
            }
        } catch (NumberFormatException e) {
            telaCadCopa.getjTextFieldTotal().setText("0.00");
        }
    }

    @Override
    public boolean isFormularioValido() {
        if (checkQuartoSelecionado == null) {
            JOptionPane.showMessageDialog(telaCadCopa, "Selecione um Quarto ocupado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog(telaCadCopa, "Selecione um Produto!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!ValidadorCampos.validarCampoTexto(telaCadCopa.getjTextFieldQuantidade().getText())) {
            JOptionPane.showMessageDialog(telaCadCopa, "Informe a quantidade!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public CopaQuarto construirDoFormulario() {
        CopaQuarto copa = new CopaQuarto();
        String idStr = telaCadCopa.getjTextFieldId().getText();
        if (!idStr.isEmpty()) {
            copa.setId(Integer.parseInt(idStr));
        }

        copa.setCheckQuarto(checkQuartoSelecionado);
        copa.setProduto(produtoSelecionado);
        copa.setQuantidade(Float.parseFloat(telaCadCopa.getjTextFieldQuantidade().getText().replace(",", ".")));
        copa.setValorUnitario(Float.parseFloat(telaCadCopa.getjTextFieldValorUnitario().getText().replace(",", ".")));
        copa.setObs(telaCadCopa.getjTextFieldObservacao().getText());
        
        int statusIdx = telaCadCopa.getjComboBoxStatus().getSelectedIndex();
        if (statusIdx == 0) copa.setStatus('P');
        else if (statusIdx == 1) copa.setStatus('F');
        else if (statusIdx == 2) copa.setStatus('C');
        
        try {
            copa.setDataHoraPedido(sdf.parse(telaCadCopa.getjFormattedTextFieldCadastro().getText()));
        } catch (Exception e) {
            copa.setDataHoraPedido(new Date());
        }
        
        return copa;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }

        try {
            CopaQuarto copa = construirDoFormulario();

            if (copa.getId() == 0) {
                copaQuartoService.Criar(copa);
            } else {
                copaQuartoService.Atualizar(copa);
            }

            JOptionPane.showMessageDialog(telaCadCopa, "Lançamento realizado com sucesso!");
            handleCancelar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(telaCadCopa, "Erro ao gravar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleBuscar() {
        this.codigo = 0;
        TelaBuscaCopaQuarto telaBusca = new TelaBuscaCopaQuarto(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaCopaQuarto ControllerBuscaCopaQuarto = new ControllerBuscaCopaQuarto(telaBusca, id -> this.codigo = id);
        telaBusca.setVisible(true);

        if (this.codigo != 0) {
            CopaQuarto copa = copaQuartoService.Carregar(this.codigo);
            if (copa != null) {
                carregarDados(copa);
            }
        }
    }

    private void carregarDados(CopaQuarto copa) {
        Utilities.ativaDesativa(telaCadCopa.getjPanelBotoes(), false);
        Utilities.limpaComponentes(telaCadCopa.getjPanelDados(), true);
        
        checkQuartoSelecionado = copa.getCheckQuarto();
        produtoSelecionado = copa.getProduto();
        
        telaCadCopa.getjTextFieldId().setText(String.valueOf(copa.getId()));
        telaCadCopa.getjFormattedTextFieldQuarto().setText(String.valueOf(checkQuartoSelecionado.getId()));
        telaCadCopa.getjFormattedTextFieldProduto().setText(String.valueOf(produtoSelecionado.getId()));
        telaCadCopa.getjTextFieldQuantidade().setText(String.valueOf(copa.getQuantidade()));
        telaCadCopa.getjTextFieldValorUnitario().setText(String.format("%.2f", copa.getValorUnitario()));
        telaCadCopa.getjTextFieldObservacao().setText(copa.getObs());
        
        char s = copa.getStatus();
        if (s == 'P') telaCadCopa.getjComboBoxStatus().setSelectedIndex(0);
        else if (s == 'F') telaCadCopa.getjComboBoxStatus().setSelectedIndex(1);
        else if (s == 'C') telaCadCopa.getjComboBoxStatus().setSelectedIndex(2);
        
        telaCadCopa.getjFormattedTextFieldCadastro().setText(sdf.format(copa.getDataHoraPedido()));
        
        calculaTotal();
    }
}
