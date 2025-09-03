package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Servico;

public class ServicoDAO implements InterfaceDAO<Servico> {
    @Override
    public void Create(Servico objeto) {

        String sqlInstrucao = "Insert Into veiculo("
                + " descricao, "
                + " obs, "
                + " status) "
                + " Values (?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getObs()));
            pstm.setString(3, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Servico Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " obs, "
                + " status "
                + " From veiculo"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Servico servico = new Servico();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                servico.setId(rst.getInt("id"));
                servico.setDescricao(rst.getString("descricao"));
                servico.setObs(rst.getString("obs"));
                servico.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return servico;
        }
    }

    @Override
    public List<Servico> Retrieve(String atributo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(Servico objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Servico objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
