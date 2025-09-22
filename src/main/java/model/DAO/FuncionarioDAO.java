package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Funcionario;
import util.AppLogger;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {
    
    @Override
    public void Create(Funcionario objeto) throws SQLException {
        String sqlInstrucao = "Insert Into funcionario("
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
                + " usuario, "
                + " senha) "
                + " Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
            pstm.setString(16, objeto.getUsuario());
            pstm.setString(17, objeto.getSenha());

            pstm.execute();

            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar funcionário", ex);
            throw new SQLException("Erro ao criar funcionário");
        }
    }

    @Override
    public Funcionario Retrieve(int id) throws SQLException {
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
                + " usuario, "
                + " senha "
                + " From funcionario"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Funcionario funcionario = new Funcionario();


            while (rst.next()) {
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString(2));
                funcionario.setFone1(rst.getString("fone"));     
                funcionario.setFone2(rst.getString("fone2"));     
                funcionario.setEmail(rst.getString("email"));     
                funcionario.setCep(rst.getString("cep"));     
                funcionario.setLogradouro(rst.getString("logradouro"));     
                funcionario.setBairro(rst.getString("bairro"));     
                funcionario.setCidade(rst.getString("cidade"));     
                funcionario.setComplemento(rst.getString("complemento"));     
                funcionario.setDataCadastro(rst.getString("data_cadastro"));     
                funcionario.setCpf(rst.getString("cpf"));     
                funcionario.setRg(rst.getString("rg"));     
                funcionario.setObs(rst.getString("obs"));     
                funcionario.setUsuario(rst.getString("usuario"));     
                funcionario.setSenha(rst.getString("senha"));    
                funcionario.setStatus(rst.getString("status").charAt(0));
                funcionario.setSexo(rst.getString("sexo").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return funcionario;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar funcionário", ex);
            throw new SQLException("Erro ao carregar funcionário");
        }
    }

    @Override
    public List<Funcionario> Retrieve(String atributo, String valor) throws SQLException {
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
                + " usuario, "
                + " senha "
                + " From funcionario"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Funcionario> listaFuncionarios = new java.util.ArrayList<>();
            

            while (rst.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString(2));
                funcionario.setFone1(rst.getString("fone"));
                funcionario.setFone2(rst.getString("fone2"));
                funcionario.setEmail(rst.getString("email"));
                funcionario.setCep(rst.getString("cep"));
                funcionario.setLogradouro(rst.getString("logradouro"));
                funcionario.setBairro(rst.getString("bairro"));
                funcionario.setCidade(rst.getString("cidade"));
                funcionario.setComplemento(rst.getString("complemento"));
                funcionario.setDataCadastro(rst.getString("data_cadastro"));
                funcionario.setCpf(rst.getString("cpf"));
                funcionario.setRg(rst.getString("rg"));
                funcionario.setObs(rst.getString("obs"));
                funcionario.setUsuario(rst.getString("usuario"));
                funcionario.setSenha(rst.getString("senha"));
                funcionario.setStatus(rst.getString("status").charAt(0));
                funcionario.setSexo(rst.getString("sexo").charAt(0));
                listaFuncionarios.add(funcionario);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaFuncionarios;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar funcionários", ex);
            throw new SQLException("Erro ao buscar funcionários");
        }
    }

    @Override
    public void Update(Funcionario objeto) throws SQLException {
        String sqlInstrucao = "Update funcionario "
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
                + " usuario = ?, "
                + " senha = ? "
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
            pstm.setString(16, objeto.getUsuario());
            pstm.setString(17, objeto.getSenha());
            pstm.setInt(18, objeto.getId());

            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar funcionário", ex);
            throw new SQLException("Erro ao atualizar funcionário");
        }
    }

    @Override
    public void Delete(Funcionario objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão.
    }
}
