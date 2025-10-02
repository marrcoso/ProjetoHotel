package model;

import java.time.LocalDateTime;

public class Check {
    private int id;
    private LocalDateTime dataHoraCadastro;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private String obs;
    private String status;
    private Reserva reserva;

    public Check() {
    }

    public Check(int id, LocalDateTime dataHoraCadastro, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, String obs, String status, Reserva reserva) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
        this.obs = obs;
        this.status = status;
        this.reserva = reserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
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

    @Override
    public String toString() {
        return "id              = " + this.getId()
                + "\nData/Hora Cad. = " + this.getDataHoraCadastro()
                + "\nData/Hora Ent. = " + this.getDataHoraEntrada()
                + "\nData/Hora Sai. = " + this.getDataHoraSaida()
                + "\nObs            = " + this.getObs()
                + "\nStatus         = " + this.getStatus()
                + "\nReserva ID     = " + this.getReserva().getId();
    }
}
