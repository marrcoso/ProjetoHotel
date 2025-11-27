package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "caixa")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "valor_de_abertura", nullable = false)
    private float valorDeAbertura;

    @Column(name = "valor_de_fechamento", nullable = false)
    private float valorDeFechamento;

    @Column(name = "data_hora_abertura", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraAbertura;

    @Column(name = "data_hora_fechamento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraFechamento;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    public Caixa() {
    }

    public Caixa(int id, float valorDeAbertura, float valorDeFechamento, Date dataHoraAbertura, Date dataHoraFechamento, String obs, char status) {
        this.id = id;
        this.valorDeAbertura = valorDeAbertura;
        this.valorDeFechamento = valorDeFechamento;
        this.dataHoraAbertura = dataHoraAbertura;
        this.dataHoraFechamento = dataHoraFechamento;
        this.obs = obs;
        this.status = status;
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

    public Date getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(Date dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public Date getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(Date dataHoraFechamento) {
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

    @Override
    public String toString() {
        return  "id                   = " + this.getId() +
                "\nvalorDeAbertura    = " + this.getValorDeAbertura() +
                "\nvalorDeFechamento  = " + this.getValorDeFechamento() +
                "\ndataHoraAbertura   = " + this.getDataHoraAbertura() +
                "\ndataHoraFechamento = " + this.getDataHoraFechamento() +
                "\nobs                = " + this.getObs() +
                "\nstatus             = " + this.getStatus();
    }
}
