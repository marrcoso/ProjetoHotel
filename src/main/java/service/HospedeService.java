package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.HospedeDAO;
import model.Hospede;

public class HospedeService implements InterfaceService<Hospede> {

    private final HospedeDAO hospedeDAO;

    public HospedeService() {
        this.hospedeDAO = new HospedeDAO();
    }

    @Override
    public void Criar(Hospede objeto) throws SQLException {
        hospedeDAO.Create(objeto);
    }

    @Override
    public Hospede Carregar(int id) throws SQLException {
        return hospedeDAO.Retrieve(id);
    }

    @Override
    public List<Hospede> Carregar(String atributo, String valor) throws SQLException {
        return hospedeDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Hospede objeto) throws SQLException {
        hospedeDAO.Update(objeto);
    }

    @Override
    public void Apagar(Hospede objeto) throws SQLException {
        hospedeDAO.Delete(objeto);
    }
}