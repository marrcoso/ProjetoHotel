package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.ModeloDAO;
import model.Modelo;

public class ModeloService implements InterfaceService<Modelo> {

    private final ModeloDAO modeloDAO;

    public ModeloService() {
        this.modeloDAO = new ModeloDAO();
    }

    @Override
    public void Criar(Modelo objeto) throws SQLException {
        modeloDAO.Create(objeto);
    }

    @Override
    public Modelo Carregar(int id) throws SQLException {
        return modeloDAO.Retrieve(id);
    }

    @Override
    public List<Modelo> Carregar(String atributo, String valor) throws SQLException {
        return modeloDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Modelo objeto) throws SQLException {
        modeloDAO.Update(objeto);
    }

    @Override
    public void Apagar(Modelo objeto) throws SQLException {
        modeloDAO.Delete(objeto);
    }
}