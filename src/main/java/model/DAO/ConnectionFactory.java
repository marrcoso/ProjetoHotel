package model.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    private static final String driver;
    private static final String banco;
    private static final String usuario;
    private static final String senha;

    static {
        Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .load();
        driver = dotenv.get("DB_DRIVER");
        banco = dotenv.get("DB_URL");
        usuario = dotenv.get("DB_USER");
        senha = dotenv.get("DB_PASS");
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(banco + "?verifyServerCertificate=false"
                    + "&useSSL=false"
                    + "&requireSSL=false"
                    + "&USER=" + usuario + "&password=" + senha + "&serverTimezone=UTC");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection conexao) {
        try {
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeConnection(Connection conexao, PreparedStatement pstm) {
        try {
            pstm.close();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeConnection(Connection conexao, PreparedStatement pstm, ResultSet rst) {
        try {
            pstm.close();
            rst.close();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
