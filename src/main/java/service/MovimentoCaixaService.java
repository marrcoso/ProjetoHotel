package service;

import java.util.Date;
import java.util.List;
import model.Caixa;
import model.MovimentoCaixa;
import model.Receber;
import model.DAO.MovimentoCaixaDAO;

public class MovimentoCaixaService implements InterfaceService<MovimentoCaixa> {

    private final MovimentoCaixaDAO movimentoCaixaDAO;

    public MovimentoCaixaService() {
        this.movimentoCaixaDAO = new MovimentoCaixaDAO();
    }

    @Override
    public void Criar(MovimentoCaixa objeto) throws RuntimeException {
        movimentoCaixaDAO.Create(objeto);
    }

    @Override
    public MovimentoCaixa Carregar(int id) throws RuntimeException {
        return movimentoCaixaDAO.Retrieve(id);
    }

    @Override
    public List<MovimentoCaixa> Carregar(String atributo, String valor) throws RuntimeException {
        return movimentoCaixaDAO.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(MovimentoCaixa objeto) throws RuntimeException {
        movimentoCaixaDAO.Update(objeto);
    }

    @Override
    public void Apagar(MovimentoCaixa objeto) throws RuntimeException {
        movimentoCaixaDAO.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws RuntimeException {
        movimentoCaixaDAO.AtivarInativar(id, ativar);
    }

    public void gerarMovimentoCheckHospede(Receber receber, Caixa caixa) throws RuntimeException {
        MovimentoCaixa movimento = this.CarregarPorReceber(receber.getId());
        
        if (movimento == null) {
            movimento = new MovimentoCaixa();
            movimento.setReceber(receber);
            movimento.setStatus('A');
        }
        
        movimento.setCaixa(caixa);
        movimento.setDataHoraMovimento(new Date());
        movimento.setValor(receber.getValorPago());
        movimento.setDescricao("Pagamento de Hospedagem - Check #" + receber.getCheck().getId());
        movimento.setObs(receber.getObs());
        
        if (movimento.getId() == 0) {
            this.Criar(movimento);
        } else {
            this.Atualizar(movimento);
        }
    }

    public MovimentoCaixa CarregarPorReceber(int receberId) throws RuntimeException {
        return movimentoCaixaDAO.RetrievePorReceber(receberId);
    }

    public List<MovimentoCaixa> CarregarPorCaixa(int caixaId) throws RuntimeException {
        return movimentoCaixaDAO.RetrievePorCaixa(caixaId, "Todos");
    }

    public List<MovimentoCaixa> CarregarPorCaixa(int caixaId, String statusFilter) throws RuntimeException {
        return movimentoCaixaDAO.RetrievePorCaixa(caixaId, statusFilter);
    }

    public float getTotalAtivos(int caixaId) throws RuntimeException {
        return movimentoCaixaDAO.getSumAtivosByCaixa(caixaId);
    }
}
