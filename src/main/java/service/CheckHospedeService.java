package service;

import java.util.List;
import java.util.stream.Collectors;

import model.CheckHospede;
import model.DAO.CheckHospedeDAO;
import model.Hospede;

public class CheckHospedeService implements InterfaceService<CheckHospede> {

    private final CheckHospedeDAO checkHospedeDAO;

    public CheckHospedeService() {
        this.checkHospedeDAO = new CheckHospedeDAO();
    }

    @Override
    public void Criar(CheckHospede objeto) throws RuntimeException {
        checkHospedeDAO.Create(objeto);
    }

    @Override
    public CheckHospede Carregar(int id) throws RuntimeException {
        return checkHospedeDAO.Retrieve(id);
    }

    @Override
    public List<CheckHospede> Carregar(String atributo, String valor) throws RuntimeException {
        return checkHospedeDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(CheckHospede objeto) throws RuntimeException {
        checkHospedeDAO.Update(objeto);
    }

    @Override
    public void Apagar(CheckHospede objeto) throws RuntimeException {
        checkHospedeDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        checkHospedeDAO.AtivarInativar(id, ativar);
    }

    public List<CheckHospede> carregarPorCheck(int checkId) throws RuntimeException {
        return checkHospedeDAO.RetrieveByCheck(checkId);
    }

    public void substituirHospedesDoCheck(int checkId, List<Hospede> hospedes) throws RuntimeException {
        List<Integer> idsHospedes = hospedes.stream()
            .map(Hospede::getId)
            .collect(Collectors.toList());
        checkHospedeDAO.ReplaceHospedesDoCheck(checkId, idsHospedes);
    }
}
