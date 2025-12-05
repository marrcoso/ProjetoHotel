package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Servico;

public class ServicoDAO implements InterfaceDAO<Servico> {

    @Override
    public void Create(Servico objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Servico Retrieve(int id) throws RuntimeException {
        return JPADao.find(Servico.class, id);
    }

    @Override
    public List<Servico> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Servico.class, "Servico", atributo, valor);
    }

    @Override
    public void Update(Servico objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Servico objeto) throws RuntimeException {
        JPADao.delete(Servico.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Servico.class, id, ativar);
    }
}
