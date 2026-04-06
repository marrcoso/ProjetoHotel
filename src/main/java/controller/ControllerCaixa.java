package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.Caixa;
import model.MovimentoCaixa;
import service.CaixaService;
import service.MovimentoCaixaService;
import utilities.Utilities;
import view.TelaBuscaCaixa;
import view.TelaCaixa;

public final class ControllerCaixa implements ActionListener, InterfaceControllerCad<Caixa> {

    private final TelaCaixa telaCaixa;
    private final CaixaService caixaService;
    private final MovimentoCaixaService movimentoCaixaService;
    private int codigo;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ControllerCaixa(TelaCaixa telaCaixa) {
        this.telaCaixa = telaCaixa;
        this.caixaService = new CaixaService();
        this.movimentoCaixaService = new MovimentoCaixaService();
        
        Utilities.ativaDesativa(this.telaCaixa.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCaixa.getjPanelDados(), false);

        Utilities.setAlwaysDisabled(this.telaCaixa.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCaixa.getjComboBoxStatus(), true);
        Utilities.setAlwaysDisabled(this.telaCaixa.getjFormattedTextFieldDataAbertura(), true);
        Utilities.setAlwaysDisabled(this.telaCaixa.getjFormattedTextFieldDataFechamento(), true);
        Utilities.setAlwaysDisabled(this.telaCaixa.getjTextFieldTotalMovimentos(), true);
        Utilities.setAlwaysDisabled(this.telaCaixa.getjTextFieldDiferenca(), true);
        initListeners();
        
        // Auto-load open register
        Caixa aberto = caixaService.getCaixaAberto();
        if (aberto != null) {
            preencherDados(aberto);
        }
    }

    @Override
    public void initListeners() {
        this.telaCaixa.getjButtonNovo().addActionListener(this);
        this.telaCaixa.getjButtonCancelar().addActionListener(this);
        this.telaCaixa.getjButtonGravar().addActionListener(this);
        this.telaCaixa.getjButtonBuscar().addActionListener(this);
        this.telaCaixa.getjButtonSair().addActionListener(this);
        
        this.telaCaixa.getjButtonAdicionarMovManual().addActionListener(this);
        this.telaCaixa.getjButtonAtivarInativarMovimento().addActionListener(this);
        this.telaCaixa.getjComboBoxFiltroStatusMovimento().addActionListener(this);

        this.telaCaixa.getjTextFieldValorFechamento().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { recalcularDiferenca(); }
            @Override
            public void removeUpdate(DocumentEvent e) { recalcularDiferenca(); }
            @Override
            public void changedUpdate(DocumentEvent e) { recalcularDiferenca(); }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaCaixa.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCaixa.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCaixa.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCaixa.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCaixa.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaCaixa.getjButtonAdicionarMovManual()) {
            handleAdicionarMovimentoManual();
            return;
        }
        if (source == telaCaixa.getjButtonAtivarInativarMovimento()) {
            handleAtivarInativarMovimento();
            return;
        }
        if (source == telaCaixa.getjComboBoxFiltroStatusMovimento()) {
            handleFiltroStatus();
        }
    }

    private void handleFiltroStatus() {
        if (!telaCaixa.getjTextFieldId().getText().isEmpty()) {
            Caixa caixa = caixaService.Carregar(Integer.parseInt(telaCaixa.getjTextFieldId().getText()));
            preencherDados(caixa);
        }
    }

    private void handleAdicionarMovimentoManual() {
        if (telaCaixa.getjTextFieldId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(telaCaixa, "Selecione ou abra um caixa primeiro.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idText = telaCaixa.getjTextFieldId().getText();
        Caixa caixa = caixaService.Carregar(Integer.parseInt(idText));
        
        if (caixa.getStatus() == 'I') {
            JOptionPane.showMessageDialog(telaCaixa, "Não é possível adicionar movimentos em um caixa fechado.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String descricao = telaCaixa.getjTextFieldDescMovManual().getText().trim();
        String valorText = telaCaixa.getjTextFieldValorMovManual().getText().trim().replace(",", ".");

        if (descricao.isEmpty() || valorText.isEmpty()) {
            JOptionPane.showMessageDialog(telaCaixa, "Descrição e Valor são obrigatórios.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            float valor = Float.parseFloat(valorText);
            MovimentoCaixa movimento = new MovimentoCaixa();
            movimento.setCaixa(caixa);
            movimento.setDescricao(descricao);
            movimento.setValor(valor);
            movimento.setDataHoraMovimento(new Date());
            movimento.setStatus('A');
            movimento.setObs("Lançamento Manual");
            movimento.setReceber(null); // Manual

            movimentoCaixaService.Criar(movimento);
            
            telaCaixa.getjTextFieldDescMovManual().setText("");
            telaCaixa.getjTextFieldValorMovManual().setText("");
            
            preencherDados(caixa);
            JOptionPane.showMessageDialog(telaCaixa, "Movimento adicionado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(telaCaixa, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAtivarInativarMovimento() {
        int row = telaCaixa.getjTableMovimentos().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(telaCaixa, "Selecione um movimento na tabela.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Caixa caixa = caixaService.Carregar(Integer.parseInt(telaCaixa.getjTextFieldId().getText()));
        String statusFiltro = (String) telaCaixa.getjComboBoxFiltroStatusMovimento().getSelectedItem();
        java.util.List<MovimentoCaixa> movimentos = movimentoCaixaService.CarregarPorCaixa(caixa.getId(), statusFiltro);
        
        MovimentoCaixa mov = movimentos.get(row);

        if (mov.getReceber() != null) {
            JOptionPane.showMessageDialog(telaCaixa, "Movimentos automáticos (vinculados a recebimentos) não podem ser alterados.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean novoStatus = mov.getStatus() == 'I'; 
        String acao = novoStatus ? "ativar" : "inativar";

        int confirm = JOptionPane.showConfirmDialog(telaCaixa, "Deseja realmente " + acao + " este movimento?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            movimentoCaixaService.AtivarInativar(mov.getId(), novoStatus);
            preencherDados(caixa);
        }
    }

    @Override
    public void handleNovo() {
        if (caixaService.getCaixaAberto() != null) {
            JOptionPane.showMessageDialog(telaCaixa, "Já existe um caixa aberto! Feche o atual antes de abrir um novo.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Utilities.ativaDesativa(this.telaCaixa.getjPanelBotoes(), false);
        Utilities.limpaComponentes(this.telaCaixa.getjPanelDados(), true);
        
        this.telaCaixa.getjComboBoxStatus().setSelectedItem("Aberto");
        this.telaCaixa.getjFormattedTextFieldDataAbertura().setText(sdf.format(new Date()));
        this.telaCaixa.getjTextFieldValorAbertura().requestFocus();
        
        this.telaCaixa.getjTextFieldValorFechamento().setEnabled(false);
        this.telaCaixa.getjFormattedTextFieldDataFechamento().setEnabled(false);
        
        ((DefaultTableModel) this.telaCaixa.getjTableMovimentos().getModel()).setRowCount(0);
        this.telaCaixa.getjTextFieldTotalMovimentos().setText("0.00");
        this.telaCaixa.getjTextFieldDiferenca().setText("0.00");
        
        Utilities.ativaDesativa(this.telaCaixa.getjPanelNovoMovimentoManual(), true);
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(this.telaCaixa.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCaixa.getjPanelDados(), false);
        ((DefaultTableModel) this.telaCaixa.getjTableMovimentos().getModel()).setRowCount(0);
    }

    @Override
    public boolean isFormularioValido() {
        String valorAbertura = telaCaixa.getjTextFieldValorAbertura().getText().trim();
        if (valorAbertura.isEmpty()) {
            JOptionPane.showMessageDialog(telaCaixa, "O valor de abertura é obrigatório.", "Validação", JOptionPane.WARNING_MESSAGE);
            telaCaixa.getjTextFieldValorAbertura().requestFocus();
            return false;
        }
        
        if (!telaCaixa.getjTextFieldId().getText().isEmpty() && telaCaixa.getjComboBoxStatus().getSelectedItem().equals("Aberto")) {
             String valorFechamento = telaCaixa.getjTextFieldValorFechamento().getText().trim();
             if (valorFechamento.isEmpty()) {
                 JOptionPane.showMessageDialog(telaCaixa, "O valor de fechamento é obrigatório para encerrar o caixa.", "Validação", JOptionPane.WARNING_MESSAGE);
                 telaCaixa.getjTextFieldValorFechamento().requestFocus();
                 return false;
             }
        }
        
        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }

        boolean isAbertura = telaCaixa.getjTextFieldId().getText().trim().isEmpty();

        if (isAbertura) {
            Caixa caixa = new Caixa();
            caixa.setValorDeAbertura(Float.parseFloat(telaCaixa.getjTextFieldValorAbertura().getText().replace(",", ".")));
            caixa.setDataHoraAbertura(new Date());
            caixa.setObs(telaCaixa.getjTextFieldObs().getText());
            caixa.setStatus('A');
            caixa.setValorDeFechamento(0);
            caixa.setDataHoraFechamento(new Date()); 

            try {
                caixaService.Criar(caixa);
                JOptionPane.showMessageDialog(telaCaixa, "Caixa aberto com sucesso!");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCaixa, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            int id = Integer.parseInt(telaCaixa.getjTextFieldId().getText());
            Caixa caixa = caixaService.Carregar(id);
            
            if (caixa.getStatus() == 'I') {
                JOptionPane.showMessageDialog(telaCaixa, "Este caixa já está fechado.");
                return;
            }
            
            caixa.setValorDeFechamento(Float.parseFloat(telaCaixa.getjTextFieldValorFechamento().getText().replace(",", ".")));
            caixa.setDataHoraFechamento(new Date());
            caixa.setObs(telaCaixa.getjTextFieldObs().getText());
            caixa.setStatus('I');

            try {
                caixaService.Atualizar(caixa);
                JOptionPane.showMessageDialog(telaCaixa, "Caixa fechado com sucesso!");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCaixa, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Utilities.ativaDesativa(telaCaixa.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCaixa.getjPanelDados(), false);
        ((DefaultTableModel) this.telaCaixa.getjTableMovimentos().getModel()).setRowCount(0);
    }

    @Override
    public Caixa construirDoFormulario() {
        return null;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaCaixa telaBuscaCaixa = new TelaBuscaCaixa(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaCaixa controllerBuscaCaixa = new ControllerBuscaCaixa(telaBuscaCaixa, caixaId -> this.codigo = caixaId);
        telaBuscaCaixa.setVisible(true);

        if (codigo != 0) {
            Caixa caixa;
            try {
                caixa = caixaService.Carregar(codigo);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCaixa, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            preencherDados(caixa);
        }
    }

    private void preencherDados(Caixa caixa) {
        Utilities.ativaDesativa(telaCaixa.getjPanelBotoes(), false);
        Utilities.limpaComponentes(telaCaixa.getjPanelDados(), true);

        telaCaixa.getjTextFieldId().setText(String.valueOf(caixa.getId()));
        telaCaixa.getjFormattedTextFieldDataAbertura().setText(sdf.format(caixa.getDataHoraAbertura()));
        telaCaixa.getjTextFieldValorAbertura().setText(String.format("%.2f", caixa.getValorDeAbertura()));
        telaCaixa.getjTextFieldObs().setText(caixa.getObs());
        telaCaixa.getjComboBoxStatus().setSelectedItem(caixa.getStatus() == 'A' ? "Aberto" : "Fechado");

        String statusFiltro = (String) telaCaixa.getjComboBoxFiltroStatusMovimento().getSelectedItem();
        DefaultTableModel tabela = (DefaultTableModel) telaCaixa.getjTableMovimentos().getModel();
        tabela.setRowCount(0);
        
        java.util.List<MovimentoCaixa> movimentos = movimentoCaixaService.CarregarPorCaixa(caixa.getId(), statusFiltro);
        for (MovimentoCaixa m : movimentos) {
            tabela.addRow(new Object[]{
                sdf.format(m.getDataHoraMovimento()),
                m.getDescricao(),
                String.format("%.2f", m.getValor()),
                m.getStatus() == 'A' ? "Ativo" : "Inativo"
            });
        }
        
        if (caixa.getStatus() == 'A') {
            this.telaCaixa.getjTextFieldValorAbertura().setEnabled(false);
            this.telaCaixa.getjTextFieldValorFechamento().setEnabled(true);
            this.telaCaixa.getjTextFieldValorFechamento().setText("");
            this.telaCaixa.getjFormattedTextFieldDataFechamento().setText("");
            this.telaCaixa.getjTextFieldValorFechamento().requestFocus();
            Utilities.ativaDesativa(this.telaCaixa.getjPanelNovoMovimentoManual(), true);
            this.telaCaixa.getjButtonAtivarInativarMovimento().setEnabled(true);
        } else {
            this.telaCaixa.getjFormattedTextFieldDataFechamento().setText(sdf.format(caixa.getDataHoraFechamento()));
            this.telaCaixa.getjTextFieldValorFechamento().setText(String.format("%.2f", caixa.getValorDeFechamento()));
            
            this.telaCaixa.getjTextFieldValorAbertura().setEnabled(false);
            this.telaCaixa.getjTextFieldValorFechamento().setEnabled(false);
            this.telaCaixa.getjTextFieldObs().setEnabled(false);
            this.telaCaixa.getjButtonGravar().setEnabled(false);
            Utilities.ativaDesativa(this.telaCaixa.getjPanelNovoMovimentoManual(), false);
            this.telaCaixa.getjButtonAtivarInativarMovimento().setEnabled(false);
        }
        
        recalcularDiferenca();
    }

    private void recalcularDiferenca() {
        if (telaCaixa.getjTextFieldId().getText().isEmpty()) return;
        
        int caixaId = Integer.parseInt(telaCaixa.getjTextFieldId().getText());
        
        try {
            float valorAbertura = 0;
            String textAbertura = telaCaixa.getjTextFieldValorAbertura().getText().replace(",", ".");
            if (!textAbertura.isEmpty()) {
                valorAbertura = Float.parseFloat(textAbertura);
            }

            float totalMovimentosAtivos = movimentoCaixaService.getTotalAtivos(caixaId);
            telaCaixa.getjTextFieldTotalMovimentos().setText(String.format("%.2f", totalMovimentosAtivos));

            float valorFechamento = 0;
            String textFechamento = telaCaixa.getjTextFieldValorFechamento().getText().replace(",", ".");
            if (!textFechamento.isEmpty()) {
                valorFechamento = Float.parseFloat(textFechamento);
            }

            float esperado = valorAbertura + totalMovimentosAtivos;
            float diferenca = valorFechamento - esperado;

            this.telaCaixa.getjTextFieldDiferenca().setText(String.format("%.2f", diferenca));
        } catch (NumberFormatException e) {
            this.telaCaixa.getjTextFieldDiferenca().setText("0.00");
        }
    }

    @Override
    public void handleSair() {
        telaCaixa.dispose();
    }
}
