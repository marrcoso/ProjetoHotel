package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioService implements InterfaceService<Funcionario> {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    @Override
    public void Criar(Funcionario objeto) throws SQLException {
        funcionarioDAO.Create(objeto);
    }

    @Override
    public Funcionario Carregar(int id) throws SQLException {
        return funcionarioDAO.Retrieve(id);
    }

    @Override
    public List<Funcionario> Carregar(String atributo, String valor) throws SQLException {
        return funcionarioDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Funcionario objeto) throws SQLException {
        funcionarioDAO.Update(objeto);
    }

    @Override
    public void Apagar(Funcionario objeto) throws SQLException {
        funcionarioDAO.Delete(objeto);
    }
}