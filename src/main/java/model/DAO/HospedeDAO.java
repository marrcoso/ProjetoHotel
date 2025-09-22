package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Hospede;
import util.AppLogger;

public class HospedeDAO implements InterfaceDAO<Hospede> {

    @Override
    public void Create(Hospede objeto) throws SQLException {
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
                + " sexo, "
                + " razao_social, "
                + " cnpj, "
                + " inscricao_estadual, "
                + " contato ) "
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
            AppLogger.error("Erro ao criar hóspede", ex);
            throw new SQLException("Erro ao criar hóspede");
        }
    }

    @Override
    public Hospede Retrieve(int id) throws SQLException {
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
                + " From hospede"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Hospede hospede = new Hospede();

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
                hospede.setSexo(rst.getString("sexo").charAt(0));
                hospede.setInscricaoEstadual(rst.getString("inscricao_estadual"));
                hospede.setContato(rst.getString("contato"));
                hospede.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return hospede;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar hóspede", ex);
            throw new SQLException("Erro ao carregar hóspede");
        }
    }

    @Override
    public List<Hospede> Retrieve(String atributo, String valor) throws SQLException {
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
                + " From hospede"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Hospede> listaHospedes = new ArrayList<>();

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
                hospede.setSexo(rst.getString("sexo").charAt(0));
                hospede.setRazaoSocial(rst.getString("razao_social"));
                hospede.setCnpj(rst.getString("cnpj"));
                hospede.setInscricaoEstadual(rst.getString("inscricao_estadual"));
                hospede.setContato(rst.getString("contato"));
                hospede.setStatus(rst.getString("status").charAt(0));
                listaHospedes.add(hospede);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaHospedes;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar hóspedes", ex);
            throw new SQLException("Erro ao buscar hóspedes");
        }
    }

    @Override
    public void Update(Hospede objeto) throws SQLException {
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
            AppLogger.error("Erro ao atualizar hóspede", ex);
            throw new SQLException("Erro ao atualizar hóspede");
        }
    }

    @Override
    public void Delete(Hospede objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão.
    }
}