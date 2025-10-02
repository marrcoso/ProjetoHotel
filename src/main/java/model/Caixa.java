package model;

import java.time.LocalDateTime;

public class Caixa {
    private int id;
    private float valorDeAbertura;
    private float valorDeFechamento;
    private LocalDateTime dataHoraAbertura;
    private LocalDateTime dataHoraFechamento;
    private String obs;
    private char status;
    private Funcionario funcionario;

    public Caixa() {
    }

    public Caixa(int id, float valorDeAbertura, float valorDeFechamento, LocalDateTime dataHoraAbertura, LocalDateTime dataHoraFechamento, String obs, char status, Funcionario funcionario) {
        this.id = id;
        this.valorDeAbertura = valorDeAbertura;
        this.valorDeFechamento = valorDeFechamento;
        this.dataHoraAbertura = dataHoraAbertura;
        this.dataHoraFechamento = dataHoraFechamento;
        this.obs = obs;
        this.status = status;
        this.funcionario = funcionario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValorDeAbertura() {
        return valorDeAbertura;
    }

    public void setValorDeAbertura(float valorDeAbertura) {
        this.valorDeAbertura = valorDeAbertura;
    }

    public float getValorDeFechamento() {
        return valorDeFechamento;
    }

    public void setValorDeFechamento(float valorDeFechamento) {
        this.valorDeFechamento = valorDeFechamento;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public LocalDateTime getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return  "id                   = " + this.getId() +
                "\nvalorDeAbertura    = " + this.getValorDeAbertura() +
                "\nvalorDeFechamento  = " + this.getValorDeFechamento() +
                "\ndataHoraAbertura   = " + this.getDataHoraAbertura() +
                "\ndataHoraFechamento = " + this.getDataHoraFechamento() +
                "\nobs                = " + this.getObs() +
                "\nstatus             = " + this.getStatus() +
                "\nfuncionario        = " + this.getFuncionario().getNome();
    }
}
