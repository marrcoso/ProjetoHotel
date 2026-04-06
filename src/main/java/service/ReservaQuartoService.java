package service;

import java.util.List;
import model.ReservaQuarto;
import model.DAO.ReservaQuartoDAO;

public class ReservaQuartoService implements InterfaceService<ReservaQuarto> {

    private final ReservaQuartoDAO reservaQuartoDAO;

    public ReservaQuartoService() {
        this.reservaQuartoDAO = new ReservaQuartoDAO();
    }

    @Override
    public void Criar(ReservaQuarto objeto) throws RuntimeException {
        reservaQuartoDAO.Create(objeto);
    }

    @Override
    public ReservaQuarto Carregar(int id) throws RuntimeException {
        return reservaQuartoDAO.Retrieve(id);
    }

    @Override
    public List<ReservaQuarto> Carregar(String atributo, String valor) throws RuntimeException {
        return reservaQuartoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(ReservaQuarto objeto) throws RuntimeException {
        reservaQuartoDAO.Update(objeto);
    }

    @Override
    public void Apagar(ReservaQuarto objeto) throws RuntimeException {
        reservaQuartoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        reservaQuartoDAO.AtivarInativar(id, ativar);
    }

    public List<ReservaQuarto> carregarPorReserva(int reservaId) throws RuntimeException {
        return reservaQuartoDAO.RetrieveByReserva(reservaId);
    }

    public void substituirReservaQuartos(int reservaId, List<ReservaQuarto> reservaQuartos) throws RuntimeException {
        reservaQuartoDAO.ReplaceReservaQuartos(reservaId, reservaQuartos);
    }
}
