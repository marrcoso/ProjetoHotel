package model;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private LocalDateTime dataHoraReserva;
    private LocalDateTime dataPrevistaEntrada;
    private LocalDateTime dataPrevistaSaida;
    private String obs;
    private char status;

    public Reserva() {
    }

    public Reserva(int id, LocalDateTime dataHoraReserva, LocalDateTime dataPrevistaEntrada, LocalDateTime dataPrevistaSaida, String obs, char status) {
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

    public LocalDateTime getDataHoraReserva() {
        return dataHoraReserva;
    }

    public void setDataHoraReserva(LocalDateTime dataHoraReserva) {
        this.dataHoraReserva = dataHoraReserva;
    }

    public LocalDateTime getDataPrevistaEntrada() {
        return dataPrevistaEntrada;
    }

    public void setDataPrevistaEntrada(LocalDateTime dataPrevistaEntrada) {
        this.dataPrevistaEntrada = dataPrevistaEntrada;
    }

    public LocalDateTime getDataPrevistaSaida() {
        return dataPrevistaSaida;
    }

    public void setDataPrevistaSaida(LocalDateTime dataPrevistaSaida) {
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
