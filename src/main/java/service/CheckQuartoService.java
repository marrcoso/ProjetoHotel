package service;

import java.util.List;
import java.util.stream.Collectors;

import model.CheckQuarto;
import model.DAO.CheckQuartoDAO;
import model.Quarto;

public class CheckQuartoService implements InterfaceService<CheckQuarto> {

    private final CheckQuartoDAO checkQuartoDAO;

    public CheckQuartoService() {
        this.checkQuartoDAO = new CheckQuartoDAO();
    }

    @Override
    public void Criar(CheckQuarto objeto) throws RuntimeException {
        checkQuartoDAO.Create(objeto);
    }

    @Override
    public CheckQuarto Carregar(int id) throws RuntimeException {
        return checkQuartoDAO.Retrieve(id);
    }

    @Override
    public List<CheckQuarto> Carregar(String atributo, String valor) throws RuntimeException {
        return checkQuartoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(CheckQuarto objeto) throws RuntimeException {
        checkQuartoDAO.Update(objeto);
    }

    @Override
    public void Apagar(CheckQuarto objeto) throws RuntimeException {
        checkQuartoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        checkQuartoDAO.AtivarInativar(id, ativar);
    }

    public List<CheckQuarto> carregarPorCheck(int checkId) throws RuntimeException {
        return checkQuartoDAO.RetrieveByCheck(checkId);
    }

    public void substituirQuartosDoCheck(int checkId, List<Quarto> quartos) throws RuntimeException {
        List<Integer> idsQuartos = quartos.stream()
            .map(Quarto::getId)
            .collect(Collectors.toList());
        checkQuartoDAO.ReplaceQuartosDoCheck(checkId, idsQuartos);
    }
}
