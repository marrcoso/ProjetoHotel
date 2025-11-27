package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Produto;

public class ProdutoDAO implements InterfaceDAO<Produto> {

    @Override
    public void Create(Produto objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Produto Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Produto.class, id), false);
    }

    @Override
    public List<Produto> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Produto.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT p FROM Produto p WHERE p." + atributo + " LIKE :valor";
            TypedQuery<Produto> q = em.createQuery(jpql, Produto.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Produto objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Produto objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Produto managed = em.find(Produto.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Produto produto = em.find(Produto.class, id);
            if (produto != null) {
                produto.setStatus(ativar ? 'A' : 'I');
                em.merge(produto);
            }
        }, true);
    }
}
