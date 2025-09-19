package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Fornecedor;

public class FornecedorDAO implements InterfaceDAO<Fornecedor> {
    
    @Override
    public void Create(Fornecedor objeto) {

        String sqlInstrucao = "Insert Into fornecedor("
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
                + " sexo, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato) "
                + " Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
            pstm.setString(15, String.valueOf(objeto.getSexo()));
            pstm.setString(16, objeto.getRazaoSocial());
            pstm.setString(17, objeto.getCnpj());
            pstm.setString(18, objeto.getInscricaoEstadual());
            pstm.setString(19, objeto.getContato());

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Fornecedor Retrieve(int id) {

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
                + " sexo, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato "
                + " From fornecedor"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Fornecedor fornecedor = new Fornecedor();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (rst.next()) {
                fornecedor.setId(rst.getInt("id"));
                fornecedor.setNome(rst.getString(2));
                fornecedor.setFone1(rst.getString("fone"));     
                fornecedor.setFone2(rst.getString("fone2"));     
                fornecedor.setEmail(rst.getString("email"));     
                fornecedor.setCep(rst.getString("cep"));     
                fornecedor.setLogradouro(rst.getString("logradouro"));     
                fornecedor.setBairro(rst.getString("bairro"));     
                fornecedor.setCidade(rst.getString("cidade"));     
                fornecedor.setComplemento(rst.getString("complemento"));     
                fornecedor.setDataCadastro(rst.getString("data_cadastro"));     
                fornecedor.setCpf(rst.getString("cpf"));     
                fornecedor.setRg(rst.getString("rg"));     
                fornecedor.setObs(rst.getString("obs"));     
                fornecedor.setRazaoSocial(rst.getString("razao_social"));     
                fornecedor.setCnpj(rst.getString("cnpj"));    
                fornecedor.setInscricaoEstadual(rst.getString("inscricao_estadual"));    
                fornecedor.setContato(rst.getString("contato"));    
                fornecedor.setStatus(rst.getString("status").charAt(0));
                fornecedor.setSexo(rst.getString("sexo").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return fornecedor;
        }
    }

    @Override
    public List<Fornecedor> Retrieve(String atributo, String valor) {
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
                + " sexo, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato "
                + " From fornecedor"
                + " Where " + atributo + " like ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Fornecedor> listaFornecedores = new java.util.ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rst.getInt("id"));
                fornecedor.setNome(rst.getString(2));
                fornecedor.setFone1(rst.getString("fone"));
                fornecedor.setFone2(rst.getString("fone2"));
                fornecedor.setEmail(rst.getString("email"));
                fornecedor.setCep(rst.getString("cep"));
                fornecedor.setLogradouro(rst.getString("logradouro"));
                fornecedor.setBairro(rst.getString("bairro"));
                fornecedor.setCidade(rst.getString("cidade"));
                fornecedor.setComplemento(rst.getString("complemento"));
                fornecedor.setDataCadastro(rst.getString("data_cadastro"));
                fornecedor.setCpf(rst.getString("cpf"));
                fornecedor.setRg(rst.getString("rg"));
                fornecedor.setObs(rst.getString("obs"));
                fornecedor.setRazaoSocial(rst.getString("razao_social"));
                fornecedor.setCnpj(rst.getString("cnpj"));
                fornecedor.setInscricaoEstadual(rst.getString("inscricao_estadual"));
                fornecedor.setContato(rst.getString("contato"));
                fornecedor.setStatus(rst.getString("status").charAt(0));
                fornecedor.setSexo(rst.getString("sexo").charAt(0));
                listaFornecedores.add(fornecedor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaFornecedores;
        }
    }

    @Override
    public void Update(Fornecedor objeto) {
        String sqlInstrucao = "Update fornecedor "
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
                + " sexo = ?, "
                + " razao_social = ?, "
                + " cnpj = ?, "
                + " inscricao_estadual = ?, "
                + " contato = ? "
                + " Where id = ? ";

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
            pstm.setString(15, String.valueOf(objeto.getSexo()));
            pstm.setString(16, objeto.getRazaoSocial());
            pstm.setString(17, objeto.getCnpj());
            pstm.setString(18, objeto.getInscricaoEstadual());
            pstm.setString(19, objeto.getContato());
            pstm.setInt(20, objeto.getId());

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Fornecedor objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
