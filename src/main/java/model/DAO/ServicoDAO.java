package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Servico;
import util.AppLogger;

public class ServicoDAO implements InterfaceDAO<Servico> {
    @Override
    public void Create(Servico objeto) throws SQLException {
        String sqlInstrucao = "Insert Into servico("
                + " descricao, "
                + " obs, "
                + " status) "
                + " Values (?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getObs()));
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar serviço", ex);
            throw new SQLException("Erro ao criar serviço");
        }
    }

    @Override
    public Servico Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " obs, "
                + " status "
                + " From servico"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Servico servico = null;

            while (rst.next()) {
                servico = new Servico();
                servico.setId(rst.getInt("id"));
                servico.setDescricao(rst.getString("descricao"));
                servico.setObs(rst.getString("obs"));
                servico.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return servico;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar serviço", ex);
            throw new SQLException("Erro ao carregar serviço");
        }
    }

    @Override
    public List<Servico> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " obs, "
                + " status "
                + " From servico"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Servico> listaServicos = new java.util.ArrayList<>();

            while (rst.next()) {
                Servico servico = new Servico();
                servico.setId(rst.getInt("id"));
                servico.setDescricao(rst.getString("descricao"));
                servico.setObs(rst.getString("obs"));
                servico.setStatus(rst.getString("status").charAt(0));
                listaServicos.add(servico);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaServicos;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar serviços", ex);
            throw new SQLException("Erro ao buscar serviços");
        }
    }

    @Override
    public void Update(Servico objeto) throws SQLException {
        String sqlInstrucao = "Update servico "
                + " Set "
                + " descricao = ?, "
                + " obs = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar serviço", ex);
            throw new SQLException("Erro ao atualizar serviço");
        }
    }

    @Override
    public void Delete(Servico objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão dos métodos acima
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
