package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.telaBuscaFuncionario.getjButtonAtivar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonInativar().addActionListener(this);
        this.telaBuscaFuncionario.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaFuncionario.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaFuncionario.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaFuncionario.getjTableDados().getValueAt(row, 3); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaFuncionario.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaFuncionario.getjButtonInativar().setEnabled(status == 'A');
        }
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
            return;
        }
        if (source == telaBuscaFuncionario.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaFuncionario.getjButtonInativar()) {
            handleAtivarInativar(false);
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
        ID, USUARIO, NOME, CPF, OBSERVACAO, TELEFONE, EMAIL, CEP, CIDADE, BAIRRO, LOGRADOURO;

        public static FiltroFuncionario fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return USUARIO;
                case 2: return NOME;
                case 3: return CPF;
                case 4: return OBSERVACAO;
                case 5: return TELEFONE;
                case 6: return EMAIL;
                case 7: return CEP;
                case 8: return CIDADE;
                case 9: return BAIRRO;
                case 10: return LOGRADOURO;
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
            funcionario.getStatus(),
            funcionario.getUsuario(),
            funcionario.getObs(),
            funcionario.getFone1(),
            funcionario.getEmail(),
            funcionario.getCidade(),
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
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
                case USUARIO: {
                    carregarPorAtributo("usuario", filtroTexto, tabela);
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
                case OBSERVACAO: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
                case TELEFONE: {
                    carregarPorAtributo("fone", filtroTexto, tabela);
                    break;
                }
                case EMAIL: {
                    carregarPorAtributo("email", filtroTexto, tabela);
                    break;
                }
                case CEP: {
                    carregarPorAtributo("cep", filtroTexto, tabela);
                    break;
                }
                case CIDADE: {
                    carregarPorAtributo("cidade", filtroTexto, tabela);
                    break;
                }
                case BAIRRO: {
                    carregarPorAtributo("bairro", filtroTexto, tabela);
                    break;
                }
                case LOGRADOURO: {
                    carregarPorAtributo("logradouro", filtroTexto, tabela);
                    break;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaFuncionario, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaFuncionario.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaFuncionario.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaFuncionario.getjTableDados()
            .getValueAt(telaBuscaFuncionario.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaFuncionario.getjTableDados()
            .getValueAt(telaBuscaFuncionario.getjTableDados().getSelectedRow(), 3);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Funcionário já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            funcionarioService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaFuncionario.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaFuncionario.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 3);
            telaBuscaFuncionario.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaFuncionario.getjButtonInativar().setEnabled(ativar);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaFuncionario, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
