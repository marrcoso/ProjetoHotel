package service;

import java.util.List;
import model.DAO.HospedeDAO;
import model.Hospede;

public class HospedeService {

    public static void Criar(Hospede objeto) {
        HospedeDAO hospedeDAO = new HospedeDAO();
        hospedeDAO.Create(objeto);
    }

    public static Hospede Carregar(int id) {

        HospedeDAO hospedeDAO = new HospedeDAO();
        return hospedeDAO.Retrieve(id);
    }

    public static List<Hospede> Carregar(String atributo, String valor) {
        HospedeDAO hospedeDAO = new HospedeDAO();
        return hospedeDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Hospede objeto) {
        HospedeDAO hospedeDAO = new HospedeDAO();
        hospedeDAO.Update(objeto);
    }

    public static void Apagar(Hospede objeto) {
        HospedeDAO hospedeDAO = new HospedeDAO();
        hospedeDAO.Delete(objeto);
    }
}