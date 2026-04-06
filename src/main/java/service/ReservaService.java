package service;

import java.util.Date;
import java.util.List;

import model.DAO.ReservaDAO;
import model.Reserva;

public class ReservaService implements InterfaceService<Reserva> {

    private final ReservaDAO reservaDAO;

    public ReservaService() {
        this.reservaDAO = new ReservaDAO();
    }

    @Override
    public void Criar(Reserva objeto) throws RuntimeException {
        prepararDatasObrigatorias(objeto);
        reservaDAO.Create(objeto);
    }

    @Override
    public Reserva Carregar(int id) throws RuntimeException {
        return reservaDAO.Retrieve(id);
    }

    @Override
    public List<Reserva> Carregar(String atributo, String valor) throws RuntimeException {
        return reservaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Reserva objeto) throws RuntimeException {
        prepararDatasObrigatorias(objeto);
        reservaDAO.Update(objeto);
    }

    @Override
    public void Apagar(Reserva objeto) throws RuntimeException {
        reservaDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        reservaDAO.AtivarInativar(id, ativar);
    }

    private void prepararDatasObrigatorias(Reserva reserva) {
        if (reserva.getDataHoraReserva() == null) {
            reserva.setDataHoraReserva(new Date());
        }
    }

    public List<Reserva> carregarReservasDisponiveis() throws RuntimeException {
        return reservaDAO.RetrieveAvailableReservas();
    }
}
