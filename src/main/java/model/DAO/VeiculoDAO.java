package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Veiculo;

public class VeiculoDAO implements InterfaceDAO<Veiculo> {

    @Override
    public void Create(Veiculo objeto) {

        String sqlInstrucao = "Insert Into veiculo("
                + " placa, "
                + " modelo_id, "
                + " cor, "
                + " status) "
                + " Values (?,?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setString(2, String.valueOf(objeto.getModelo().getId()));
            pstm.setString(3, objeto.getCor());
            pstm.setString(4, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Veiculo Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id, "
                + " placa, "
                + " modelo_id, "
                + " cor, "
                + " status "
                + " From veiculo"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Veiculo veiculo = new Veiculo();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return veiculo;
        }
    }

    @Override
    public List<Veiculo> Retrieve(String atributo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(Veiculo objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Veiculo objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
