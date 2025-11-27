package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.VagaEstacionamento;

public class VagaEstacionamentoDAO implements InterfaceDAO<VagaEstacionamento> {

    @Override
    public void Create(VagaEstacionamento objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public VagaEstacionamento Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(VagaEstacionamento.class, id), false);
    }

    @Override
    public List<VagaEstacionamento> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(VagaEstacionamento.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT v FROM VagaEstacionamento v WHERE v." + atributo + " LIKE :valor";
            TypedQuery<VagaEstacionamento> q = em.createQuery(jpql, VagaEstacionamento.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(VagaEstacionamento objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(VagaEstacionamento objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            VagaEstacionamento managed = em.find(VagaEstacionamento.class, objeto.getId());
            if (managed != null)
                em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            VagaEstacionamento vaga = em.find(VagaEstacionamento.class, id);
            if (vaga != null) {
                vaga.setStatus(ativar ? 'A' : 'I');
                em.merge(vaga);
            }
        }, true);
    }
}
