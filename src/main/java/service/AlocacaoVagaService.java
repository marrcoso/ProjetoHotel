package service;

import java.util.List;

import model.AlocacaoVaga;
import model.DAO.AlocacaoVagaDAO;

public class AlocacaoVagaService implements InterfaceService<AlocacaoVaga> {

    private final AlocacaoVagaDAO alocacaoVagaDAO;

    public AlocacaoVagaService() {
        this.alocacaoVagaDAO = new AlocacaoVagaDAO();
    }

    @Override
    public void Criar(AlocacaoVaga objeto) throws RuntimeException {
        alocacaoVagaDAO.Create(objeto);
    }

    @Override
    public AlocacaoVaga Carregar(int id) throws RuntimeException {
        return alocacaoVagaDAO.Retrieve(id);
    }

    @Override
    public List<AlocacaoVaga> Carregar(String atributo, String valor) throws RuntimeException {
        return alocacaoVagaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(AlocacaoVaga objeto) throws RuntimeException {
        alocacaoVagaDAO.Update(objeto);
    }

    @Override
    public void Apagar(AlocacaoVaga objeto) throws RuntimeException {
        alocacaoVagaDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        alocacaoVagaDAO.AtivarInativar(id, ativar);
    }

    public List<AlocacaoVaga> carregarPorCheck(int checkId) throws RuntimeException {
        return alocacaoVagaDAO.RetrieveByCheck(checkId);
    }

    public void substituirAlocacoesDoCheck(int checkId, List<AlocacaoVaga> alocacoes) throws RuntimeException {
        alocacaoVagaDAO.ReplaceAlocacoesDoCheck(checkId, alocacoes);
    }
}
