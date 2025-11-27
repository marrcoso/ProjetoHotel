package service;

import java.util.List;
import model.DAO.ServicoDAO;
import model.Servico;

public class ServicoService implements InterfaceService<Servico> {

    private final ServicoDAO servicoDAO = new ServicoDAO();

    @Override
    public void Criar(Servico objeto) throws RuntimeException {
        servicoDAO.Create(objeto);
    }

    @Override
    public Servico Carregar(int id) throws RuntimeException {
        return servicoDAO.Retrieve(id);
    }

    @Override
    public List<Servico> Carregar(String atributo, String valor) throws RuntimeException {
        return servicoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Servico objeto) throws RuntimeException {
        servicoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Servico objeto) throws RuntimeException {
        servicoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        servicoDAO.AtivarInativar(id, ativar);
    }
}