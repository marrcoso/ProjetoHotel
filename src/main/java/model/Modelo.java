package model;

import javax.persistence.*;

@Entity
@Table(name = "modelo")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "status", nullable = false)
    private char status;

    // Relação com Marca
    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", referencedColumnName = "id")
    private Marca marca;

    public Modelo() {
    }

    public Modelo(int id, String descricao, char status, Marca marca) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.marca = marca;
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "id = " + id + " Desc. = " + descricao + " Status= " + status + " Marca= "
                + (marca != null ? marca.getDescricao() : "");
    }
}