package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Veiculo;
import util.AppLogger;

public class VeiculoDAO implements InterfaceDAO<Veiculo> {

    @Override
    public void Create(Veiculo objeto) throws SQLException {
        String sqlInstrucao = "Insert Into veiculo("
                + " placa, "
                + " modelo_id, "
                + " cor, "
                + " status) "
                + " Values (?,?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setInt(2, objeto.getModelo().getId());
            pstm.setString(3, objeto.getCor());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao criar veículo", ex);
            throw new SQLException("Erro ao criar veículo");
        }
    }

    @Override
    public Veiculo Retrieve(int id) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " placa, "
                + " modelo_id, "
                + " cor, "
                + " status "
                + " From veiculo"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Veiculo veiculo = null;
            
            while (rst.next()) {
                if (veiculo == null) {
                    veiculo = new Veiculo();
                }
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setModelo(new ModeloDAO().Retrieve(rst.getInt("modelo_id")));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return veiculo;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar veículo", ex);
            throw new SQLException("Erro ao carregar veículo");
        }
    }

    @Override
    public List<Veiculo> Retrieve(String atributo, String valor) throws SQLException {
        String sqlInstrucao = "Select "
                + " id, "
                + " placa, "
                + " modelo_id, "
                + " cor, "
                + " status "
                + " From veiculo"
                + " Where " + atributo + " like ?";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            ResultSet rst = pstm.executeQuery();
            List<Veiculo> listaVeiculos = new java.util.ArrayList<>();

            while (rst.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                // Aqui você pode buscar o modelo pelo id se necessário
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));
                listaVeiculos.add(veiculo);
            }
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaVeiculos;
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar veículos", ex);
            throw new SQLException("Erro ao buscar veículos");
        }
    }

    @Override
    public void Update(Veiculo objeto) throws SQLException {
        String sqlInstrucao = "Update veiculo "
                + " Set"
                + " placa = ?, "
                + " modelo_id = ?, "
                + " cor = ?, "
                + " status = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setInt(2, objeto.getModelo().getId());
            pstm.setString(3, objeto.getCor());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());
            pstm.execute();
            ConnectionFactory.closeConnection(conexao, pstm);
        } catch (SQLException ex) {
            AppLogger.error("Erro ao atualizar veículo", ex);
            throw new SQLException("Erro ao atualizar veículo");
        }
    }

    @Override
    public void Delete(Veiculo objeto) throws SQLException {
        // Implemente aqui se necessário, seguindo o padrão acima
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
