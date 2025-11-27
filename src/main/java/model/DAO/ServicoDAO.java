package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Servico;

public class ServicoDAO implements InterfaceDAO<Servico> {

    @Override
    public void Create(Servico objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Servico Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Servico.class, id), false);
    }

    @Override
    public List<Servico> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Servico.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT s FROM Servico s WHERE s." + atributo + " LIKE :valor";
            TypedQuery<Servico> q = em.createQuery(jpql, Servico.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Servico objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Servico objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Servico managed = em.find(Servico.class, objeto.getId());
            if (managed != null)
                em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Servico servico = em.find(Servico.class, id);
            if (servico != null) {
                servico.setStatus(ativar ? 'A' : 'I');
                em.merge(servico);
            }
        }, true);
    }
}
