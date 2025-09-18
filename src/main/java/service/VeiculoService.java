package service;

import java.util.List;
import model.DAO.VeiculoDAO;
import model.Veiculo;

public class VeiculoService {

    public static void Criar(Veiculo objeto) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        veiculoDAO.Create(objeto);
    }

    public static Veiculo Carregar(int id) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        return veiculoDAO.Retrieve(id);
    }

    public static List<Veiculo> Carregar(String atributo, String valor) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        return veiculoDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Veiculo objeto) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        veiculoDAO.Update(objeto);
    }

    public static void Apagar(Veiculo objeto) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        veiculoDAO.Delete(objeto);
    }
}