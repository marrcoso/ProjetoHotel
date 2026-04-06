package service;

import java.util.List;
import model.DAO.OrdemServicoDAO;
import model.OrdemServico;

public class OrdemServicoService implements InterfaceService<OrdemServico> {

    private final OrdemServicoDAO dao;

    public OrdemServicoService() {
        this.dao = new OrdemServicoDAO();
    }

    @Override
    public void Criar(OrdemServico objeto) throws RuntimeException {
        dao.Create(objeto);
    }

    @Override
    public OrdemServico Carregar(int id) throws RuntimeException {
        return dao.Retrieve(id);
    }

    @Override
    public List<OrdemServico> Carregar(String atributo, String valor) throws RuntimeException {
        return dao.Retrieve(atributo, valor);
    }

    public List<OrdemServico> Listar() {
        return dao.Retrieve("", "");
    }

    @Override
    public void Atualizar(OrdemServico objeto) throws RuntimeException {
        dao.Update(objeto);
    }

    @Override
    public void Apagar(OrdemServico objeto) throws RuntimeException {
        dao.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        // Seguindo o padrão de CopaQuartoDAO: ativar vira Pendente, inativar vira Cancelado
        dao.mudarStatus(id, ativar ? 'P' : 'C');
    }

    public void mudarStatus(int id, char novoStatus) throws RuntimeException {
        dao.mudarStatus(id, novoStatus);
    }
}
