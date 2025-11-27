package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Servico;
import service.ServicoService;
import view.TelaBuscaServico;

public final class ControllerBuscaServico implements ActionListener, InterfaceControllerBusca<Servico> {

    private final TelaBuscaServico telaBuscaServico;
    private final ServicoService servicoService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaServico(TelaBuscaServico telaBuscaServico, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaServico = telaBuscaServico;
        this.servicoService = new ServicoService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaServico.getjButtonCarregar().addActionListener(this);
        this.telaBuscaServico.getjButtonFiltar().addActionListener(this);
        this.telaBuscaServico.getjButtonSair().addActionListener(this);
        this.telaBuscaServico.getjButtonAtivar().addActionListener(this);
        this.telaBuscaServico.getjButtonInativar().addActionListener(this);
        this.telaBuscaServico.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaServico.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaServico.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaServico.getjTableDados().getValueAt(row, 3); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaServico.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaServico.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaServico.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaServico.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaServico.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaServico.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaServico.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaServico.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaServico.getjTableDados()
                .getValueAt(telaBuscaServico.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaServico.dispose();
        }
    }

    private enum FiltroServico {
        ID, DESCRICAO, OBSERVACAO;

        public static FiltroServico fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                case 2: return OBSERVACAO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Servico servico) {
        tabela.addRow(new Object[]{
            servico.getId(),
            servico.getDescricao(),
            servico.getObs(),
            servico.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<Servico> listaServicos = servicoService.Carregar(atributo, valor);
        for (Servico s : listaServicos) {
            adicionarLinhaTabela(tabela, s);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaServico.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaServico.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaServico.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaServico.getjTFFiltro().getText();

        FiltroServico filtro = FiltroServico.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Servico servico = servicoService.Carregar(Integer.parseInt(filtroTexto));
                    if (servico != null) {
                        adicionarLinhaTabela(tabela, servico);
                    }
                    break;
                }
                case DESCRICAO: {
                    carregarPorAtributo("descricao", filtroTexto, tabela);
                    break;
                }
                case OBSERVACAO:
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaServico.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaServico.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaServico.getjTableDados()
            .getValueAt(telaBuscaServico.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaServico.getjTableDados()
            .getValueAt(telaBuscaServico.getjTableDados().getSelectedRow(), 3);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Serviço já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            servicoService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaServico.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaServico.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 3);
            telaBuscaServico.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaServico.getjButtonInativar().setEnabled(ativar);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
