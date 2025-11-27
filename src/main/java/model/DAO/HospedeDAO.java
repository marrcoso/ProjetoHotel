package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Hospede;

public class HospedeDAO implements InterfaceDAO<Hospede> {

    @Override
    public void Create(Hospede objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Hospede Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Hospede.class, id), false);
    }

    @Override
    public List<Hospede> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Hospede.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT h FROM Hospede h WHERE h." + atributo + " LIKE :valor";
            TypedQuery<Hospede> q = em.createQuery(jpql, Hospede.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Hospede objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Hospede objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Hospede managed = em.find(Hospede.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Hospede hospede = em.find(Hospede.class, id);
            if (hospede != null) {
                hospede.setStatus(ativar ? 'A' : 'I');
                em.merge(hospede);
            }
        }, true);
    }
}