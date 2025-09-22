package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.MarcaDAO;
import model.Marca;

public class MarcaService implements InterfaceService<Marca> {

    private final MarcaDAO marcaDAO;

    public MarcaService() {
        this.marcaDAO = new MarcaDAO();
    }

    @Override
    public void Criar(Marca objeto) throws SQLException {
        marcaDAO.Create(objeto);
    }

    @Override
    public Marca Carregar(int id) throws SQLException {
        return marcaDAO.Retrieve(id);
    }

    @Override
    public List<Marca> Carregar(String atributo, String valor) throws SQLException {
        return marcaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Marca objeto) throws SQLException {
        marcaDAO.Update(objeto);
    }

    @Override
    public void Apagar(Marca objeto) throws SQLException {
        marcaDAO.Delete(objeto);
    }
}