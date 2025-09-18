package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Quarto;

public class QuartoDAO implements InterfaceDAO<Quarto> {
    @Override
    public void Create(Quarto objeto) {

        String sqlInstrucao = "Insert Into quarto("
                + " descricao, "
                + " capacidadehospedes, "
                + " metragem, "
                + " identificacao, "
                + " andar, "
                + " flaganimais, "
                + " obs, "
                + " status) "
                + " Values (?,?,?,?,?,?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getCapacidadeHospedes()));
            pstm.setString(3, String.valueOf(objeto.getMetragem()));
            pstm.setString(4, objeto.getIdentificacao());
            pstm.setString(5, String.valueOf(objeto.getAndar()));
            pstm.setString(6, String.valueOf(objeto.isFlagAnimais()));
            pstm.setString(7, objeto.getObs());
            pstm.setString(8, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Quarto Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " capacidadehospedes, "
                + " metragem, "
                + " identificacao, "
                + " andar, "
                + " flaganimais, "
                + " obs, "
                + " status "
                + " From quarto"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Quarto quarto = new Quarto();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidadehospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flaganimais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return quarto;
        }
    }

    @Override
    public List<Quarto> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " capacidadehospedes, "
                + " metragem, "
                + " identificacao, "
                + " andar, "
                + " flaganimais, "
                + " obs, "
                + " status "
                + " From quarto"
                + " Where " + atributo + " like ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Quarto> listaQuartos = new java.util.ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidadehospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flaganimais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
                listaQuartos.add(quarto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaQuartos;
        }
    }

    @Override
    public void Update(Quarto objeto) {
        String sqlInstrucao = "Update quarto "
                + " Set"
                + " descricao = ?, "
                + " capacidadehospedes = ?, "
                + " metragem = ?, "
                + " identificacao = ?, "
                + " andar = ?, "
                + " flaganimais = ?, "
                + " obs = ?, "
                + " status = ? "
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Quarto objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
