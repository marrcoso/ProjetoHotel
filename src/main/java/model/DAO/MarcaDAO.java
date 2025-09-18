package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Marca;

public class MarcaDAO implements InterfaceDAO<Marca> {
    
    @Override
    public void Create(Marca objeto) {

        String sqlInstrucao = "Insert Into marca("
                + " descricao, "
                + " status) "
                + " Values (?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Marca Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id,"
                + " descricao, "
                + " status "
                + " From marca"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Marca marca = new Marca();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao")); 
                marca.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return marca;
        }
    }

    @Override
    public List<Marca> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "Select "
                + " id,"
                + " descricao, "
                + " status "
                + " From marca"
                + " Where " + atributo + " like ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Marca> listaMarcas = new java.util.ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Marca marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
                listaMarcas.add(marca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaMarcas;
        }
    }

    @Override
    public void Update(Marca objeto) {
        String sqlInstrucao = "Update marca "
                + " Set"
                + " descricao = ?, "
                + " status = ? "
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getId());

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Marca objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
