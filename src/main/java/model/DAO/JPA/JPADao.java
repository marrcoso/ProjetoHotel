package model.DAO.JPA;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import utilities.AppLogger;

public class JPADao {

    private static javax.persistence.EntityManagerFactory getFactory() {
        return JPAFactory.getEntityManagerFactory();
    }

    public static <R> R executeWithEntityManager(Function<EntityManager, R> work, boolean withTransaction)
            throws RuntimeException {
        EntityManager em = null;
        try {
            em = getFactory().createEntityManager();
            if (withTransaction)
                em.getTransaction().begin();
            R result = work.apply(em);
            if (withTransaction)
                em.getTransaction().commit();
            return result;
        } catch (RuntimeException ex) {
            if (em != null && withTransaction && em.getTransaction().isActive()) {
                try {
                    em.getTransaction().rollback();
                } catch (Exception e) {
                    AppLogger.error("Rollback falhou", e);
                }
            }
            AppLogger.error("Erro na operação JPA", ex);
            throw ex;
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    public static void executeVoid(Consumer<EntityManager> work, boolean withTransaction) {
        executeWithEntityManager(em -> {
            work.accept(em);
            return null;
        }, withTransaction);
    }

    public static void validAttribute(Class<?> clazz, String attr) throws RuntimeException {
        if (attr == null || attr.trim().isEmpty()) {
            throw new RuntimeException("Atributo vazio");
        }
        if (attr.endsWith(".id")) {
            attr = attr.substring(0, attr.length() - 3);
        }
        try {
            Class<?> current = clazz;
            boolean found = false;
            while (current != null && !found) {
                try {
                    current.getDeclaredField(attr);
                    found = true;
                } catch (NoSuchFieldException e) {
                    current = current.getSuperclass();
                }
            }
            if (!found) {
                throw new RuntimeException("Atributo não encontrado: " + attr + " em " + clazz.getSimpleName());
            }
        } catch (RuntimeException ex) {
            AppLogger.error("Falha ao validar atributo por reflexão", ex);
            throw new RuntimeException("Falha ao validar atributo por reflexão", ex);
        }
    }

    public static <T> void create(T entity) throws RuntimeException {
        executeVoid(em -> em.persist(entity), true);
    }

    public static <T> T find(Class<T> clazz, int id) throws RuntimeException {
        return executeWithEntityManager(em -> em.find(clazz, id), false);
    }

    public static <T> void update(T entity) throws RuntimeException {
        executeVoid(em -> em.merge(entity), true);
    }

    public static <T> void delete(Class<T> clazz, int id) throws RuntimeException {
        executeVoid(em -> {
            T managed = em.find(clazz, id);
            if (managed != null)
                em.remove(managed);
        }, true);
    }

    public static <T> List<T> findByAttribute(Class<T> clazz, String entityName, String atributo, String valor)
            throws RuntimeException {
        validAttribute(clazz, atributo);
        return executeWithEntityManager(em -> {
            String jpql;
            TypedQuery<T> q;
            if (atributo.endsWith(".id")) {
                jpql = "SELECT e FROM " + entityName + " e WHERE e." + atributo + " = :valor";
                q = em.createQuery(jpql, clazz);
                q.setParameter("valor", Integer.valueOf(valor));
            } else {
                jpql = "SELECT e FROM " + entityName + " e WHERE e." + atributo + " LIKE :valor";
                q = em.createQuery(jpql, clazz);
                q.setParameter("valor", "%" + valor + "%");
            }
            return q.getResultList();
        }, false);
    }

    public static <T> void setActiveStatus(Class<T> clazz, int id, boolean ativar) throws RuntimeException {
        executeVoid(em -> {
            T entity = em.find(clazz, id);
            if (entity != null) {
                try {
                    clazz.getMethod("setStatus", char.class).invoke(entity, ativar ? 'A' : 'I');
                    em.merge(entity);
                } catch (IllegalAccessException | NoSuchMethodException | SecurityException
                        | InvocationTargetException ex) {
                    AppLogger.error("Erro ao definir status via reflexão", ex);
                    throw new RuntimeException("Erro ao definir status via reflexão", ex);
                }
            }
        }, true);
    }
}