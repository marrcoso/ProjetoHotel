package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Modelo;

public class ModeloDAO implements InterfaceDAO<Modelo> {

    @Override
    public void Create(Modelo objeto) {

        String sqlInstrucao = "Insert Into modelo("
                + " descricao, "
                + " marca_id, "
                + " status) "
                + " Values (?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getMarca().getId()));
            pstm.setString(3, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Modelo Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " marca_id, "
                + " status "
                + " From modelo"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Modelo modelo = new Modelo();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                modelo.setId(rst.getInt("id"));
                modelo.setDescricao(rst.getString("descricao")); 
                modelo.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return modelo;
        }
    }

    @Override
    public List<Modelo> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " marca_id, "
                + " status "
                + " From modelo"
                + " Where " + atributo + " like ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Modelo> listaModelos = new java.util.ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("id"));
                modelo.setDescricao(rst.getString("descricao"));
                // Aqui você pode buscar a marca pelo id se necessário
                modelo.setStatus(rst.getString("status").charAt(0));
                listaModelos.add(modelo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaModelos;
        }
    }

    @Override
    public void Update(Modelo objeto) {
        String sqlInstrucao = "Update modelo "
                + " Set"
                + " descricao = ?, "
                + " marca_id = ?, "
                + " status = ? "
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getMarca().getId());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getId());

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Modelo objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
