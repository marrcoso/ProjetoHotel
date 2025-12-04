package model.DAO.JPA;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.cdimascio.dotenv.Dotenv;
import utilities.AppLogger;

public final class JPAFactory {

    private static volatile EntityManagerFactory emf;

    private JPAFactory() {
    }

    @SuppressWarnings("UseSpecificCatch")
    private static void init() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.engine.jdbc.spi.SqlExceptionHelper").setLevel(Level.OFF);
        if (emf != null) {
            return;
        }
        synchronized (JPAFactory.class) {
            if (emf != null) {
                return;
            }
            try {
                Dotenv dotenv = Dotenv.configure().directory("./").load();
                Map<String, String> props = new HashMap<>();
                props.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                props.put("javax.persistence.jdbc.url", dotenv.get("DB_URL"));
                props.put("javax.persistence.jdbc.user", dotenv.get("DB_USER"));
                props.put("javax.persistence.jdbc.password", dotenv.get("DB_PASS"));
                emf = Persistence.createEntityManagerFactory("ProjetoHotelPU", props);
            } catch (Exception ex) {
                AppLogger.error("Erro ao criar EntityManagerFactory", ex);
                throw new RuntimeException("Erro ao inicializar conexão ao banco", ex);
            }
        }
    }

    /**
     * Retorna a EntityManagerFactory inicializada. Lança RuntimeException se a
     * inicialização falhar (por exemplo DB ausente).
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        init();
        return emf;
    }

    /**
     * Fecha a factory (usar no shutdown app).
     */
    public static void closeFactory() {
        if (emf != null && emf.isOpen()) {
            try {
                emf.close();
            } catch (Exception ex) {
                AppLogger.error("Erro fechando EntityManagerFactory", ex);
            } finally {
                emf = null;
            }
        }
    }

}
