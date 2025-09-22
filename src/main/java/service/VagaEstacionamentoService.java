package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.VagaEstacionamentoDAO;
import model.VagaEstacionamento;

public class VagaEstacionamentoService implements InterfaceService<VagaEstacionamento> {

    private final VagaEstacionamentoDAO vagaDAO;

    public VagaEstacionamentoService() {
        this.vagaDAO = new VagaEstacionamentoDAO();
    }

    @Override
    public void Criar(VagaEstacionamento objeto) throws SQLException {
        vagaDAO.Create(objeto);
    }

    @Override
    public VagaEstacionamento Carregar(int id) throws SQLException {
        return vagaDAO.Retrieve(id);
    }

    @Override
    public List<VagaEstacionamento> Carregar(String atributo, String valor) throws SQLException {
        return vagaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(VagaEstacionamento objeto) throws SQLException {
        vagaDAO.Update(objeto);
    }

    @Override
    public void Apagar(VagaEstacionamento objeto) throws SQLException {
        vagaDAO.Delete(objeto);
    }
}