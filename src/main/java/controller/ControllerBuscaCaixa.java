package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Caixa;
import service.CaixaService;
import view.TelaBuscaCaixa;

public final class ControllerBuscaCaixa implements ActionListener, InterfaceControllerBusca<Caixa> {

    private final TelaBuscaCaixa telaBuscaCaixa;
    private final CaixaService caixaService;
    private final Consumer<Integer> atualizaCodigo;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ControllerBuscaCaixa(TelaBuscaCaixa telaBuscaCaixa, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaCaixa = telaBuscaCaixa;
        this.caixaService = new CaixaService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
        carregarCaixaAberto();
    }

    @Override
    public void initListeners() {
        this.telaBuscaCaixa.getjButtonCarregar().addActionListener(this);
        this.telaBuscaCaixa.getjButtonFiltar().addActionListener(this);
        this.telaBuscaCaixa.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaCaixa.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaCaixa.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaCaixa.getjButtonSair()) {
            handleSair();
        }
    }

    private void carregarCaixaAberto() {
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaCaixa.getjTableDados().getModel();
        tabela.setRowCount(0);
        try {
            Caixa caixa = caixaService.getCaixaAberto();
            if (caixa != null) {
                adicionarLinhaTabela(tabela, caixa);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar caixa aberto: " + ex.getMessage());
        }
    }

    @Override
    public void handleCarregar() {
        int selectedRow = telaBuscaCaixa.getjTableDados().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um registro para carregar!");
            return;
        }
        int id = (int) telaBuscaCaixa.getjTableDados().getValueAt(selectedRow, 0);
        atualizaCodigo.accept(id);
        telaBuscaCaixa.dispose();
    }

    @Override
    public void handleFiltrar() {
        String filtroTexto = telaBuscaCaixa.getjTFFiltro().getText().trim();
        int filtroIndex = telaBuscaCaixa.getjCBFiltro().getSelectedIndex();
        
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaCaixa.getjTableDados().getModel();
        tabela.setRowCount(0);
        
        try {
            switch (filtroIndex) {
                case 0: // ID
                    if (!filtroTexto.isEmpty()) {
                        Caixa caixa = caixaService.Carregar(Integer.parseInt(filtroTexto));
                        if (caixa != null) adicionarLinhaTabela(tabela, caixa);
                    } else {
                        List<Caixa> caixas = caixaService.Carregar("", "");
                        for (Caixa c : caixas) adicionarLinhaTabela(tabela, c);
                    }
                    break;
                case 1: // Data Abertura
                    carregarPorAtributo("dataHoraAbertura", filtroTexto, tabela);
                    break;
                case 2: // Status
                    carregarPorAtributo("status", filtroTexto, tabela);
                    break;
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaCaixa, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Caixa caixa) {
        tabela.addRow(new Object[]{
            caixa.getId(),
            caixa.getDataHoraAbertura() != null ? sdf.format(caixa.getDataHoraAbertura()) : "",
            caixa.getStatus() == 'I' ? (caixa.getDataHoraFechamento() != null ? sdf.format(caixa.getDataHoraFechamento()) : "") : "",
            caixa.getStatus() == 'A' ? "Aberto" : "Fechado"
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<Caixa> caixas = caixaService.Carregar(atributo, valor);
        for (Caixa c : caixas) {
            adicionarLinhaTabela(tabela, c);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaCaixa.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        // Not used for Caixa history
    }
}
