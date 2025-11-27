package model;

import javax.persistence.*;

@Entity
@Table(name = "check_hospede")
public class CheckHospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tipo_hospede", nullable = false)
    private String tipoHospede;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    public CheckHospede() {
    }

    public CheckHospede(int id, String tipoHospede, String obs, char status, Check check, Hospede hospede) {
        this.id = id;
        this.tipoHospede = tipoHospede;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.hospede = hospede;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoHospede() {
        return tipoHospede;
    }

    public void setTipoHospede(String tipoHospede) {
        this.tipoHospede = tipoHospede;
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

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    @Override
    public String toString() {
        return "id           = " + this.getId() +
                "\nTipo Hospede = " + this.getTipoHospede() +
                "\nObs          = " + this.getObs() +
                "\nStatus       = " + this.getStatus() +
                "\nCheck ID     = " + this.getCheck().getId() +
                "\nHospede      = " + this.getHospede().getNome();
    }
}
