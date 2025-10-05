package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Quarto;
import util.AppLogger;

public class QuartoDAO implements InterfaceDAO<Quarto> {
    @Override
    public void Create(Quarto objeto) throws SQLException {
        String sqlInstrucao = "Insert Into quarto("
                + " descricao, "
                + " capacidade_hospedes, "
                + " metragem, "
                + " identificacao, "
                + " andar, "
                + " flag_animais, "
                + " obs, "
                + " status) "
                + " Values (?,?,?,?,?,?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getCapacidadeHospedes());
            pstm.setFloat(3, objeto.getMetragem());
            pstm.setString(4, objeto.getIdentificacao());
            pstm.setInt(5, objeto.getAndar());
            pstm.setBoolean(6, objeto.isFlagAnimais());
            pstm.setString(7, objeto.getObs());
            pstm.setString(8, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar quarto", ex);
            throw new SQLException("Erro ao criar quarto");
        }
    }

    @Override
    public Quarto Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " capacidade_hospedes, "
                + " metragem, "
                + " identificacao, "
                + " andar, "
                + " flag_animais, "
                + " obs, "
                + " status "
                + " From quarto"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Quarto quarto = null;

            while (rst.next()) {
                quarto = new Quarto();
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidade_hospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flag_animais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return quarto;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar quarto", ex);
            throw new SQLException("Erro ao carregar quarto");
        }
    }

    @Override
    public List<Quarto> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " capacidade_hospedes, "
                + " metragem, "
                + " identificacao, "
                + " andar, "
                + " flag_animais, "
                + " obs, "
                + " status "
                + " From quarto"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Quarto> listaQuartos = new java.util.ArrayList<>();

            while (rst.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidade_hospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flag_animais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
                listaQuartos.add(quarto);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaQuartos;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar quartos", ex);
            throw new SQLException("Erro ao buscar quartos");
        }
    }

    @Override
    public void Update(Quarto objeto) throws SQLException {
        String sqlInstrucao = "Update quarto "
                + " Set"
                + " descricao = ?, "
                + " capacidade_hospedes = ?, "
                + " metragem = ?, "
                + " identificacao = ?, "
                + " andar = ?, "
                + " flag_animais = ?, "
                + " obs = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getCapacidadeHospedes());
            pstm.setFloat(3, objeto.getMetragem());
            pstm.setString(4, objeto.getIdentificacao());
            pstm.setInt(5, objeto.getAndar());
            pstm.setBoolean(6, objeto.isFlagAnimais());
            pstm.setString(7, objeto.getObs());
            pstm.setString(8, String.valueOf(objeto.getStatus()));
            pstm.setInt(9, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar quarto", ex);
            throw new SQLException("Erro ao atualizar quarto");
        }
    }

    @Override
    public void Delete(Quarto objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão das demais operações
        String sqlInstrucao = "Delete From quarto Where id = ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao deletar quarto", ex);
            throw new SQLException("Erro ao deletar quarto");
        }
    }
}
