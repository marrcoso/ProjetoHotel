package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.VagaEstacionamento;

public class VagaEstacionamentoDAO implements InterfaceDAO<VagaEstacionamento> {
    @Override
    public void Create(VagaEstacionamento objeto) {

        String sqlInstrucao = "Insert Into vaga_estacionamento("
                + " descricao, "
                + " obs, "
                + " metragemvaga, "
                + " status) "
                + " Values (?,?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getObs()));
            pstm.setString(3, String.valueOf(objeto.getMetragemVaga()));
            pstm.setString(4, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public VagaEstacionamento Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " obs, "
                + " metragemvaga, "
                + " status "
                + " From vaga_estacionamento"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        VagaEstacionamento vagaEstacionamento = new VagaEstacionamento();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                vagaEstacionamento.setId(rst.getInt("id"));
                vagaEstacionamento.setDescricao(rst.getString("descricao"));
                vagaEstacionamento.setObs(rst.getString("obs"));
                vagaEstacionamento.setMetragemVaga(rst.getFloat("metragemvaga"));
                vagaEstacionamento.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return vagaEstacionamento;
        }
    }

    @Override
    public List<VagaEstacionamento> Retrieve(String atributo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(VagaEstacionamento objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(VagaEstacionamento objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
