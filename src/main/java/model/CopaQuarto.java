package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "copa_quarto")
public class CopaQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantidade", nullable = false)
    private float quantidade;

    @Column(name = "data_hora_pedido", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPedido;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "check_quarto_id")
    private CheckQuarto checkQuarto;

    public CopaQuarto() {
    }

    public CopaQuarto(int id, float quantidade, Date dataHoraPedido, String obs, String status, CheckQuarto checkQuarto) {
        this.id = id;
        this.quantidade = quantidade;
        this.dataHoraPedido = dataHoraPedido;
        this.obs = obs;
        this.status = status;
        this.checkQuarto = checkQuarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataHoraPedido() {
        return dataHoraPedido;
    }

    public void setDataHoraPedido(Date dataHoraPedido) {
        this.dataHoraPedido = dataHoraPedido;
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

    public CheckQuarto getCheckQuarto() {
        return checkQuarto;
    }

    public void setCheckQuarto(CheckQuarto checkQuarto) {
        this.checkQuarto = checkQuarto;
    }

    @Override
    public String toString() {
        return "CopaQuarto{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", dataHoraPedido=" + dataHoraPedido +
                ", obs='" + obs + '\'' +
                ", status='" + status + '\'' +
                ", checkQuarto=" + checkQuarto +
                '}';
    }
}