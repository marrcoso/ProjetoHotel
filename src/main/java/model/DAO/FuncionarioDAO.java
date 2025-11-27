package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Funcionario;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {

    @Override
    public void Create(Funcionario objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Funcionario Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Funcionario.class, id), false);
    }

    @Override
    public List<Funcionario> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Funcionario.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT f FROM Funcionario f WHERE f." + atributo + " LIKE :valor";
            TypedQuery<Funcionario> q = em.createQuery(jpql, Funcionario.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Funcionario objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Funcionario objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Funcionario managed = em.find(Funcionario.class, objeto.getId());
            if (managed != null) em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Funcionario funcionario = em.find(Funcionario.class, id);
            if (funcionario != null) {
                funcionario.setStatus(ativar ? 'A' : 'I');
                em.merge(funcionario);
            }
        }, true);
    }
}
