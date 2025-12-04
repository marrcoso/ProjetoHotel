package model.DAO.JPA;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;

import utilities.AppLogger;

public class JPADao {

    private static javax.persistence.EntityManagerFactory getFactory() {
        return JPAFactory.getEntityManagerFactory();
    }

    public static <R> R executeWithEntityManager(Function<EntityManager, R> work, boolean withTransaction) throws RuntimeException {
        EntityManager em = null;
        try {
            em = getFactory().createEntityManager();
            if (withTransaction) em.getTransaction().begin();
            R result = work.apply(em);
            if (withTransaction) em.getTransaction().commit();
            return result;
        } catch (RuntimeException ex) {
            if (em != null && withTransaction && em.getTransaction().isActive()) {
                try { em.getTransaction().rollback(); } catch (Exception e) { AppLogger.error("Rollback falhou", e); }
            }
            AppLogger.error("Erro na operação JPA", ex);
            throw ex;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    public static void executeVoid(Consumer<EntityManager> work, boolean withTransaction) {
        executeWithEntityManager(em -> {
            work.accept(em);
            return null;
        }, withTransaction);
    }

    public static void validAttribute(Class<?> entityClass, String attr) throws RuntimeException {
        try {
            if (attr == null || attr.trim().isEmpty() || entityClass.getDeclaredField(attr) == null) {
                throw new RuntimeException("Falha ao validar atributo por reflexão");
            }
        } catch (NoSuchFieldException | RuntimeException ex) {
            AppLogger.error("Falha ao validar atributo por reflexão", ex);
            throw new RuntimeException("Falha ao validar atributo por reflexão", ex);
        }
    }
}