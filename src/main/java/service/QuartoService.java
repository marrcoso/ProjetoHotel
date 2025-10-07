package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.QuartoDAO;
import model.Quarto;

public class QuartoService implements InterfaceService<Quarto> {

    private final QuartoDAO quartoDAO;

    public QuartoService() {
        this.quartoDAO = new QuartoDAO();
    }

    @Override
    public void Criar(Quarto objeto) throws SQLException {
        quartoDAO.Create(objeto);
    }

    @Override
    public Quarto Carregar(int id) throws SQLException {
        return quartoDAO.Retrieve(id);
    }

    @Override
    public List<Quarto> Carregar(String atributo, String valor) throws SQLException {
        return quartoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Quarto objeto) throws SQLException {
        quartoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Quarto objeto) throws SQLException {
        quartoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws SQLException {
        quartoDAO.AtivarInativar(id, ativar);
    }
}