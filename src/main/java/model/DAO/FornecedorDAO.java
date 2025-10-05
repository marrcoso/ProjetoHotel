package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Fornecedor;
import util.AppLogger;

public class FornecedorDAO implements InterfaceDAO<Fornecedor> {
    
    @Override
    public void Create(Fornecedor objeto) throws SQLException {
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

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);

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

            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar fornecedor", ex);
            throw new SQLException("Erro ao criar fornecedor");
        }
    }

    @Override
    public Fornecedor Retrieve(int id) throws SQLException {
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

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Fornecedor fornecedor = null;

            while (rst.next()) {
                fornecedor = new Fornecedor();
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
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return fornecedor;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar fornecedor", ex);
            throw new SQLException("Erro ao carregar fornecedor");
        }
    }

    @Override
    public List<Fornecedor> Retrieve(String atributo, String valor) throws SQLException {
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

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Fornecedor> listaFornecedores = new java.util.ArrayList<>();


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
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaFornecedores;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar fornecedores", ex);
            throw new SQLException("Erro ao buscar fornecedores");
        }
    }

    @Override
    public void Update(Fornecedor objeto) throws SQLException {
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

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);

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
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar fornecedor", ex);
            throw new SQLException("Erro ao atualizar fornecedor");
        }
    }

    @Override
    public void Delete(Fornecedor objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão das demais operações
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
