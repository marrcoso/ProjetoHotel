package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_reserva", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraReserva;

    @Column(name = "data_prevista_entrada", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataPrevistaEntrada;

    @Column(name = "data_prevista_saida", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataPrevistaSaida;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "check_id")
    private Check check;

    public Reserva() {
    }

    public Reserva(int id, Date dataHoraReserva, Date dataPrevistaEntrada, Date dataPrevistaSaida, String obs, String status) {
        this.id = id;
        this.dataHoraReserva = dataHoraReserva;
        this.dataPrevistaEntrada = dataPrevistaEntrada;
        this.dataPrevistaSaida = dataPrevistaSaida;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataHoraReserva() {
        return dataHoraReserva;
    }

    public void setDataHoraReserva(Date dataHoraReserva) {
        this.dataHoraReserva = dataHoraReserva;
    }

    public Date getDataPrevistaEntrada() {
        return dataPrevistaEntrada;
    }

    public void setDataPrevistaEntrada(Date dataPrevistaEntrada) {
        this.dataPrevistaEntrada = dataPrevistaEntrada;
    }

    public Date getDataPrevistaSaida() {
        return dataPrevistaSaida;
    }

    public void setDataPrevistaSaida(Date dataPrevistaSaida) {
        this.dataPrevistaSaida = dataPrevistaSaida;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        return "id              = " + this.getId()
                + "\nData/Hora Cad. = " + this.getDataHoraReserva()
                + "\nData/Hora Ent. = " + this.getDataPrevistaEntrada()
                + "\nData/Hora Sai. = " + this.getDataPrevistaSaida()
                + "\nObs            = " + this.getObs()
                + "\nStatus         = " + this.getStatus();
    }
}
