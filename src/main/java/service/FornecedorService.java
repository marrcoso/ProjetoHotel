package service;

import java.util.List;
import model.DAO.FornecedorDAO;
import model.Fornecedor;

public class FornecedorService implements InterfaceService<Fornecedor> {

    private final FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    @Override
    public void Criar(Fornecedor objeto) throws RuntimeException {
        fornecedorDAO.Create(objeto);
    }

    @Override
    public Fornecedor Carregar(int id) throws RuntimeException {
        return fornecedorDAO.Retrieve(id);
    }

    @Override
    public List<Fornecedor> Carregar(String atributo, String valor) throws RuntimeException {
        return fornecedorDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Fornecedor objeto) throws RuntimeException {
        fornecedorDAO.Update(objeto);
    }

    @Override
    public void Apagar(Fornecedor objeto) throws RuntimeException {
        fornecedorDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        fornecedorDAO.AtivarInativar(id, ativar);
    }
}