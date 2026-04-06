package model.DAO;

import java.util.List;

import javax.persistence.TypedQuery;

import model.AlocacaoVaga;
import model.Check;
import model.DAO.JPA.JPADao;
import model.VagaEstacionamento;
import model.Veiculo;

public class AlocacaoVagaDAO implements InterfaceDAO<AlocacaoVaga> {

    @Override
    public void Create(AlocacaoVaga objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public AlocacaoVaga Retrieve(int id) throws RuntimeException {
        return JPADao.find(AlocacaoVaga.class, id);
    }

    @Override
    public List<AlocacaoVaga> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(AlocacaoVaga.class, "AlocacaoVaga", atributo, valor);
    }

    @Override
    public void Update(AlocacaoVaga objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(AlocacaoVaga objeto) throws RuntimeException {
        JPADao.delete(AlocacaoVaga.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(AlocacaoVaga.class, id, ativar);
    }

    public List<AlocacaoVaga> RetrieveByCheck(int checkId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<AlocacaoVaga> query = em.createQuery(
                "SELECT av FROM AlocacaoVaga av JOIN FETCH av.vagaEstacionamento WHERE av.check.id = :checkId ORDER BY av.id",
                AlocacaoVaga.class
            );
            query.setParameter("checkId", checkId);
            return query.getResultList();
        }, false);
    }

    public void ReplaceAlocacoesDoCheck(int checkId, List<AlocacaoVaga> alocacoes) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Check check = em.find(Check.class, checkId);
            if (check == null) {
                throw new RuntimeException("Check não encontrado para vincular vagas.");
            }

            // IDs que devem ser mantidos
            java.util.Set<Integer> idsParaManter = new java.util.HashSet<>();
            for (AlocacaoVaga av : alocacoes) {
                if (av.getId() > 0) {
                    idsParaManter.add(av.getId());
                }
            }

            // Remove apenas as alocações existentes que não estão na nova lista (órfãos)
            TypedQuery<AlocacaoVaga> query = em.createQuery(
                "SELECT av FROM AlocacaoVaga av WHERE av.check.id = :checkId",
                AlocacaoVaga.class
            );
            query.setParameter("checkId", checkId);

            for (AlocacaoVaga alocExistente : query.getResultList()) {
                if (!idsParaManter.contains(alocExistente.getId())) {
                    em.remove(alocExistente);
                }
            }

            // Persiste novas ou atualiza existentes
            for (AlocacaoVaga aloc : alocacoes) {
                // Garante que as referências estejam gerenciadas pelo EntityManager
                VagaEstacionamento vaga = em.find(VagaEstacionamento.class, aloc.getVagaEstacionamento().getId());
                Veiculo veiculo = em.find(Veiculo.class, aloc.getVeiculo().getId());
                if (vaga == null || veiculo == null) {
                    throw new RuntimeException("Vaga ou veículo não encontrado para vincular ao check.");
                }
                
                aloc.setCheck(check);
                aloc.setVagaEstacionamento(vaga);
                aloc.setVeiculo(veiculo);
                if (aloc.getObs() == null) {
                    aloc.setObs("");
                }
                
                if (aloc.getId() == 0) {
                    em.persist(aloc);
                } else {
                    em.merge(aloc);
                }
            }
        }, true);
    }
}
