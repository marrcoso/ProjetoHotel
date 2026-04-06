package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.Reserva;
import model.ReservaQuarto;
import model.DAO.JPA.JPADao;

public class ReservaQuartoDAO implements InterfaceDAO<ReservaQuarto> {

    @Override
    public void Create(ReservaQuarto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public ReservaQuarto Retrieve(int id) throws RuntimeException {
        return JPADao.find(ReservaQuarto.class, id);
    }

    @Override
    public List<ReservaQuarto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(ReservaQuarto.class, "ReservaQuarto", atributo, valor);
    }

    @Override
    public void Update(ReservaQuarto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(ReservaQuarto objeto) throws RuntimeException {
        JPADao.delete(ReservaQuarto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(ReservaQuarto.class, id, ativar);
    }

    public List<ReservaQuarto> RetrieveByReserva(int reservaId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<ReservaQuarto> query = em.createQuery(
                "SELECT rq FROM ReservaQuarto rq JOIN FETCH rq.quarto WHERE rq.reserva.id = :reservaId ORDER BY rq.id",
                ReservaQuarto.class
            );
            query.setParameter("reservaId", reservaId);
            return query.getResultList();
        }, false);
    }

    public void ReplaceReservaQuartos(int reservaId, List<ReservaQuarto> reservaQuartos) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Reserva reserva = em.find(Reserva.class, reservaId);
            if (reserva == null) {
                throw new RuntimeException("Reserva não encontrada para vincular quartos.");
            }

            TypedQuery<ReservaQuarto> query = em.createQuery(
                "SELECT rq FROM ReservaQuarto rq WHERE rq.reserva.id = :reservaId",
                ReservaQuarto.class
            );
            query.setParameter("reservaId", reservaId);

            for (ReservaQuarto reservaQuarto : query.getResultList()) {
                em.remove(reservaQuarto);
            }

            for (ReservaQuarto rq : reservaQuartos) {
                rq.setReserva(reserva);
                if (rq.getId() == 0) {
                    em.persist(rq);
                } else {
                    em.merge(rq);
                }
            }
        }, true);
    }
}
