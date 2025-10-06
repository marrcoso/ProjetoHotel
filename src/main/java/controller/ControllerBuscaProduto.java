package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Produto;
import service.ProdutoService;
import view.TelaBuscaProduto;

public final class ControllerBuscaProduto implements ActionListener, InterfaceControllerBusca<Produto> {

    private final TelaBuscaProduto telaBuscaProduto;
    private final ProdutoService produtoService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaProduto(TelaBuscaProduto telaBuscaProduto, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaProduto = telaBuscaProduto;
        this.produtoService = new ProdutoService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaProduto.getjButtonCarregar().addActionListener(this);
        this.telaBuscaProduto.getjButtonFiltar().addActionListener(this);
        this.telaBuscaProduto.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaProduto.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaProduto.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaProduto.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaProduto.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaProduto.getjTableDados()
                .getValueAt(telaBuscaProduto.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaProduto.dispose();
        }
    }

    private enum FiltroProduto {
        ID, DESCRICAO, OBSERVACAO, VALOR;

        public static FiltroProduto fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                case 2: return OBSERVACAO;
                case 3: return VALOR;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Produto produto) {
        tabela.addRow(new Object[]{
            produto.getId(),
            produto.getDescricao(),
            produto.getObs(),
            produto.getValor(),
            produto.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<Produto> listaProdutos = produtoService.Carregar(atributo, valor);
        for (Produto p : listaProdutos) {
            adicionarLinhaTabela(tabela, p);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaProduto.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaProduto.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaProduto.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaProduto.getjTFFiltro().getText();

        FiltroProduto filtro = FiltroProduto.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Produto produto = produtoService.Carregar(Integer.parseInt(filtroTexto));
                    if (produto != null) {
                        adicionarLinhaTabela(tabela, produto);
                    }
                    break;
                }
                case DESCRICAO: {
                    carregarPorAtributo("descricao", filtroTexto, tabela);
                    break;
                }
                case OBSERVACAO: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
                case VALOR: {
                    carregarPorAtributo("valor", filtroTexto, tabela);
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaProduto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaProduto.dispose();
    }

}
