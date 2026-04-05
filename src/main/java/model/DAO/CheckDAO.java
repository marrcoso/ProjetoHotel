package model.DAO;

import java.util.List;

import model.Check;
import model.DAO.JPA.JPADao;

public class CheckDAO implements InterfaceDAO<Check> {

    @Override
    public void Create(Check objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Check Retrieve(int id) throws RuntimeException {
        return JPADao.find(Check.class, id);
    }

    @Override
    public List<Check> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Check.class, "Check", atributo, valor);
    }

    @Override
    public void Update(Check objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Check objeto) throws RuntimeException {
        JPADao.delete(Check.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Check.class, id, ativar);
    }

    public List<Check> RetrieveAvailableChecks() throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            return em.createQuery(
                "SELECT c FROM Check c WHERE c.status = 'A'",
                Check.class
            ).getResultList();
        }, false);
    }
}
