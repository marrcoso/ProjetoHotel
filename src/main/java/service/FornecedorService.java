package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.FornecedorDAO;
import model.Fornecedor;

public class FornecedorService implements InterfaceService<Fornecedor> {

    private final FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    @Override
    public void Criar(Fornecedor objeto) throws SQLException {
        fornecedorDAO.Create(objeto);
    }

    @Override
    public Fornecedor Carregar(int id) throws SQLException {
        return fornecedorDAO.Retrieve(id);
    }

    @Override
    public List<Fornecedor> Carregar(String atributo, String valor) throws SQLException {
        return fornecedorDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Fornecedor objeto) throws SQLException {
        fornecedorDAO.Update(objeto);
    }

    @Override
    public void Apagar(Fornecedor objeto) throws SQLException {
        fornecedorDAO.Delete(objeto);
    }
}