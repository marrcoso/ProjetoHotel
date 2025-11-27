package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Quarto;

public class QuartoDAO implements InterfaceDAO<Quarto> {

    @Override
    public void Create(Quarto objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Quarto Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Quarto.class, id), false);
    }

    @Override
    public List<Quarto> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Quarto.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT q FROM Quarto q WHERE q." + atributo + " LIKE :valor";
            TypedQuery<Quarto> q = em.createQuery(jpql, Quarto.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Quarto objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Quarto objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Quarto managed = em.find(Quarto.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Quarto quarto = em.find(Quarto.class, id);
            if (quarto != null) {
                quarto.setStatus(ativar ? 'A' : 'I');
                em.merge(quarto);
            }
        }, true);
    }
}
