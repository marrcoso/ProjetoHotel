package model.DAO;

import java.util.List;
import model.CopaQuarto;
import model.DAO.JPA.JPADao;

public class CopaQuartoDAO implements InterfaceDAO<CopaQuarto> {

    @Override
    public void Create(CopaQuarto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public CopaQuarto Retrieve(int id) throws RuntimeException {
        return JPADao.find(CopaQuarto.class, id);
    }

    @Override
    public List<CopaQuarto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(CopaQuarto.class, "CopaQuarto", atributo, valor);
    }

    @Override
    public void Update(CopaQuarto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(CopaQuarto objeto) throws RuntimeException {
        JPADao.delete(CopaQuarto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        CopaQuarto copa = Retrieve(id);
        if (copa != null) {
            copa.setStatus(ativar ? 'P' : 'C');
            Update(copa);
        }
    }
}
