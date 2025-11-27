package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movimento_caixa")
public class MovimentoCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_movimento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraMovimento;

    @Column(name = "valor", nullable = false)
    private float valor;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receber_id")
    private Receber receber;

    public MovimentoCaixa() {
    }

    public MovimentoCaixa(int id, Date dataHoraMovimento, float valor, String descricao, String obs, char status, Caixa caixa, Receber receber) {
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

    public Date getDataHoraMovimento() {
        return dataHoraMovimento;
    }

    public void setDataHoraMovimento(Date dataHoraMovimento) {
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
