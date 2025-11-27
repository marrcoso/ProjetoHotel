package service;

import java.util.List;
import model.DAO.ProdutoDAO;
import model.Produto;

public class ProdutoService implements InterfaceService<Produto> {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void Criar(Produto objeto) throws RuntimeException {
        produtoDAO.Create(objeto);
    }

    @Override
    public Produto Carregar(int id) throws RuntimeException {
        return produtoDAO.Retrieve(id);
    }

    @Override
    public List<Produto> Carregar(String atributo, String valor) throws RuntimeException {
        return produtoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Produto objeto) throws RuntimeException {
        produtoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Produto objeto) throws RuntimeException {
        produtoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        produtoDAO.AtivarInativar(id, ativar);
    }
}