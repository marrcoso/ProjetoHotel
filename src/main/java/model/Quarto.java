package model;

import javax.persistence.*;

@Entity
@Table(name = "quarto")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "capacidade_hospedes", nullable = false)
    private int capacidadeHospedes;

    @Column(name = "metragem", nullable = false)
    private float metragem;

    @Column(name = "identificacao", nullable = false)
    private String identificacao;

    @Column(name = "andar", nullable = false)
    private int andar;

    @Column(name = "flag_animais", nullable = false)
    private boolean flagAnimais;

    @Column(name = "obs")
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    public Quarto() {
    }

    public Quarto(int id, String descricao, int capacidadeHospedes, float metragem, String identificacao, int andar,
            boolean flagAnimais, String obs, char status) {
        this.id = id;
        this.descricao = descricao;
        this.capacidadeHospedes = capacidadeHospedes;
        this.metragem = metragem;
        this.identificacao = identificacao;
        this.andar = andar;
        this.flagAnimais = flagAnimais;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidadeHospedes() {
        return capacidadeHospedes;
    }

    public void setCapacidadeHospedes(int capacidadeHospedes) {
        this.capacidadeHospedes = capacidadeHospedes;
    }

    public float getMetragem() {
        return metragem;
    }

    public void setMetragem(float metragem) {
        this.metragem = metragem;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public boolean isFlagAnimais() {
        return flagAnimais;
    }

    public void setFlagAnimais(boolean flagAnimais) {
        this.flagAnimais = flagAnimais;
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
        return "id = " + id + " Desc. = " + descricao + " Capacidade = " + capacidadeHospedes +
                " Metragem = " + metragem + " Identificação = " + identificacao + " Andar = " + andar +
                " Animais = " + flagAnimais + " Obs = " + obs + " Status = " + status;
    }
}
