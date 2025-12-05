package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Quarto;

public class QuartoDAO implements InterfaceDAO<Quarto> {

    @Override
    public void Create(Quarto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Quarto Retrieve(int id) throws RuntimeException {
        return JPADao.find(Quarto.class, id);
    }

    @Override
    public List<Quarto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Quarto.class, "Quarto", atributo, valor);
    }

    @Override
    public void Update(Quarto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Quarto objeto) throws RuntimeException {
        JPADao.delete(Quarto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Quarto.class, id, ativar);
    }
}
