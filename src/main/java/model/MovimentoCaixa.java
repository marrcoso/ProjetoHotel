package model;

import java.time.LocalDateTime;

public class MovimentoCaixa {
    private int id;
    private LocalDateTime dataHoraMovimento;
    private float valor;
    private String descricao;
    private String obs;
    private char status;
    private Caixa caixa;
    private Receber receber;

    public MovimentoCaixa() {
    }

    public MovimentoCaixa(int id, LocalDateTime dataHoraMovimento, float valor, String descricao, String obs, char status, Caixa caixa, Receber receber) {
        this.receber = receber;
        this.caixa = caixa;
        this.id = id;
        this.dataHoraMovimento = dataHoraMovimento;
        this.valor = valor;
        this.descricao = descricao;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraMovimento() {
        return dataHoraMovimento;
    }

    public void setDataHoraMovimento(LocalDateTime dataHoraMovimento) {
        this.dataHoraMovimento = dataHoraMovimento;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Receber getReceber() {
        return receber;
    }

    public void setReceber(Receber receber) {
        this.receber = receber;
    }

    @Override
    public String toString() {
        return "Id                     = " + this.getId() +
                "\nData/Hora Movimento = " + this.getDataHoraMovimento() +
                "\nValor               = " + this.getValor() +
                "\nDesc.               = " + this.getDescricao() +
                "\nObs                 = " + this.getObs() +
                "\nStatus              = " + this.getStatus() +
                "\nCaixa ID            = " + (caixa != null ? caixa.getId() : "N/A") +
                "\nReceber ID          = " + (receber != null ? receber.getId() : "N/A");
    }
}
