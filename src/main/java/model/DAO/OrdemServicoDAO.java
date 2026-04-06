package model.DAO;

import java.util.List;
import model.OrdemServico;
import model.DAO.JPA.JPADao;

public class OrdemServicoDAO implements InterfaceDAO<OrdemServico> {

    @Override
    public void Create(OrdemServico objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public OrdemServico Retrieve(int id) throws RuntimeException {
        return JPADao.find(OrdemServico.class, id);
    }

    @Override
    public List<OrdemServico> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(OrdemServico.class, "OrdemServico", atributo, valor);
    }

    @Override
    public void Update(OrdemServico objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(OrdemServico objeto) throws RuntimeException {
        JPADao.delete(OrdemServico.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        mudarStatus(id, ativar ? 'P' : 'C');
    }

    public void mudarStatus(int id, char status) throws RuntimeException {
        OrdemServico ordem = Retrieve(id);
        if (ordem != null) {
            ordem.setStatus(status);
            Update(ordem);
        }
    }
}
