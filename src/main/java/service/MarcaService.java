package service;

import java.util.List;
import model.DAO.MarcaDAO;
import model.Marca;

public class MarcaService {

    public static void Criar(Marca objeto) {
        MarcaDAO marcaDAO = new MarcaDAO();
        marcaDAO.Create(objeto);
    }

    public static Marca Carregar(int id) {
        MarcaDAO marcaDAO = new MarcaDAO();
        return marcaDAO.Retrieve(id);
    }

    public static List<Marca> Carregar(String atributo, String valor) {
        MarcaDAO marcaDAO = new MarcaDAO();
        return marcaDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Marca objeto) {
        MarcaDAO marcaDAO = new MarcaDAO();
        marcaDAO.Update(objeto);
    }

    public static void Apagar(Marca objeto) {
        MarcaDAO marcaDAO = new MarcaDAO();
        marcaDAO.Delete(objeto);
    }
}