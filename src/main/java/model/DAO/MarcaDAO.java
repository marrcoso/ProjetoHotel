package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Marca;
import util.AppLogger;

public class MarcaDAO implements InterfaceDAO<Marca> {
    
    @Override
    public void Create(Marca objeto) throws SQLException {
        String sqlInstrucao = "Insert Into marca("
                + " descricao, "
                + " status) "
                + " Values (?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar marca", ex);
            throw new SQLException("Erro ao criar marca");
        }
    }

    @Override
    public Marca Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id,"
                + " descricao, "
                + " status "
                + " From marca"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Marca marca = null;

            while (rst.next()) {
                marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao")); 
                marca.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return marca;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar marca", ex);
            throw new SQLException("Erro ao carregar marca");
        }
    }

    @Override
    public List<Marca> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id,"
                + " descricao, "
                + " status "
                + " From marca"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Marca> listaMarcas = new java.util.ArrayList<>();

            while (rst.next()) {
                Marca marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
                listaMarcas.add(marca);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaMarcas;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar marcas", ex);
            throw new SQLException("Erro ao buscar marcas");
        }
    }

    @Override
    public void Update(Marca objeto) throws SQLException {
        String sqlInstrucao = "Update marca "
                + " Set"
                + " descricao = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar marca", ex);
            throw new SQLException("Erro ao atualizar marca");
        }
    }

    @Override
    public void Delete(Marca objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão.
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws SQLException {
        String sqlInstrucao = "UPDATE marca SET status = ? WHERE id = ?";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao)) {
            pstm.setString(1, ativar ? "A" : "I");
            pstm.setInt(2, id);
            pstm.execute();
        } catch (SQLException ex) {
            AppLogger.error("Erro ao ativar/inativar marca", ex);
            throw new SQLException("Erro ao ativar/inativar marca");
        }
    }
}
