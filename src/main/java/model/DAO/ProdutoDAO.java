package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.Produto;

public class ProdutoDAO implements InterfaceDAO<Produto> {

    @Override
    public void Create(Produto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public Produto Retrieve(int id) throws RuntimeException {
        return JPADao.find(Produto.class, id);
    }

    @Override
    public List<Produto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(Produto.class, "Produto", atributo, valor);
    }

    @Override
    public void Update(Produto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(Produto objeto) throws RuntimeException {
        JPADao.delete(Produto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(Produto.class, id, ativar);
    }
}
