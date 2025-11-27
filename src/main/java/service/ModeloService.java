package service;

import java.util.List;
import model.DAO.ModeloDAO;
import model.Modelo;

public class ModeloService implements InterfaceService<Modelo> {

    private final ModeloDAO modeloDAO = new ModeloDAO();

    @Override
    public void Criar(Modelo objeto) throws RuntimeException {
        modeloDAO.Create(objeto);
    }

    @Override
    public Modelo Carregar(int id) throws RuntimeException {
        return modeloDAO.Retrieve(id);
    }

    @Override
    public List<Modelo> Carregar(String atributo, String valor) throws RuntimeException {
        return modeloDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Modelo objeto) throws RuntimeException {
        modeloDAO.Update(objeto);
    }

    @Override
    public void Apagar(Modelo objeto) throws RuntimeException {
        modeloDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        modeloDAO.AtivarInativar(id, ativar);
    }
}