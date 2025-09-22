package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Modelo;
import util.AppLogger;

public class ModeloDAO implements InterfaceDAO<Modelo> {

    @Override
    public void Create(Modelo objeto) throws SQLException {
        String sqlInstrucao = "Insert Into modelo("
                + " descricao, "
                + " marca_id, "
                + " status) "
                + " Values (?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getMarca().getId());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar modelo", ex);
            throw new SQLException("Erro ao criar modelo");
        }
    }

    @Override
    public Modelo Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " marca_id, "
                + " status "
                + " From modelo"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Modelo modelo = new Modelo();

            while (rst.next()) {
                modelo.setId(rst.getInt("id"));
                modelo.setDescricao(rst.getString("descricao")); 
                // Aqui você pode buscar a marca pelo id se necessário
                modelo.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return modelo;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar modelo", ex);
            throw new SQLException("Erro ao carregar modelo");
        }
    }

    @Override
    public List<Modelo> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " marca_id, "
                + " status "
                + " From modelo"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Modelo> listaModelos = new java.util.ArrayList<>();

            while (rst.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("id"));
                modelo.setDescricao(rst.getString("descricao"));
                modelo.setStatus(rst.getString("status").charAt(0));
                listaModelos.add(modelo);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaModelos;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar modelos", ex);
            throw new SQLException("Erro ao buscar modelos");
        }
    }

    @Override
    public void Update(Modelo objeto) throws SQLException {
        String sqlInstrucao = "Update modelo "
                + " Set"
                + " descricao = ?, "
                + " marca_id = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getMarca().getId());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar modelo", ex);
            throw new SQLException("Erro ao atualizar modelo");
        }
    }

    @Override
    public void Delete(Modelo objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão.
    }
}
