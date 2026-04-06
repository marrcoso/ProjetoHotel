package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.Receber;
import model.DAO.JPA.JPADao;

public class ReceberDAO implements InterfaceDAO<Receber> {

    @Override
    public void Create(Receber objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Receber Retrieve(int id) throws RuntimeException {
        return JPADao.find(Receber.class, id);
    }

    @Override
    public List<Receber> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Receber.class, "Receber", atributo, valor);
    }

    @Override
    public void Update(Receber objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Receber objeto) throws RuntimeException {
        JPADao.delete(Receber.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Receber.class, id, ativar);
    }

    public Receber RetrieveByCheck(int checkId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<Receber> query = em.createQuery(
                "SELECT r FROM Receber r WHERE r.check.id = :checkId",
                Receber.class
            );
            query.setParameter("checkId", checkId);
            List<Receber> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        }, false);
    }
}
