package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reserva_quarto")
public class ReservaQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_inicio", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraFim;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public ReservaQuarto() {
    }

    public ReservaQuarto(int id, Date dataHoraInicio, Date dataHoraFim, String obs, String status, Reserva reserva, Quarto quarto) {
        this.id = id;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.obs = obs;
        this.status = status;
        this.reserva = reserva;
        this.quarto = quarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Date dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
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

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "id               = " + this.getId() +
            "\nData/Hora Inicio  = " + this.getDataHoraInicio() +
            "\nData/Hora Fim     = " + this.getDataHoraFim() +
            "\nObs               = " + this.getObs() +
            "\nStatus            = " + this.getStatus() +
            "\nReserva           = " + (this.getReserva() != null ? this.getReserva().toString() : "null") +
            "\nQuarto            = " + (this.getQuarto() != null ? this.getQuarto().toString() : "null");
    }
}

