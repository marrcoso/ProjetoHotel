package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Veiculo;

public class VeiculoDAO implements InterfaceDAO<Veiculo> {

    @Override
    public void Create(Veiculo objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Veiculo Retrieve(int id) throws RuntimeException {
        return JPADao.find(Veiculo.class, id);
    }

    @Override
    public List<Veiculo> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Veiculo.class, "Veiculo", atributo, valor);
    }

    @Override
    public void Update(Veiculo objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Veiculo objeto) throws RuntimeException {
        JPADao.delete(Veiculo.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Veiculo.class, id, ativar);
    }
}
