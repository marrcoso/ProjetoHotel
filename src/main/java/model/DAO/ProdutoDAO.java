package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Produto;
import util.AppLogger;

public class ProdutoDAO implements InterfaceDAO<Produto> {
    @Override
    public void Create(Produto objeto) throws SQLException {
        String sqlInstrucao = "Insert Into produto_copa("
                + " descricao, "
                + " valor, "
                + " obs, "
                + " status "
                + " ) Values (?,?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getValor()));
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar produto", ex);
            throw new SQLException("Erro ao criar produto");
        }
    }

    @Override
    public Produto Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " valor, "
                + " obs, "
                + " status "
                + " From produto_copa"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Produto produto = null;

            while (rst.next()) {
                produto = new Produto();
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("descricao"));
                produto.setValor(rst.getFloat("valor"));
                produto.setObs(rst.getString("obs"));
                produto.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return produto;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar produto", ex);
            throw new SQLException("Erro ao carregar produto");
        }
    }

    @Override
    public List<Produto> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " descricao, "
                + " valor, "
                + " obs, "
                + " status "
                + " From produto_copa"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Produto> listaProdutos = new java.util.ArrayList<>();

            while (rst.next()) {
                Produto produto = new Produto();
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("descricao"));
                produto.setValor(rst.getFloat("valor"));
                produto.setObs(rst.getString("obs"));
                produto.setStatus(rst.getString("status").charAt(0));
                listaProdutos.add(produto);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaProdutos;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar produtos", ex);
            throw new SQLException("Erro ao buscar produtos");
        }
    }

    @Override
    public void Update(Produto objeto) throws SQLException {
        String sqlInstrucao = "Update produto_copa "
                + " Set"
                + " descricao = ?, "
                + " valor = ?, "
                + " obs = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setFloat(2, objeto.getValor());
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar produto", ex);
            throw new SQLException("Erro ao atualizar produto");
        }
    }

    @Override
    public void Delete(Produto objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o mesmo padrão.
    }
}
