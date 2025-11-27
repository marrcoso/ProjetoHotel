package model.DAO;

import java.util.List;
import javax.persistence.TypedQuery;
import model.DAO.JPA.JPADao;
import model.Veiculo;

public class VeiculoDAO implements InterfaceDAO<Veiculo> {

    @Override
    public void Create(Veiculo objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.persist(objeto), true);
    }

    @Override
    public Veiculo Retrieve(int id) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> em.find(Veiculo.class, id), false);
    }

    @Override
    public List<Veiculo> Retrieve(String atributo, String valor) throws RuntimeException {
        JPADao.validAttribute(Veiculo.class, atributo);
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT v FROM Veiculo v WHERE v." + atributo + " LIKE :valor";
            TypedQuery<Veiculo> q = em.createQuery(jpql, Veiculo.class);
            q.setParameter("valor", "%" + valor + "%");
            return q.getResultList();
        }, false);
    }

    @Override
    public void Update(Veiculo objeto) throws RuntimeException {
        JPADao.executeVoid(em -> em.merge(objeto), true);
    }

    @Override
    public void Delete(Veiculo objeto) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Veiculo managed = em.find(Veiculo.class, objeto.getId());
            if (managed != null)
                em.remove(managed);
        }, true);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Veiculo veiculo = em.find(Veiculo.class, id);
            if (veiculo != null) {
                veiculo.setStatus(ativar ? 'A' : 'I');
                em.merge(veiculo);
            }
        }, true);
    }
}
