package model.DAO;

import java.util.List;

import javax.persistence.TypedQuery;

import model.Check;
import model.CheckQuarto;
import model.DAO.JPA.JPADao;

public class CheckQuartoDAO implements InterfaceDAO<CheckQuarto> {

    @Override
    public void Create(CheckQuarto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public CheckQuarto Retrieve(int id) throws RuntimeException {
        return JPADao.find(CheckQuarto.class, id);
    }

    @Override
    public List<CheckQuarto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(CheckQuarto.class, "CheckQuarto", atributo, valor);
    }

    @Override
    public void Update(CheckQuarto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(CheckQuarto objeto) throws RuntimeException {
        JPADao.delete(CheckQuarto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(CheckQuarto.class, id, ativar);
    }

    public List<CheckQuarto> RetrieveByCheck(int checkId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<CheckQuarto> query = em.createQuery(
                "SELECT cq FROM CheckQuarto cq JOIN FETCH cq.quarto WHERE cq.check.id = :checkId ORDER BY cq.id",
                CheckQuarto.class
            );
            query.setParameter("checkId", checkId);
            return query.getResultList();
        }, false);
    }

     public void ReplaceCheckQuartos(int checkId, List<CheckQuarto> checkQuartos) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Check check = em.find(Check.class, checkId);
            if (check == null) {
                throw new RuntimeException("Check não encontrado para vincular quartos.");
            }

            TypedQuery<CheckQuarto> query = em.createQuery(
                "SELECT cq FROM CheckQuarto cq WHERE cq.check.id = :checkId",
                CheckQuarto.class
            );
            query.setParameter("checkId", checkId);

            for (CheckQuarto checkQuarto : query.getResultList()) {
                em.remove(checkQuarto);
            }

            for (CheckQuarto cq : checkQuartos) {
                cq.setCheck(check);
                if (cq.getId() == 0) {
                    em.persist(cq);
                } else {
                    em.merge(cq);
                }
            }
        }, true);
    }
}
