package service;

import java.util.List;
import model.Receber;
import model.DAO.ReceberDAO;

public class ReceberService implements InterfaceService<Receber> {

    private final ReceberDAO receberDAO;

    public ReceberService() {
        this.receberDAO = new ReceberDAO();
    }

    @Override
    public void Criar(Receber objeto) throws RuntimeException {
        receberDAO.Create(objeto);
    }

    @Override
    public Receber Carregar(int id) throws RuntimeException {
        return receberDAO.Retrieve(id);
    }

    @Override
    public List<Receber> Carregar(String atributo, String valor) throws RuntimeException {
        return receberDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Receber objeto) throws RuntimeException {
        receberDAO.Update(objeto);
    }

    @Override
    public void Apagar(Receber objeto) throws RuntimeException {
        receberDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        receberDAO.AtivarInativar(id, ativar);
    }

    public Receber CarregarPorCheck(int checkId) throws RuntimeException {
        return receberDAO.RetrieveByCheck(checkId);
    }
}
