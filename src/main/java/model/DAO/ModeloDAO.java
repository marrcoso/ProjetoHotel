package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Modelo;

public class ModeloDAO implements InterfaceDAO<Modelo> {

    @Override
    public void Create(Modelo objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Modelo Retrieve(int id) throws RuntimeException {
        return JPADao.find(Modelo.class, id);
    }

    @Override
    public List<Modelo> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Modelo.class, "Modelo", atributo, valor);
    }

    @Override
    public void Update(Modelo objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Modelo objeto) throws RuntimeException {
        JPADao.delete(Modelo.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Modelo.class, id, ativar);
    }
}
