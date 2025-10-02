package model;

public class Quarto {
    private int id;
    private String descricao;
    private int capacidadeHospedes;
    private float metragem;
    private String identificacao;
    private int andar;
    private boolean flagAnimais;
    private String obs;
    private char status;

    public Quarto() {
    }

    public Quarto(int id, String descricao, int capacidadeHospedes, float metragem, String identificacao, int andar, boolean flagAnimais, String obs, char status) {
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
        if ( (status == 'A') || (status == 'C') || (status == 'a') || (status == 'c') ) {
            this.status = status;
        }else {
            this.status = 'A';
        }
    }

    @Override
    public String toString() {
        return 
        "id                   = " + id + 
        "\ndescricao          = " + descricao + 
        "\ncapacidadeHospedes = " + capacidadeHospedes + 
        "\nmetragem           = " + metragem + 
        "\nidentificacao      = " + identificacao + 
        "\nandar              = " + andar + 
        "\nflagAnimais        = " + flagAnimais + 
        "\nobs                = " + obs + 
        "\nstatus             = " + status;
    }
}
