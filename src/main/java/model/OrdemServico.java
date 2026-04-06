package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCadastro;

    @Column(name = "data_hora_prevista_inicio", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPrevistaInicio;

    @Column(name = "data_hora_prevista_termino", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPrevistaTermino;

    @Column(name = "obs", nullable = false, length = 100)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne(optional = true)
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public OrdemServico() {
    }

    public OrdemServico(int id, Date dataHoraCadastro, Date dataHoraPrevistaInicio, Date dataHoraPrevistaTermino, 
                        String obs, char status, Check check, Servico servico, Quarto quarto) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraPrevistaInicio = dataHoraPrevistaInicio;
        this.dataHoraPrevistaTermino = dataHoraPrevistaTermino;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.servico = servico;
        this.quarto = quarto;
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

    public Date getDataHoraPrevistaInicio() {
        return dataHoraPrevistaInicio;
    }

    public void setDataHoraPrevistaInicio(Date dataHoraPrevistaInicio) {
        this.dataHoraPrevistaInicio = dataHoraPrevistaInicio;
    }

    public Date getDataHoraPrevistaTermino() {
        return dataHoraPrevistaTermino;
    }

    public void setDataHoraPrevistaTermino(Date dataHoraPrevistaTermino) {
        this.dataHoraPrevistaTermino = dataHoraPrevistaTermino;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "id=" + id +
                ", dataHoraCadastro=" + dataHoraCadastro +
                ", dataHoraPrevistaInicio=" + dataHoraPrevistaInicio +
                ", dataHoraPrevistaTermino=" + dataHoraPrevistaTermino +
                ", obs='" + obs + '\'' +
                ", status=" + status +
                ", check=" + (check != null ? check.getId() : "null") +
                ", servico=" + (servico != null ? servico.getDescricao() : "null") +
                ", quarto=" + (quarto != null ? quarto.getDescricao() : "null") +
                '}';
    }
}
