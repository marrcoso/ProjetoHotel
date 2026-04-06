package service;

import java.util.List;

import model.Caixa;
import model.DAO.CaixaDAO;

public class CaixaService implements InterfaceService<Caixa> {

    private final CaixaDAO caixaDAO;

    public CaixaService() {
        this.caixaDAO = new CaixaDAO();
    }

    @Override
    public void Criar(Caixa objeto) throws RuntimeException {
        if (getCaixaAberto() != null) {
            throw new RuntimeException("Já existe um caixa aberto!");
        }
        caixaDAO.Create(objeto);
    }

    @Override
    public Caixa Carregar(int id) throws RuntimeException {
        return caixaDAO.Retrieve(id);
    }

    @Override
    public List<Caixa> Carregar(String atributo, String valor) throws RuntimeException {
        return caixaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Caixa objeto) throws RuntimeException {
        caixaDAO.Update(objeto);
    }

    @Override
    public void Apagar(Caixa objeto) throws RuntimeException {
        caixaDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        caixaDAO.AtivarInativar(id, ativar);
    }

    public Caixa getCaixaAberto() throws RuntimeException {
        return caixaDAO.RetrieveAberto();
    }
}
