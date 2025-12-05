package model.DAO;

import java.util.List;

import model.DAO.JPA.JPADao;
import model.VagaEstacionamento;

public class VagaEstacionamentoDAO implements InterfaceDAO<VagaEstacionamento> {

    @Override
    public void Create(VagaEstacionamento objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public VagaEstacionamento Retrieve(int id) throws RuntimeException {
        return JPADao.find(VagaEstacionamento.class, id);
    }

    @Override
    public List<VagaEstacionamento> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(VagaEstacionamento.class, "VagaEstacionamento", atributo, valor);
    }

    @Override
    public void Update(VagaEstacionamento objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(VagaEstacionamento objeto) throws RuntimeException {
        JPADao.delete(VagaEstacionamento.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(VagaEstacionamento.class, id, ativar);
    }
}
