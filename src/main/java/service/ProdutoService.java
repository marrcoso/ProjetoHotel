package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.ProdutoDAO;
import model.Produto;

public class ProdutoService implements InterfaceService<Produto> {

    private final ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    public void Criar(Produto objeto) throws SQLException {
        produtoDAO.Create(objeto);
    }

    @Override
    public Produto Carregar(int id) throws SQLException {
        return produtoDAO.Retrieve(id);
    }

    @Override
    public List<Produto> Carregar(String atributo, String valor) throws SQLException {
        return produtoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Produto objeto) throws SQLException {
        produtoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Produto objeto) throws SQLException {
        produtoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws SQLException {
        produtoDAO.AtivarInativar(id, ativar);
    }
}