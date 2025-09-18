package service;

import java.util.List;

import model.DAO.FuncionarioDAO;
import model.Funcionario;
import model.Hospede;

public class FuncionarioService {

    public static void Criar(Funcionario objeto) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.Create(objeto);
    }

    public static Funcionario Carregar(int id) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        return funcionarioDAO.Retrieve(id);
    }

    public static List<Funcionario> Carregar(String atributo, String valor) {
        FuncionarioDAO hospedeDAO = new FuncionarioDAO();
        return hospedeDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Funcionario objeto) {
        FuncionarioDAO hospedeDAO = new FuncionarioDAO();
        hospedeDAO.Update(objeto);
    }

    public static void Apagar(Funcionario objeto) {
        FuncionarioDAO hospedeDAO = new FuncionarioDAO();
        hospedeDAO.Delete(objeto);
    }
}