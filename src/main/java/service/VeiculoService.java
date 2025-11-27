package service;

import java.util.List;
import model.DAO.VeiculoDAO;
import model.Veiculo;

public class VeiculoService implements InterfaceService<Veiculo> {

    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    @Override
    public void Criar(Veiculo objeto) throws RuntimeException {
        veiculoDAO.Create(objeto);
    }

    @Override
    public Veiculo Carregar(int id) throws RuntimeException {
        return veiculoDAO.Retrieve(id);
    }

    @Override
    public List<Veiculo> Carregar(String atributo, String valor) throws RuntimeException {
        return veiculoDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Veiculo objeto) throws RuntimeException {
        veiculoDAO.Update(objeto);
    }

    @Override
    public void Apagar(Veiculo objeto) throws RuntimeException {
        veiculoDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        veiculoDAO.AtivarInativar(id, ativar);
    }
}