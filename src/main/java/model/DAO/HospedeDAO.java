package model.DAO;
import java.util.List;
import model.Hospede;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class HospedeDAO implements InterfaceDAO<Hospede>{

    @Override
    public void Create(Hospede objeto) {
        String sqlInstrucao = "Insert into hospede("
            + "nome, "
            + "fone, "
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
            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
        PreparedStatement pstm = null;
        
        try {
            Connection conexao = ConnectionFactory.getConnection();
            pstm = conexao.prepareStatement(sqlInstrucao);
            
            pstm.setString(1, objeto.getNome());
            pstm.setString(2, objeto.getFone1());
            pstm.setString(3, objeto.getFone2());
            pstm.setString(4, objeto.getEmail());
            pstm.setString(5, objeto.getCep());
            pstm.setString(6, objeto.getLogradouro());
            pstm.setString(7, objeto.getBairro());
            pstm.setString(8, objeto.getCidade());
            pstm.setString(9, objeto.getComplemento());
            pstm.setString(10, objeto.getDataCadastro());
            pstm.setString(11, objeto.getCpf());
            pstm.setString(12, objeto.getRg());
            pstm.setString(13, objeto.getObs());
            pstm.setString(14, String.valueOf(objeto.getStatus()));
            pstm.setString(15, objeto.getRazaoSocial());
            pstm.setString(16, objeto.getCnpj());
            pstm.setString(17, objeto.getInscricaoEstdual());
            pstm.setString(18, objeto.getContato());
            
            
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Hospede Retrieve(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Hospede> Retrieve(String atributo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Update(Hospede objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Delete(Hospede objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
