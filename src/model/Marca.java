package model;

public class Marca {

    private int id;
    private String descricao;
    private char status;

    public Marca() {
    }

    public Marca(int id, String descricao, char status) {
        this.id = id;
        this.descricao = descricao;
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        if ( (status == 'A') || (status == 'C') || (status == 'a') || (status == 'c') ) {
            this.status = status;
        }else {
            this.status = 'A';
        }
    }

    @Override
    public String toString() {
        return    "id    = " + this.getId()
                + "Desc. = " + this.getDescricao()
                + "Status= " + this.getStatus();
    }

}
