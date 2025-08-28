package model.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;


public class FuncionarioDAO implements InterfaceDAO<Funcionario>{

    @Override
    public void Create(Funcionario objeto) {
        Connection conexao = ConnectionFactory.getConnection();

        String sqlInstrucao = "Insert into funcionario(id, "
            + "nome, "
            + "fone1, "
            + "fone2, "
            + "email, "
            + "cep, "
            + "logradouro, "
            + "bairro, "
            + "cidade, "
            + "complemento, "
            + "data_cadastro, "
            + "cpf, "
            + "rg, "
            + "obs, "
            + "status, "
            + "razao_social, "
            + "cnpj, "
            + "inscricao_estadual, "
            + "contato) "
            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        
        PreparedStatement pstm = null;
        
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
        } catch (SQLException ex) {
            Logger.getLogger(HospedeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Funcionario Retrieve(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Funcionario> Retrieve(String atributo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Update(Funcionario objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Delete(Funcionario objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
