package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Produto;

public class ProdutoDAO implements InterfaceDAO<Produto> {
    @Override
    public void Create(Produto objeto) {

        String sqlInstrucao = "Insert Into produto("
                + " descricao, "
                + " valor, "
                + " obs, "
                + " status) "
                + " Values (?,?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getValor()));
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Produto Retrieve(int id) {

        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " valor, "
                + " obs, "
                + " status "
                + " From produto"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Produto produto = new Produto();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (!rst.next()) {
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("descricao"));
                produto.setValor(rst.getFloat("valor"));
                produto.setObs(rst.getString("obs"));
                produto.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return produto;
        }
    }

    @Override
    public List<Produto> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " valor, "
                + " obs, "
                + " status "
                + " From produto"
                + " Where " + atributo + " like ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Produto> listaProdutos = new java.util.ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Produto produto = new Produto();
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("descricao"));
                produto.setValor(rst.getFloat("valor"));
                produto.setObs(rst.getString("obs"));
                produto.setStatus(rst.getString("status").charAt(0));
                listaProdutos.add(produto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaProdutos;
        }
    }

    @Override
    public void Update(Produto objeto) {
        String sqlInstrucao = "Update produto "
                + " Set"
                + " descricao = ?, "
                + " valor = ?, "
                + " obs = ?, "
                + " status = ? "
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setFloat(2, objeto.getValor());
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Produto objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
