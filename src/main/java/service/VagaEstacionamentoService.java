package service;

import java.util.List;
import model.DAO.VagaEstacionamentoDAO;
import model.VagaEstacionamento;

public class VagaEstacionamentoService {

    public static void Criar(VagaEstacionamento objeto) {
        VagaEstacionamentoDAO vagaDAO = new VagaEstacionamentoDAO();
        vagaDAO.Create(objeto);
    }

    public static VagaEstacionamento Carregar(int id) {
        VagaEstacionamentoDAO vagaDAO = new VagaEstacionamentoDAO();
        return vagaDAO.Retrieve(id);
    }

    public static List<VagaEstacionamento> Carregar(String atributo, String valor) {
        VagaEstacionamentoDAO vagaDAO = new VagaEstacionamentoDAO();
        return vagaDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(VagaEstacionamento objeto) {
        VagaEstacionamentoDAO vagaDAO = new VagaEstacionamentoDAO();
        vagaDAO.Update(objeto);
    }

    public static void Apagar(VagaEstacionamento objeto) {
        VagaEstacionamentoDAO vagaDAO = new VagaEstacionamentoDAO();
        vagaDAO.Delete(objeto);
    }
}