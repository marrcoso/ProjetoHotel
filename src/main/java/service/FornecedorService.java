package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.FornecedorDAO;
import model.Fornecedor;

public class FornecedorService {

    private final FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    public void Criar(Fornecedor objeto) throws SQLException {
        fornecedorDAO.Create(objeto);
    }

    public Fornecedor Carregar(int id) throws SQLException {
        return fornecedorDAO.Retrieve(id);
    }

    public List<Fornecedor> Carregar(String atributo, String valor) throws SQLException {
        return fornecedorDAO.Retrieve(atributo, valor);
    }

    public void Atualizar(Fornecedor objeto) throws SQLException {
        fornecedorDAO.Update(objeto);
    }

    public void Apagar(Fornecedor objeto) throws SQLException {
        fornecedorDAO.Delete(objeto);
    }
}