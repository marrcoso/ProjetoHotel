package model.DAO;

import java.util.List;
import model.CopaQuarto;
import model.DAO.JPA.JPADao;

public class CopaQuartoDAO implements InterfaceDAO<CopaQuarto> {

    @Override
    public void Create(CopaQuarto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public CopaQuarto Retrieve(int id) throws RuntimeException {
        return JPADao.find(CopaQuarto.class, id);
    }

    @Override
    public List<CopaQuarto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(CopaQuarto.class, "CopaQuarto", atributo, valor);
    }

    @Override
    public void Update(CopaQuarto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(CopaQuarto objeto) throws RuntimeException {
        JPADao.delete(CopaQuarto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        mudarStatus(id, ativar ? 'P' : 'C');
    }

    public void mudarStatus(int id, char status) throws RuntimeException {
        CopaQuarto copa = Retrieve(id);
        if (copa != null) {
            copa.setStatus(status);
            Update(copa);
        }
    }

    public float sumTotalFinalizadoPorCheck(int checkId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            String jpql = "SELECT SUM(c.quantidade * c.valorUnitario) FROM CopaQuarto c " +
                          "WHERE c.status = 'F' AND c.checkQuarto.check.id = :checkId";
            Double result = em.createQuery(jpql, Double.class)
                             .setParameter("checkId", checkId)
                             .getSingleResult();
            return result != null ? result.floatValue() : 0.0f;
        }, false);
    }
}
