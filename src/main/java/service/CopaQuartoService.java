package service;

import java.util.List;
import model.CopaQuarto;
import model.DAO.CopaQuartoDAO;

public class CopaQuartoService implements InterfaceService<CopaQuarto> {

    private final CopaQuartoDAO copaQuartoDAO;

    public CopaQuartoService() {
        this.copaQuartoDAO = new CopaQuartoDAO();
    }

    @Override
    public void Criar(CopaQuarto objeto) throws RuntimeException {
        copaQuartoDAO.Create(objeto);
    }

    @Override
    public CopaQuarto Carregar(int id) throws RuntimeException {
        return copaQuartoDAO.Retrieve(id);
    }

    @Override
    public List<CopaQuarto> Carregar(String atributo, String valor) throws RuntimeException {
        return copaQuartoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(CopaQuarto objeto) throws RuntimeException {
        copaQuartoDAO.Update(objeto);
    }

    @Override
    public void Apagar(CopaQuarto objeto) throws RuntimeException {
        copaQuartoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        copaQuartoDAO.AtivarInativar(id, ativar);
    }

    public void mudarStatus(int id, char status) throws RuntimeException {
        copaQuartoDAO.mudarStatus(id, status);
    }

    public float calcularTotalPorCheck(int checkId) throws RuntimeException {
        return copaQuartoDAO.sumTotalFinalizadoPorCheck(checkId);
    }
}
