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
    }

    @Override
    public void initListeners() {
        this.telaCaixa.getjButtonNovo().addActionListener(this);
        this.telaCaixa.getjButtonCancelar().addActionListener(this);
        this.telaCaixa.getjButtonGravar().addActionListener(this);
        this.telaCaixa.getjButtonBuscar().addActionListener(this);
        this.telaCaixa.getjButtonSair().addActionListener(this);
        
        this.telaCaixa.getjTextFieldValorFechamento().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { recalcularDiferenca(); }
            @Override
            public void removeUpdate(DocumentEvent e) { recalcularDiferenca(); }
            @Override
            public void changedUpdate(DocumentEvent e) { recalcularDiferenca(); }
        });
    }
    
    // ... (actionPerformed remains the same)
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
        
        // Desabilita campos de fechamento ao abrir
        this.telaCaixa.getjTextFieldValorFechamento().setEnabled(false);
        this.telaCaixa.getjFormattedTextFieldDataFechamento().setEnabled(false);
        
        // Limpa tabela de lançamentos
        ((DefaultTableModel) this.telaCaixa.getjTableMovimentos().getModel()).setRowCount(0);
        this.telaCaixa.getjTextFieldTotalMovimentos().setText("0.00");
        this.telaCaixa.getjTextFieldDiferenca().setText("0.00");
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
        
        // Se estiver fechando o caixa
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
            caixa.setValorDeAbertura(Float.parseFloat(telaCaixa.getjTextFieldValorAbertura().getText()));
            caixa.setDataHoraAbertura(new Date());
            caixa.setObs(telaCaixa.getjTextFieldObs().getText());
            caixa.setStatus('A');
            caixa.setValorDeFechamento(0);
            caixa.setDataHoraFechamento(new Date()); // JPA requires value or nullable

            try {
                caixaService.Criar(caixa);
                JOptionPane.showMessageDialog(telaCaixa, "Caixa aberto com sucesso!");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(telaCaixa, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // Fechamento
            int id = Integer.parseInt(telaCaixa.getjTextFieldId().getText());
            Caixa caixa = caixaService.Carregar(id);
            
            if (caixa.getStatus() == 'I') {
                JOptionPane.showMessageDialog(telaCaixa, "Este caixa já está fechado.");
                return;
            }
            
            caixa.setValorDeFechamento(Float.parseFloat(telaCaixa.getjTextFieldValorFechamento().getText()));
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

        // Preencher movimentos
        DefaultTableModel tabela = (DefaultTableModel) telaCaixa.getjTableMovimentos().getModel();
        tabela.setRowCount(0);
        float totalMovimentos = 0;
        
        java.util.List<MovimentoCaixa> movimentos = movimentoCaixaService.CarregarPorCaixa(caixa.getId());
        for (MovimentoCaixa m : movimentos) {
            tabela.addRow(new Object[]{
                sdf.format(m.getDataHoraMovimento()),
                m.getDescricao(),
                String.format("%.2f", m.getValor()),
            });
            totalMovimentos += m.getValor();
        }
        
        telaCaixa.getjTextFieldTotalMovimentos().setText(String.format("%.2f", totalMovimentos));

        if (caixa.getStatus() == 'A') {
            this.telaCaixa.getjTextFieldValorAbertura().setEnabled(false);
            this.telaCaixa.getjTextFieldValorFechamento().setEnabled(true);
            this.telaCaixa.getjTextFieldValorFechamento().setText("");
            this.telaCaixa.getjFormattedTextFieldDataFechamento().setText("");
            this.telaCaixa.getjTextFieldValorFechamento().requestFocus();
        } else {
            this.telaCaixa.getjFormattedTextFieldDataFechamento().setText(sdf.format(caixa.getDataHoraFechamento()));
            this.telaCaixa.getjTextFieldValorFechamento().setText(String.format("%.2f", caixa.getValorDeFechamento()));
            
            // Tudo desabilitado se já estiver fechado
            this.telaCaixa.getjTextFieldValorAbertura().setEnabled(false);
            this.telaCaixa.getjTextFieldValorFechamento().setEnabled(false);
            this.telaCaixa.getjTextFieldObs().setEnabled(false);
            this.telaCaixa.getjButtonGravar().setEnabled(false);
        }
        
        recalcularDiferenca();
    }

    private void recalcularDiferenca() {
        try {
            float valorAbertura = 0;
            String textAbertura = telaCaixa.getjTextFieldValorAbertura().getText().replace(",", ".");
            if (!textAbertura.isEmpty()) {
                valorAbertura = Float.parseFloat(textAbertura);
            }

            float totalMovimentos = 0;
            String textTotalMovimentos = telaCaixa.getjTextFieldTotalMovimentos().getText().replace(",", ".");
            if (!textTotalMovimentos.isEmpty()) {
                totalMovimentos = Float.parseFloat(textTotalMovimentos);
            }

            float valorFechamento = 0;
            String textFechamento = telaCaixa.getjTextFieldValorFechamento().getText().replace(",", ".");
            if (!textFechamento.isEmpty()) {
                valorFechamento = Float.parseFloat(textFechamento);
            }

            float esperado = valorAbertura + totalMovimentos;
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
