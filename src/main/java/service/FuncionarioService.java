package service;

import java.util.List;
import model.DAO.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioService implements InterfaceService<Funcionario> {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    @Override
    public void Criar(Funcionario objeto) throws RuntimeException {
        funcionarioDAO.Create(objeto);
    }

    @Override
    public Funcionario Carregar(int id) throws RuntimeException {
        return funcionarioDAO.Retrieve(id);
    }

    @Override
    public List<Funcionario> Carregar(String atributo, String valor) throws RuntimeException {
        return funcionarioDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Funcionario objeto) throws RuntimeException {
        funcionarioDAO.Update(objeto);
    }

    @Override
    public void Apagar(Funcionario objeto) throws RuntimeException {
        funcionarioDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        funcionarioDAO.AtivarInativar(id, ativar);
    }
}