package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "check")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCadastro;

    @Column(name = "data_hora_entrada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraEntrada;

    @Column(name = "data_hora_saida", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraSaida;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    public Check() {
    }

    public Check(int id, Date dataHoraCadastro, Date dataHoraEntrada, Date dataHoraSaida, String obs, char status) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
        this.obs = obs;
        this.status = status;
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

    public Date getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(Date dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public Date getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(Date dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
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
        return "id              = " + this.getId()
                + "\nData/Hora Cad. = " + this.getDataHoraCadastro()
                + "\nData/Hora Ent. = " + this.getDataHoraEntrada()
                + "\nData/Hora Sai. = " + this.getDataHoraSaida()
                + "\nObs            = " + this.getObs()
                + "\nStatus         = " + this.getStatus();
    }
}
