package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Hospede;

public class HospedeDAO implements InterfaceDAO<Hospede> {

    @Override
    public void Create(Hospede objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Hospede Retrieve(int id) throws RuntimeException {
        return JPADao.find(Hospede.class, id);
    }

    @Override
    public List<Hospede> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Hospede.class, "Hospede", atributo, valor);
    }

    @Override
    public void Update(Hospede objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Hospede objeto) throws RuntimeException {
        JPADao.delete(Hospede.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Hospede.class, id, ativar);
    }
}