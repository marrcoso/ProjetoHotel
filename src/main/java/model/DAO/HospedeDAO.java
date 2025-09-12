package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.bo.Hospede;

public class HospedeDAO implements InterfaceDAO<Hospede> {

    @Override
    public void Create(Hospede objeto) {

        String sqlInstrucao = "Insert Into hospede("
                + " nome, "
                + " fone, "
                + " fone2,"
                + " email, "
                + " cep, "
                + " logradouro,"
                + " bairro, "
                + " cidade, "
                + " complemento, "
                + " data_cadastro, "
                + " cpf, "
                + " rg, "
                + " obs, "
                + " status, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato ) "
                + " Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
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
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Hospede Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id,"
                + " nome, "
                + " fone, "
                + " fone2,"
                + " email, "
                + " cep, "
                + " logradouro,"
                + " bairro, "
                + " cidade, "
                + " complemento, "
                + " data_cadastro, "
                + " cpf, "
                + " rg, "
                + " obs, "
                + " status, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato "
                + " From hospede"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Hospede hospede = new Hospede();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (rst.next()) {
                hospede.setId(rst.getInt("id"));
                hospede.setNome(rst.getString(2));
                hospede.setFone1(rst.getString("fone"));
                hospede.setFone2(rst.getString("fone2"));
                hospede.setEmail(rst.getString("email"));
                hospede.setCep(rst.getString("cep"));
                hospede.setLogradouro(rst.getString("logradouro"));
                hospede.setBairro(rst.getString("bairro"));
                hospede.setCidade(rst.getString("cidade"));
                hospede.setComplemento(rst.getString("complemento"));
                hospede.setDataCadastro(rst.getString("data_cadastro"));
                hospede.setCpf(rst.getString("cpf"));
                hospede.setRg(rst.getString("rg"));
                hospede.setObs(rst.getString("obs"));
                hospede.setRazaoSocial(rst.getString("razao_social"));
                hospede.setCnpj(rst.getString("cnpj"));
                hospede.setInscricaoEstdual(rst.getString("inscricao_estadual"));
                hospede.setContato(rst.getString("contato"));
                hospede.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return hospede;
        }
    }

    @Override
    public List<Hospede> Retrieve(String atributo, String valor) {

        String sqlInstrucao = "Select "
                + " id,"
                + " nome, "
                + " fone, "
                + " fone2,"
                + " email, "
                + " cep, "
                + " logradouro,"
                + " bairro, "
                + " cidade, "
                + " complemento, "
                + " data_cadastro, "
                + " cpf, "
                + " rg, "
                + " obs, "
                + " status, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato "
                + " From hospede"
                + " Where " + atributo + " like ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Hospede> listaHospedes = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Hospede hospede = new Hospede();
                hospede.setId(rst.getInt("id"));
                hospede.setNome(rst.getString(2));
                hospede.setFone1(rst.getString("fone"));
                hospede.setFone2(rst.getString("fone2"));
                hospede.setEmail(rst.getString("email"));
                hospede.setCep(rst.getString("cep"));
                hospede.setLogradouro(rst.getString("logradouro"));
                hospede.setBairro(rst.getString("bairro"));
                hospede.setCidade(rst.getString("cidade"));
                hospede.setComplemento(rst.getString("complemento"));
                hospede.setDataCadastro(rst.getString("data_cadastro"));
                hospede.setCpf(rst.getString("cpf"));
                hospede.setRg(rst.getString("rg"));
                hospede.setObs(rst.getString("obs"));
                hospede.setRazaoSocial(rst.getString("razao_social"));
                hospede.setCnpj(rst.getString("cnpj"));
                hospede.setInscricaoEstdual(rst.getString("inscricao_estadual"));
                hospede.setContato(rst.getString("contato"));
                hospede.setStatus(rst.getString("status").charAt(0));
                listaHospedes.add(hospede);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaHospedes;
        }
    }

    @Override
    public void Update(Hospede objeto) {

        String sqlInstrucao = "Update hospede "
                + " Set"
                + " nome = ?, "
                + " fone = ?, "
                + " fone2 = ?,"
                + " email = ?, "
                + " cep = ?, "
                + " logradouro = ?,"
                + " bairro = ?, "
                + " cidade = ?, "
                + " complemento = ?, "
                + " data_cadastro = ?, "
                + " cpf = ?, "
                + " rg = ?, "
                + " obs = ?, "
                + " status = ?, "
                + " razao_social = ?, "
                + " cnpj = ?, "
                + " inscricao_estadual = ?, "
                + " contato = ? "
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        
        try{
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
        pstm.setInt(19, objeto.getId());
        
        pstm.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Hospede objeto) {
        
        
        
    }

}