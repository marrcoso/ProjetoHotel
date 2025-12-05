package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Marca;

public class MarcaDAO implements InterfaceDAO<Marca> {

    @Override
    public void Create(Marca objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Marca Retrieve(int id) throws RuntimeException {
        return JPADao.find(Marca.class, id);
    }

    @Override
    public List<Marca> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Marca.class, "Marca", atributo, valor);
    }

    @Override
    public void Update(Marca objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Marca objeto) throws RuntimeException {
        JPADao.delete(Marca.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Marca.class, id, ativar);
    }
}
