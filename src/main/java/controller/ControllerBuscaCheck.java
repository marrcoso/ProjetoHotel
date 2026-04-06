package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Check;
import service.CheckService;
import view.TelaBuscaCheck;

public final class ControllerBuscaCheck implements ActionListener, InterfaceControllerBusca<Check> {

    private final TelaBuscaCheck telaBuscaCheck;
    private final CheckService checkService;
    private final Consumer<Integer> atualizaCodigo;
    private final boolean apenasDisponiveis;

    public ControllerBuscaCheck(TelaBuscaCheck telaBuscaCheck, Consumer<Integer> atualizaCodigo) {
        this(telaBuscaCheck, atualizaCodigo, false);
    }

    public ControllerBuscaCheck(TelaBuscaCheck telaBuscaCheck, Consumer<Integer> atualizaCodigo, boolean apenasDisponiveis) {
        this.telaBuscaCheck = telaBuscaCheck;
        this.checkService = new CheckService();
        this.atualizaCodigo = atualizaCodigo;
        this.apenasDisponiveis = apenasDisponiveis;
        initListeners();
        if (apenasDisponiveis) {
            carregarChecksDisponiveis();
        }
    }

    @Override
    public void initListeners() {
        this.telaBuscaCheck.getjButtonCarregar().addActionListener(this);
        this.telaBuscaCheck.getjButtonFiltar().addActionListener(this);
        this.telaBuscaCheck.getjButtonSair().addActionListener(this);
        this.telaBuscaCheck.getjButtonAtivar().addActionListener(this);
        this.telaBuscaCheck.getjButtonInativar().addActionListener(this);
        this.telaBuscaCheck.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaCheck.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaCheck.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaCheck.getjTableDados().getValueAt(row, 5); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaCheck.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaCheck.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    private void carregarChecksDisponiveis() {
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaCheck.getjTableDados().getModel();
        tabela.setRowCount(0);
        try {
            List<Check> checks = checkService.carregarChecksDisponiveis();
            for (Check c : checks) {
                adicionarLinhaTabela(tabela, c);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar checks disponíveis: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaCheck.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaCheck.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaCheck.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaCheck.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaCheck.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaCheck.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaCheck.getjTableDados()
                .getValueAt(telaBuscaCheck.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaCheck.dispose();
        }
    }

    private enum FiltroCheck {
        ID, DATA_CADASTRO, DATA_ENTRADA, DATA_SAIDA, OBS, STATUS;

        public static FiltroCheck fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DATA_CADASTRO;
                case 2: return DATA_ENTRADA;
                case 3: return DATA_SAIDA;
                case 4: return OBS;
                case 5: return STATUS;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Check check) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tabela.addRow(new Object[]{
            check.getId(),
            sdf.format(check.getDataHoraCadastro()),
            sdf.format(check.getDataHoraEntrada()),
            sdf.format(check.getDataHoraSaida()),
            check.getObs(),
            check.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<Check> listaChecks = checkService.Carregar(atributo, valor);
        if (apenasDisponiveis) {
            List<Check> disponiveis = checkService.carregarChecksDisponiveis();
            listaChecks = listaChecks.stream()
                .filter(disponiveis::contains)
                .collect(Collectors.toList());
        }
        for (Check c : listaChecks) {
            adicionarLinhaTabela(tabela, c);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaCheck.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaCheck.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaCheck.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaCheck.getjTFFiltro().getText();

        FiltroCheck filtro = FiltroCheck.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Check check = checkService.Carregar(Integer.parseInt(filtroTexto));
                    if (apenasDisponiveis) {
                        List<Integer> disponiveis = checkService.carregarChecksDisponiveis().stream()
                            .map(Check::getId)
                            .collect(java.util.stream.Collectors.toList());
                        boolean disponivel = check != null && disponiveis.contains(check.getId());
                        if (!disponivel) {
                            JOptionPane.showMessageDialog(null, "Check indisponivel para seleção.");
                            return;
                        }
                    }
                    if (check != null) {
                        adicionarLinhaTabela(tabela, check);
                    }
                    break;
                }
                case DATA_CADASTRO: {
                    carregarPorAtributo("data_hora_cadastro", filtroTexto, tabela);
                    break;
                }
                case DATA_ENTRADA: {
                    carregarPorAtributo("data_hora_entrada", filtroTexto, tabela);
                    break;
                }
                case DATA_SAIDA: {
                    carregarPorAtributo("data_hora_saida", filtroTexto, tabela);
                    break;
                }
                case OBS: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
                case STATUS: {
                    carregarPorAtributo("status", filtroTexto, tabela);
                    break;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaCheck.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaCheck.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaCheck.getjTableDados()
            .getValueAt(telaBuscaCheck.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaCheck.getjTableDados()
            .getValueAt(telaBuscaCheck.getjTableDados().getSelectedRow(), 5);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Check já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            checkService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaCheck.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaCheck.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 5);
            telaBuscaCheck.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaCheck.getjButtonInativar().setEnabled(ativar);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}