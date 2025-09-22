package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.ServicoDAO;
import model.Servico;

public class ServicoService implements InterfaceService<Servico> {

    private final ServicoDAO servicoDAO;

    public ServicoService() {
        this.servicoDAO = new ServicoDAO();
    }

    @Override
    public void Criar(Servico objeto) throws SQLException {
        servicoDAO.Create(objeto);
    }

    @Override
    public Servico Carregar(int id) throws SQLException {
        return servicoDAO.Retrieve(id);
    }

    @Override
    public List<Servico> Carregar(String atributo, String valor) throws SQLException {
        return servicoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Servico objeto) throws SQLException {
        servicoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Servico objeto) throws SQLException {
        servicoDAO.Delete(objeto);
    }
}