package model.DAO;

import java.util.List;

import javax.persistence.TypedQuery;

import model.Check;
import model.CheckHospede;
import model.DAO.JPA.JPADao;
import model.Hospede;

public class CheckHospedeDAO implements InterfaceDAO<CheckHospede> {

    @Override
    public void Create(CheckHospede objeto) throws RuntimeException {
        JPADao.create(objeto);
    }

    @Override
    public CheckHospede Retrieve(int id) throws RuntimeException {
        return JPADao.find(CheckHospede.class, id);
    }

    @Override
    public List<CheckHospede> Retrieve(String atributo, String valor) throws RuntimeException {
        return JPADao.findByAttribute(CheckHospede.class, "CheckHospede", atributo, valor);
    }

    @Override
    public void Update(CheckHospede objeto) throws RuntimeException {
        JPADao.update(objeto);
    }

    @Override
    public void Delete(CheckHospede objeto) throws RuntimeException {
        JPADao.delete(CheckHospede.class, objeto.getId());
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        JPADao.setActiveStatus(CheckHospede.class, id, ativar);
    }

    public List<CheckHospede> RetrieveByCheck(int checkId) throws RuntimeException {
        return JPADao.executeWithEntityManager(em -> {
            TypedQuery<CheckHospede> query = em.createQuery(
                "SELECT ch FROM CheckHospede ch JOIN FETCH ch.hospede WHERE ch.check.id = :checkId ORDER BY ch.id",
                CheckHospede.class
            );
            query.setParameter("checkId", checkId);
            return query.getResultList();
        }, false);
    }

    public void ReplaceHospedesDoCheck(int checkId, List<Integer> hospedeIds) throws RuntimeException {
        JPADao.executeVoid(em -> {
            Check check = em.find(Check.class, checkId);
            if (check == null) {
                throw new RuntimeException("Check não encontrado para vincular hóspedes.");
            }

            TypedQuery<CheckHospede> query = em.createQuery(
                "SELECT ch FROM CheckHospede ch WHERE ch.check.id = :checkId",
                CheckHospede.class
            );
            query.setParameter("checkId", checkId);

            for (CheckHospede checkHospede : query.getResultList()) {
                em.remove(checkHospede);
            }

            for (int i = 0; i < hospedeIds.size(); i++) {
                Hospede hospede = em.find(Hospede.class, hospedeIds.get(i));
                if (hospede == null) {
                    throw new RuntimeException("Hóspede não encontrado para vincular ao check.");
                }

                CheckHospede novo = new CheckHospede();
                novo.setCheck(check);
                novo.setHospede(hospede);
                novo.setTipoHospede(i == 0 ? "Titular" : "Acompanhante");
                novo.setObs("");
                novo.setStatus('A');
                em.persist(novo);
            }
        }, true);
    }
}
