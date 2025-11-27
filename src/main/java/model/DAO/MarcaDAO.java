package model.DAO;

import java.util.List;

import javax.persistence.TypedQuery;

import model.DAO.JPA.JPADao;
import model.Marca;

public class MarcaDAO implements InterfaceDAO<Marca> {

    @Override
    public void Create(Marca objeto) {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Marca Retrieve(int id) {
        return JPADao.executeWithEntityManager(em -> em.find(Marca.class, id), false);
    }

    @Override
    public List<Marca> Retrieve(String atributo, String valor) {
        JPADao.validAttribute(Marca.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT m FROM Marca m WHERE m." + atributo + " LIKE :valor";
            TypedQuery<Marca> q = em.createQuery(jpql, Marca.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Marca objeto) {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Marca objeto) {
        JPADao.executeVoid(em -> {
            Marca managed = em.find(Marca.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) {
        JPADao.executeVoid(em -> {
            Marca marca = em.find(Marca.class, id);
            if (marca != null) {
                marca.setStatus(ativar ? 'A' : 'I');
                em.merge(marca);
            }
        }, true);
    }
}
