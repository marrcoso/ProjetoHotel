package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Fornecedor;

public class FornecedorDAO implements InterfaceDAO<Fornecedor> {

    @Override
    public void Create(Fornecedor objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Fornecedor Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Fornecedor.class, id), false);
    }

    @Override
    public List<Fornecedor> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Fornecedor.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT f FROM Fornecedor f WHERE f." + atributo + " LIKE :valor";
            TypedQuery<Fornecedor> q = em.createQuery(jpql, Fornecedor.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Fornecedor objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Fornecedor objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Fornecedor managed = em.find(Fornecedor.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Fornecedor fornecedor = em.find(Fornecedor.class, id);
            if (fornecedor != null) {
                fornecedor.setStatus(ativar ? 'A' : 'I');
                em.merge(fornecedor);
            }
        }, true);
    }
}
