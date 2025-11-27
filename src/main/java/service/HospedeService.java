package service;

import java.util.List;
import model.DAO.HospedeDAO;
import model.Hospede;

public class HospedeService implements InterfaceService<Hospede> {

    private final HospedeDAO hospedeDAO;

    public HospedeService() {
        this.hospedeDAO = new HospedeDAO();
    }

    @Override
    public void Criar(Hospede objeto) throws RuntimeException {
        hospedeDAO.Create(objeto);
    }

    @Override
    public Hospede Carregar(int id) throws RuntimeException {
        return hospedeDAO.Retrieve(id);
    }

    @Override
    public List<Hospede> Carregar(String atributo, String valor) throws RuntimeException {
        return hospedeDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Hospede objeto) throws RuntimeException {
        hospedeDAO.Update(objeto);
    }

    @Override
    public void Apagar(Hospede objeto) throws RuntimeException {
        hospedeDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        hospedeDAO.AtivarInativar(id, ativar);
    }
}