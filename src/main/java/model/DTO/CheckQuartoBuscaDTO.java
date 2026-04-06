package model.DTO;

import java.util.Date;

public class CheckQuartoBuscaDTO {
    private int id;
    private String quartoIdentificacao;
    private Date dataEntrada;
    private String hospedeTitular;
    private int checkId;

    public CheckQuartoBuscaDTO(int id, String quartoIdentificacao, Date dataEntrada, String hospedeTitular, int checkId) {
        this.id = id;
        this.quartoIdentificacao = quartoIdentificacao;
        this.dataEntrada = dataEntrada;
        this.hospedeTitular = hospedeTitular;
        this.checkId = checkId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuartoIdentificacao() {
        return quartoIdentificacao;
    }

    public void setQuartoIdentificacao(String quartoIdentificacao) {
        this.quartoIdentificacao = quartoIdentificacao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getHospedeTitular() {
        return hospedeTitular;
    }

    public void setHospedeTitular(String hospedeTitular) {
        this.hospedeTitular = hospedeTitular;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }
}
