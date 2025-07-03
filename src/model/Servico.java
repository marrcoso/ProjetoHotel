
package model;


public class Servico {
    private int id;
    private String descricao;
    private String obs;
    private char status;

    public Servico(int id, String descricao, String obs, char status) {
        this.id = id;
        this.descricao = descricao;
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
        if ( (status == 'A') || (status == 'C') || (status == 'a') || (status == 'c') ) {
            this.status = status;
        }else {
            this.status = 'A';
        }
    }

    @Override
    public String toString() {
        return 
        "id          = " + this.id + 
        "\ndescricao = " + this.descricao + 
        "\nobs       = " + this.obs + 
        "\nstatus    = " + this.status;
    }
    
    
    
    
}
