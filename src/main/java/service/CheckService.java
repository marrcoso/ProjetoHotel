package service;

import java.util.Date;
import java.util.List;

import model.Check;
import model.DAO.CheckDAO;

public class CheckService implements InterfaceService<Check> {

    private final CheckDAO checkDAO;

    public CheckService() {
        this.checkDAO = new CheckDAO();
    }

    @Override
    public void Criar(Check objeto) throws RuntimeException {
        prepararDatasObrigatorias(objeto);
        checkDAO.Create(objeto);
    }

    @Override
    public Check Carregar(int id) throws RuntimeException {
        return checkDAO.Retrieve(id);
    }

    @Override
    public List<Check> Carregar(String atributo, String valor) throws RuntimeException {
        return checkDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(Check objeto) throws RuntimeException {
        prepararDatasObrigatorias(objeto);
        checkDAO.Update(objeto);
    }

    @Override
    public void Apagar(Check objeto) throws RuntimeException {
        checkDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        checkDAO.AtivarInativar(id, ativar);
    }

    public float calcularValorPagar(float valorOriginal, float desconto, float acrescimo) {
        return valorOriginal - desconto + acrescimo;
    }

    public void prepararDatasObrigatorias(Check check) {
        Date agora = new Date();

        if (check.getDataHoraCadastro() == null) {
            check.setDataHoraCadastro(agora);
        }
        if (check.getDataHoraEntrada() == null) {
            check.setDataHoraEntrada(check.getDataHoraCadastro());
        }
        if (check.getObs() == null) {
            check.setObs("");
        }
    }
}
