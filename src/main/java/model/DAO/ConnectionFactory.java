package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    private static final String BANCO;
    private static final String USUARIO;
    private static final String SENHA;

    static {
        Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .load();
        BANCO = dotenv.get("DB_URL");
        USUARIO = dotenv.get("DB_USER");
        SENHA = dotenv.get("DB_PASS");
    }

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(BANCO + "?verifyServerCertificate=false"
            + "&useSSL=false"
            + "&requireSSL=false"
            + "&USER=" + USUARIO + "&password=" + SENHA + "&serverTimezone=UTC");
    }

    public static void closeConnection(Connection conexao) throws SQLException {
        conexao.close();
    }

    public static void closeConnection(Connection conexao, PreparedStatement pstm) throws SQLException {
        pstm.close();
        conexao.close();
    }

    public static void closeConnection(Connection conexao, PreparedStatement pstm, ResultSet rst) throws SQLException {
        pstm.close();
        rst.close();
        conexao.close();
    }
}
