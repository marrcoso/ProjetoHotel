package model.DAO;

import java.util.List;

import model.Caixa;
import model.DAO.JPA.JPADao;

public class CaixaDAO implements InterfaceDAO<Caixa> {

    @Override
    public void Create(Caixa objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Caixa Retrieve(int id) throws RuntimeException {
        return JPADao.find(Caixa.class, id);
    }

    @Override
    public List<Caixa> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Caixa.class, "Caixa", atributo, valor);
    }

    @Override
    public void Update(Caixa objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Caixa objeto) throws RuntimeException {
        JPADao.delete(Caixa.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Caixa.class, id, ativar);
    }

    public Caixa RetrieveAberto() throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            List<Caixa> results = em.createQuery("SELECT c FROM Caixa c WHERE c.status = 'A'", Caixa.class)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        }, false);
    }
}
