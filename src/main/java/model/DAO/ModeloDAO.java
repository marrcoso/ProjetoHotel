package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Modelo;

public class ModeloDAO implements InterfaceDAO<Modelo> {

    @Override
    public void Create(Modelo objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Modelo Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Modelo.class, id), false);
    }

    @Override
    public List<Modelo> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Modelo.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT m FROM Modelo m WHERE m." + atributo + " LIKE :valor";
            TypedQuery<Modelo> q = em.createQuery(jpql, Modelo.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Modelo objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Modelo objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Modelo managed = em.find(Modelo.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Modelo modelo = em.find(Modelo.class, id);
            if (modelo != null) {
                modelo.setStatus(ativar ? 'A' : 'I');
                em.merge(modelo);
            }
        }, true);
    }
}
