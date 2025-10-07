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
                + " status, "
                + " funcionario_id, "
                + " fornecedor_id, "
                + " hospede_id) "
                + " Values (?,?,?,?,?,?,?)";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setInt(2, objeto.getModelo().getId());
            pstm.setString(3, objeto.getCor());
            pstm.setString(4, String.valueOf(objeto.getStatus()));

            switch (objeto.getProprietario().getClass().getSimpleName()) {
                case "Funcionario":
                    pstm.setInt(5, objeto.getProprietario().getId());
                    pstm.setNull(6, java.sql.Types.INTEGER);
                    pstm.setNull(7, java.sql.Types.INTEGER);
                    break;
                case "Fornecedor":
                    pstm.setNull(5, java.sql.Types.INTEGER);
                    pstm.setInt(6, objeto.getProprietario().getId());
                    pstm.setNull(7, java.sql.Types.INTEGER);
                    break;
                case "Hospede":
                    pstm.setNull(5, java.sql.Types.INTEGER);
                    pstm.setNull(6, java.sql.Types.INTEGER);
                    pstm.setInt(7, objeto.getProprietario().getId());
                    break;
                default:
                    throw new SQLException("Tipo de proprietário desconhecido: " + objeto.getProprietario().getClass().getSimpleName());
            }

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
                + " status, "
                + " funcionario_id, "
                + " fornecedor_id, "
                + " hospede_id "
                + " From veiculo"
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Veiculo veiculo = null;

            while (rst.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setModelo(new ModeloDAO().Retrieve(rst.getInt("modelo_id")));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                int funcionarioId = rst.getInt("funcionario_id");
                int fornecedorId = rst.getInt("fornecedor_id");
                int hospedeId = rst.getInt("hospede_id");

                if (funcionarioId > 0) {
                    veiculo.setProprietario(new service.FuncionarioService().Carregar(funcionarioId));
                } else if (fornecedorId > 0) {
                    veiculo.setProprietario(new service.FornecedorService().Carregar(fornecedorId));
                } else if (hospedeId > 0) {
                    veiculo.setProprietario(new service.HospedeService().Carregar(hospedeId));
                } else {
                    throw new SQLException("Veículo sem proprietário associado.");
                }
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
                + " status, "
                + " funcionario_id, "
                + " fornecedor_id, "
                + " hospede_id "
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
                veiculo.setModelo(new ModeloDAO().Retrieve(rst.getInt("modelo_id")));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                int funcionarioId = rst.getInt("funcionario_id");
                int fornecedorId = rst.getInt("fornecedor_id");
                int hospedeId = rst.getInt("hospede_id");

                if (funcionarioId > 0) {
                    veiculo.setProprietario(new service.FuncionarioService().Carregar(funcionarioId));
                } else if (fornecedorId > 0) {
                    veiculo.setProprietario(new service.FornecedorService().Carregar(fornecedorId));
                } else if (hospedeId > 0) {
                    veiculo.setProprietario(new service.HospedeService().Carregar(hospedeId));
                } else {
                    throw new SQLException("Veículo de id " + veiculo.getId() + " sem proprietário associado.");
                }

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
                + " status = ?, "
                + " funcionario_id = ?, "
                + " fornecedor_id = ?, "
                + " hospede_id = ? "
                + " Where id = ? ";

        try {
            Connection conexao = model.DAO.ConnectionFactory.getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setInt(2, objeto.getModelo().getId());
            pstm.setString(3, objeto.getCor());
            pstm.setString(4, String.valueOf(objeto.getStatus()));

            switch (objeto.getProprietario().getClass().getSimpleName()) {
                case "Funcionario":
                    pstm.setInt(5, objeto.getProprietario().getId());
                    pstm.setNull(6, java.sql.Types.INTEGER);
                    pstm.setNull(7, java.sql.Types.INTEGER);
                    break;
                case "Fornecedor":
                    pstm.setNull(5, java.sql.Types.INTEGER);
                    pstm.setInt(6, objeto.getProprietario().getId());
                    pstm.setNull(7, java.sql.Types.INTEGER);
                    break;
                case "Hospede":
                    pstm.setNull(5, java.sql.Types.INTEGER);
                    pstm.setNull(6, java.sql.Types.INTEGER);
                    pstm.setInt(7, objeto.getProprietario().getId());
                    break;
                default:
                    throw new SQLException("Tipo de proprietário desconhecido: " + objeto.getProprietario().getClass().getSimpleName());
            }

            pstm.setInt(8, objeto.getId());
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

    @Override
    public void AtivarInativar(int id, boolean ativar) throws SQLException {
        String sqlInstrucao = "UPDATE veiculo SET status = ? WHERE id = ?";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement pstm = conexao.prepareStatement(sqlInstrucao)) {
            pstm.setString(1, ativar ? "A" : "I");
            pstm.setInt(2, id);
            pstm.execute();
        } catch (SQLException ex) {
            AppLogger.error("Erro ao ativar/inativar veículo", ex);
            throw new SQLException("Erro ao ativar/inativar veículo");
        }
    }
}
