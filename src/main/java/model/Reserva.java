package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private char status;

    @OneToMany(mappedBy = "reserva")
    private List<Check> checks;

    public Reserva() {
    }

    public Reserva(int id, Date dataHoraReserva, Date dataPrevistaEntrada, Date dataPrevistaSaida, String obs, char status) {
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
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
