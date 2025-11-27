package service;

import java.util.List;
import model.DAO.VagaEstacionamentoDAO;
import model.VagaEstacionamento;

public class VagaEstacionamentoService implements InterfaceService<VagaEstacionamento> {

    private final VagaEstacionamentoDAO vagaDAO = new VagaEstacionamentoDAO();

    @Override
    public void Criar(VagaEstacionamento objeto) throws RuntimeException {
        vagaDAO.Create(objeto);
    }

    @Override
    public VagaEstacionamento Carregar(int id) throws RuntimeException {
        return vagaDAO.Retrieve(id);
    }

    @Override
    public List<VagaEstacionamento> Carregar(String atributo, String valor) throws RuntimeException {
        return vagaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(VagaEstacionamento objeto) throws RuntimeException {
        vagaDAO.Update(objeto);
    }

    @Override
    public void Apagar(VagaEstacionamento objeto) throws RuntimeException {
        vagaDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        vagaDAO.AtivarInativar(id, ativar);
    }
}