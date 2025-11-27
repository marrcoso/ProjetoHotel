package service;

import java.util.List;

import model.DAO.MarcaDAO;
import model.Marca;

public class MarcaService implements InterfaceService<Marca> {

    private final MarcaDAO marcaDAO;

    public MarcaService() {
        this.marcaDAO = new MarcaDAO();
    }

    @Override
    public void Criar(Marca objeto) throws RuntimeException {
        marcaDAO.Create(objeto);
    }

    @Override
    public Marca Carregar(int id) throws RuntimeException {
        return marcaDAO.Retrieve(id);
    }

    @Override
    public List<Marca> Carregar(String atributo, String valor) throws RuntimeException {
        return marcaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Marca objeto) throws RuntimeException {
        marcaDAO.Update(objeto);
    }

    @Override
    public void Apagar(Marca objeto) throws RuntimeException {
        marcaDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        marcaDAO.AtivarInativar(id, ativar);
    }
}