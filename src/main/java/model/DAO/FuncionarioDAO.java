package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Funcionario;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {

    @Override
    public void Create(Funcionario objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Funcionario Retrieve(int id) throws RuntimeException {
        return JPADao.find(Funcionario.class, id);
    }

    @Override
    public List<Funcionario> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Funcionario.class, "Funcionario", atributo, valor);
    }

    @Override
    public void Update(Funcionario objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Funcionario objeto) throws RuntimeException {
        JPADao.delete(Funcionario.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Funcionario.class, id, ativar);
    }
}
