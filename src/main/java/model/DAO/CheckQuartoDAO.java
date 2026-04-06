package model.DAO;

import java.util.List;

import javax.persistence.TypedQuery;

import model.Check;
import model.CheckQuarto;
import model.DAO.JPA.JPADao;

public class CheckQuartoDAO implements InterfaceDAO<CheckQuarto> {

    @Override
    public void Create(CheckQuarto objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public CheckQuarto Retrieve(int id) throws RuntimeException {
        return JPADao.find(CheckQuarto.class, id);
    }

    @Override
    public List<CheckQuarto> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(CheckQuarto.class, "CheckQuarto", atributo, valor);
    }

    @Override
    public void Update(CheckQuarto objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(CheckQuarto objeto) throws RuntimeException {
        JPADao.delete(CheckQuarto.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(CheckQuarto.class, id, ativar);
    }

    public List<CheckQuarto> RetrieveByCheck(int checkId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<CheckQuarto> query = em.createQuery(
                "SELECT cq FROM CheckQuarto cq JOIN FETCH cq.quarto WHERE cq.check.id = :checkId ORDER BY cq.id",
                CheckQuarto.class
            );
            query.setParameter("checkId", checkId);
            return query.getResultList();
        }, false);
    }

    public List<model.DTO.CheckQuartoBuscaDTO> RetrieveAtivosComFiltro(String atributo, String valor) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            StringBuilder jpql = new StringBuilder(
                "SELECT new model.DTO.CheckQuartoBuscaDTO(" +
                "cq.id, q.identificacao, c.dataHoraEntrada, h.nome, c.id) " +
                "FROM CheckQuarto cq " +
                "JOIN cq.quarto q " +
                "JOIN cq.check c " +
                "LEFT JOIN CheckHospede ch ON ch.check = c AND ch.tipoHospede = 'Titular' " +
                "LEFT JOIN ch.hospede h " +
                "WHERE (c.status = 'A' OR c.status = 'a') "
            );

            if (valor != null && !valor.trim().isEmpty()) {
                switch (atributo) {
                    case "Quarto":
                        jpql.append("AND LOWER(q.identificacao) LIKE :valor ");
                        break;
                    case "Hóspede":
                        jpql.append("AND LOWER(h.nome) LIKE :valor ");
                        break;
                    case "Check ID":
                        jpql.append("AND c.id = :valorId ");
                        break;
                }
            }
            
            jpql.append("ORDER BY q.identificacao");

            TypedQuery<model.DTO.CheckQuartoBuscaDTO> query = em.createQuery(jpql.toString(), model.DTO.CheckQuartoBuscaDTO.class);
            if (valor != null && !valor.trim().isEmpty()) {
                if ("Check ID".equals(atributo)) {
                    try {
                        query.setParameter("valorId", Integer.parseInt(valor));
                    } catch (NumberFormatException e) {
                        query.setParameter("valorId", 0);
                    }
                } else {
                    query.setParameter("valor", "%" + valor.toLowerCase() + "%");
                }
            }
            return query.getResultList();
        }, false);
    }

    public List<CheckQuarto> RetrieveAtivos() throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<CheckQuarto> query = em.createQuery(
                "SELECT cq FROM CheckQuarto cq " +
                "JOIN FETCH cq.quarto " +
                "JOIN FETCH cq.check c " +
                "WHERE c.status = 'A' OR c.status = 'a' " +
                "ORDER BY cq.quarto.identificacao",
                CheckQuarto.class
            );
            return query.getResultList();
        }, false);
    }
     public void ReplaceCheckQuartos(int checkId, List<CheckQuarto> checkQuartos) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Check check = em.find(Check.class, checkId);
            if (check == null) {
                throw new RuntimeException("Check não encontrado para vincular quartos.");
            }

            // IDs que devem ser mantidos
            java.util.Set<Integer> idsParaManter = new java.util.HashSet<>();
            for (CheckQuarto cq : checkQuartos) {
                if (cq.getId() > 0) {
                    idsParaManter.add(cq.getId());
                }
            }

            // Remove apenas os que não estão na nova lista (órfãos)
            TypedQuery<CheckQuarto> query = em.createQuery(
                "SELECT cq FROM CheckQuarto cq WHERE cq.check.id = :checkId",
                CheckQuarto.class
            );
            query.setParameter("checkId", checkId);

            for (CheckQuarto cqExistente : query.getResultList()) {
                if (!idsParaManter.contains(cqExistente.getId())) {
                    em.remove(cqExistente);
                }
            }

            // Persiste novos ou atualiza existentes
            for (CheckQuarto cq : checkQuartos) {
                cq.setCheck(check);
                if (cq.getId() == 0) {
                    em.persist(cq);
                } else {
                    em.merge(cq);
                }
            }
        }, true);
    }
}
