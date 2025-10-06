package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import service.FuncionarioService;
import view.TelaBuscaFuncionario;

public final class ControllerBuscaFuncionario implements ActionListener, InterfaceControllerBusca<Funcionario> {

    private final TelaBuscaFuncionario telaBuscaFuncionario;
    private final FuncionarioService funcionarioService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaFuncionario(TelaBuscaFuncionario telaBuscaFuncionario, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaFuncionario = telaBuscaFuncionario;
        this.funcionarioService = new FuncionarioService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaFuncionario.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaFuncionario.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaFuncionario.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaFuncionario.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaFuncionario.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
        } else {
            int codigo = (int) telaBuscaFuncionario.getjTableDados()
                .getValueAt(telaBuscaFuncionario.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaFuncionario.dispose();
        }
    }

    private enum FiltroFuncionario {
        ID, NOME, CPF;

        public static FiltroFuncionario fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CPF;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Funcionario funcionario) {
        tabela.addRow(new Object[]{
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getCpf(),
            funcionario.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<Funcionario> listaFuncionarios = funcionarioService.Carregar(atributo, valor);
        for (Funcionario f : listaFuncionarios) {
            adicionarLinhaTabela(tabela, f);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaFuncionario.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaFuncionario.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaFuncionario.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaFuncionario.getjTFFiltro().getText();

        FiltroFuncionario filtro = FiltroFuncionario.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Funcionario funcionario = funcionarioService.Carregar(Integer.parseInt(filtroTexto));
                    if (funcionario != null) {
                        adicionarLinhaTabela(tabela, funcionario);
                    }
                    break;
                }
                case NOME: {
                    carregarPorAtributo("nome", filtroTexto, tabela);
                    break;
                }
                case CPF: {
                    carregarPorAtributo("cpf", filtroTexto, tabela);
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaFuncionario, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaFuncionario.dispose();
    }
}
