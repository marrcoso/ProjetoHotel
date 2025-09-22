package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.VeiculoDAO;
import model.Veiculo;

public class VeiculoService implements InterfaceService<Veiculo> {

    private final VeiculoDAO veiculoDAO;

    public VeiculoService() {
        this.veiculoDAO = new VeiculoDAO();
    }

    @Override
    public void Criar(Veiculo objeto) throws SQLException {
        veiculoDAO.Create(objeto);
    }

    @Override
    public Veiculo Carregar(int id) throws SQLException {
        return veiculoDAO.Retrieve(id);
    }

    @Override
    public List<Veiculo> Carregar(String atributo, String valor) throws SQLException {
        return veiculoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Veiculo objeto) throws SQLException {
        veiculoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Veiculo objeto) throws SQLException {
        veiculoDAO.Delete(objeto);
    }
}