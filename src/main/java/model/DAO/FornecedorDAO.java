package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Fornecedor;

public class FornecedorDAO implements InterfaceDAO<Fornecedor> {

    @Override
    public void Create(Fornecedor objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Fornecedor Retrieve(int id) throws RuntimeException {
        return JPADao.find(Fornecedor.class, id);
    }

    @Override
    public List<Fornecedor> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Fornecedor.class, "Fornecedor", atributo, valor);
    }

    @Override
    public void Update(Fornecedor objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Fornecedor objeto) throws RuntimeException {
        JPADao.delete(Fornecedor.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Fornecedor.class, id, ativar);
    }
}
