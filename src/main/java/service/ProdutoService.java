package service;

import java.util.List;
import model.DAO.ProdutoDAO;
import model.Produto;

public class ProdutoService {

    public static void Criar(Produto objeto) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.Create(objeto);
    }

    public static Produto Carregar(int id) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.Retrieve(id);
    }

    public static List<Produto> Carregar(String atributo, String valor) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Produto objeto) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.Update(objeto);
    }

    public static void Apagar(Produto objeto) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.Delete(objeto);
    }
}