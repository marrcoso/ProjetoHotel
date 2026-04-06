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

    @Column(name = "valor_unitario", nullable = false)
    private float valorUnitario;

    @Column(name = "data_hora_pedido", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPedido;

    @Column(name = "obs", nullable = false)
    private String obs;

    @Column(name = "status", nullable = false)
    private char status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "check_quarto_id")
    private CheckQuarto checkQuarto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public CopaQuarto() {
    }

    public CopaQuarto(int id, float quantidade, float valorUnitario, Date dataHoraPedido, String obs, char status, CheckQuarto checkQuarto, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataHoraPedido = dataHoraPedido;
        this.obs = obs;
        this.status = status;
        this.checkQuarto = checkQuarto;
        this.produto = produto;
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

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public CheckQuarto getCheckQuarto() {
        return checkQuarto;
    }

    public void setCheckQuarto(CheckQuarto checkQuarto) {
        this.checkQuarto = checkQuarto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "CopaQuarto{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                ", dataHoraPedido=" + dataHoraPedido +
                ", obs='" + obs + '\'' +
                ", status='" + status + '\'' +
                ", checkQuarto=" + (checkQuarto != null ? checkQuarto.getId() : "null") +
                ", produto=" + (produto != null ? produto.getDescricao() : "null") +
                '}';
    }
}