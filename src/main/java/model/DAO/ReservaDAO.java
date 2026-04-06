package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Reserva;

public class ReservaDAO implements InterfaceDAO<Reserva> {

    @Override
    public void Create(Reserva objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Reserva Retrieve(int id) throws RuntimeException {
        return JPADao.find(Reserva.class, id);
    }

    @Override
    public List<Reserva> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Reserva.class, "Reserva", atributo, valor);
    }

    @Override
    public void Update(Reserva objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Reserva objeto) throws RuntimeException {
        JPADao.delete(Reserva.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Reserva.class, id, ativar);
    }

    public List<Reserva> RetrieveAvailableReservas() throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            return em.createQuery(
                "SELECT r FROM Reserva r WHERE r.status = 'A'",
                Reserva.class
            ).getResultList();
        }, false);
    }
}
