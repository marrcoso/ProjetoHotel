package service;

import java.util.List;
import model.DAO.QuartoDAO;
import model.Quarto;

public class QuartoService implements InterfaceService<Quarto> {

    private final QuartoDAO quartoDAO = new QuartoDAO();

    @Override
    public void Criar(Quarto objeto) throws RuntimeException {
        quartoDAO.Create(objeto);
    }

    @Override
    public Quarto Carregar(int id) throws RuntimeException {
        return quartoDAO.Retrieve(id);
    }

    @Override
    public List<Quarto> Carregar(String atributo, String valor) throws RuntimeException {
        return quartoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Quarto objeto) throws RuntimeException {
        quartoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Quarto objeto) throws RuntimeException {
        quartoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        quartoDAO.AtivarInativar(id, ativar);
    }
}