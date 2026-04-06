package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "receber")
public class Receber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCadastro;

    @Column(name = "valor_original", nullable = false)
    private float valorOriginal;

    @Column(name = "desconto", nullable = false)
    private float desconto;

    @Column(name = "acrescimo", nullable = false)
    private float acrescimo;

    @Column(name = "valor_pago", nullable = false)
    private float valorPago;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "check_id")
    private Check check;

    public Receber() {
    }

    public Receber(int id, Date dataHoraCadastro, float valorOriginal, float desconto, float acrescimo, float valorPago, String obs, char status, Check check) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.valorOriginal = valorOriginal;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.valorPago = valorPago;
        this.obs = obs;
        this.status = status;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Date dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public float getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(float valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public float getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(float acrescimo) {
        this.acrescimo = acrescimo;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
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

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "id               = " + this.getId() +
                "\nData/Hora Cad.  = " + this.getDataHoraCadastro() +
                "\nValor Original  = " + this.getValorOriginal() +
                "\nDesconto        = " + this.getDesconto() +
                "\nAcréscimo       = " + this.getAcrescimo() +
                "\nValor Pago      = " + this.getValorPago() +
                "\nObs             = " + this.getObs() +
                "\nStatus          = " + this.getStatus() +
                "\nCheck ID        = " + (this.getCheck() != null ? this.getCheck().getId() : "null");
    }


}
