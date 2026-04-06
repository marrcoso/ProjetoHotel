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
}
