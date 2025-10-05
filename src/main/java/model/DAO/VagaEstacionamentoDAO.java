package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.VagaEstacionamento;
import util.AppLogger;

public class VagaEstacionamentoDAO implements InterfaceDAO<VagaEstacionamento> {
    @Override
    public void Create(VagaEstacionamento objeto) throws SQLException {
        String sqlInstrucao = "Insert Into vaga_estacionamento("
                + " descricao, "
                + " obs, "
                + " metragemvaga, "
                + " status) "
                + " Values (?,?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setFloat(3, objeto.getMetragemVaga());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar vaga de estacionamento", ex);
            throw new SQLException("Erro ao criar vaga de estacionamento");
        }
    }

    @Override
    public VagaEstacionamento Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " obs, "
                + " metragemvaga, "
                + " status "
                + " From vaga_estacionamento"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            VagaEstacionamento vagaEstacionamento = null;

            while (rst.next()) {
                vagaEstacionamento = new VagaEstacionamento();
                vagaEstacionamento.setId(rst.getInt("id"));
                vagaEstacionamento.setDescricao(rst.getString("descricao"));
                vagaEstacionamento.setObs(rst.getString("obs"));
                vagaEstacionamento.setMetragemVaga(rst.getFloat("metragemvaga"));
                vagaEstacionamento.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return vagaEstacionamento;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar vaga de estacionamento", ex);
            throw new SQLException("Erro ao carregar vaga de estacionamento");
        }
    }

    @Override
    public List<VagaEstacionamento> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " obs, "
                + " metragemvaga, "
                + " status "
                + " From vaga_estacionamento"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<VagaEstacionamento> listaVagas = new java.util.ArrayList<>();

            while (rst.next()) {
                VagaEstacionamento vaga = new VagaEstacionamento();
                vaga.setId(rst.getInt("id"));
                vaga.setDescricao(rst.getString("descricao"));
                vaga.setObs(rst.getString("obs"));
                vaga.setMetragemVaga(rst.getFloat("metragemvaga"));
                vaga.setStatus(rst.getString("status").charAt(0));
                listaVagas.add(vaga);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaVagas;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar vagas de estacionamento", ex);
            throw new SQLException("Erro ao buscar vagas de estacionamento");
        }
    }

    @Override
    public void Update(VagaEstacionamento objeto) throws SQLException {
        String sqlInstrucao = "Update vaga_estacionamento "
                + " Set"
                + " descricao = ?, "
                + " obs = ?, "
                + " metragemvaga = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setFloat(3, objeto.getMetragemVaga());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar vaga de estacionamento", ex);
            throw new SQLException("Erro ao atualizar vaga de estacionamento");
        }
    }

    @Override
    public void Delete(VagaEstacionamento objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão dos métodos acima
    }
}
