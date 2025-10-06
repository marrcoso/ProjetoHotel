package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;

public final class ControllerBuscaFornecedor implements ActionListener, InterfaceControllerBusca<Fornecedor> {

    private final TelaBuscaFornecedor telaBuscaFornecedor;
    private final FornecedorService fornecedorService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaFornecedor(TelaBuscaFornecedor telaBuscaFornecedor, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaFornecedor = telaBuscaFornecedor;
        this.fornecedorService = new FornecedorService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaFornecedor.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaFornecedor.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaFornecedor.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaFornecedor.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaFornecedor.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaFornecedor.getjTableDados()
                .getValueAt(telaBuscaFornecedor.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaFornecedor.dispose();
        }
    }

    private enum FiltroFornecedor {
        ID, NOME, CPF, RAZAO_SOCIAL, CNPJ, OBSERVACAO, TELEFONE, EMAIL, CEP, CIDADE, BAIRRO, LOGRADOURO;

        public static FiltroFornecedor fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CPF;
                case 3: return RAZAO_SOCIAL;
                case 4: return CNPJ;
                case 5: return OBSERVACAO;
                case 6: return TELEFONE;
                case 7: return EMAIL;
                case 8: return CEP;
                case 9: return CIDADE;
                case 10: return BAIRRO;
                case 11: return LOGRADOURO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Fornecedor fornecedor) {
        tabela.addRow(new Object[]{
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getCpf(),
            fornecedor.getStatus(),
            fornecedor.getRazaoSocial(),
            fornecedor.getCnpj(),
            fornecedor.getFone1(),
            fornecedor.getEmail(),
            fornecedor.getCidade(),
            fornecedor.getObs()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<Fornecedor> listaFornecedores = fornecedorService.Carregar(atributo, valor);
        for (Fornecedor f : listaFornecedores) {
            adicionarLinhaTabela(tabela, f);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaFornecedor.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaFornecedor.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaFornecedor.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaFornecedor.getjTFFiltro().getText();

        FiltroFornecedor filtro = FiltroFornecedor.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Fornecedor fornecedor = fornecedorService.Carregar(Integer.parseInt(filtroTexto));
                    if (fornecedor != null) {
                        adicionarLinhaTabela(tabela, fornecedor);
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
                case RAZAO_SOCIAL: {
                    carregarPorAtributo("razao_social", filtroTexto, tabela);
                    break;
                }
                case CNPJ: {
                    carregarPorAtributo("cnpj", filtroTexto, tabela);
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaFornecedor, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaFornecedor.dispose();
    }
}
