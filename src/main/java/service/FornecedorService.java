package service;

import java.util.List;
import model.DAO.FornecedorDAO;
import model.Fornecedor;

public class FornecedorService {

    public static void Criar(Fornecedor objeto) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        fornecedorDAO.Create(objeto);
    }

    public static Fornecedor Carregar(int id) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        return fornecedorDAO.Retrieve(id);
    }

    public static List<Fornecedor> Carregar(String atributo, String valor) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        return fornecedorDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Fornecedor objeto) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        fornecedorDAO.Update(objeto);
    }

    public static void Apagar(Fornecedor objeto) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        fornecedorDAO.Delete(objeto);
    }
}