package model.DAO;

import java.util.List;
import model.MovimentoCaixa;
import model.DAO.JPA.JPADao;

public class MovimentoCaixaDAO implements InterfaceDAO<MovimentoCaixa> {

    @Override
    public void Create(MovimentoCaixa objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public MovimentoCaixa Retrieve(int id) throws RuntimeException {
        return JPADao.find(MovimentoCaixa.class, id);
    }

    @Override
    public List<MovimentoCaixa> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(MovimentoCaixa.class, "MovimentoCaixa", atributo, valor);
    }

    @Override
    public void Update(MovimentoCaixa objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(MovimentoCaixa objeto) throws RuntimeException {
        JPADao.delete(MovimentoCaixa.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(MovimentoCaixa.class, id, ativar);
    }

    public float getSumAtivosByCaixa(int caixaId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT SUM(m.valor) FROM MovimentoCaixa m WHERE m.caixa.id = :caixaId AND m.status = 'A'";
            Double sum = em.createQuery(jpql, Double.class)
                    .setParameter("caixaId", caixaId)
                    .getSingleResult();
            return sum != null ? sum.floatValue() : 0.0f;
        }, false);
    }

    public List<MovimentoCaixa> RetrievePorCaixa(int caixaId, String statusFilter) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT m FROM MovimentoCaixa m WHERE m.caixa.id = :caixaId";
            if ("Ativos".equals(statusFilter)) {
                jpql += " AND m.status = 'A'";
            } else if ("Inativados".equals(statusFilter)) {
                jpql += " AND m.status = 'I'";
            }
            return em.createQuery(jpql, MovimentoCaixa.class)
                    .setParameter("caixaId", caixaId)
                    .getResultList();
        }, false);
    }

    public MovimentoCaixa RetrievePorReceber(int receberId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT m FROM MovimentoCaixa m WHERE m.receber.id = :receberId";
            List<MovimentoCaixa> results = em.createQuery(jpql, MovimentoCaixa.class)
                    .setParameter("receberId", receberId)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        }, false);
    }
}
