package model;
public class Hospede extends Pessoa{
    private String razaoSocial;
    private String cnpj;
    private String inscricaoEstdual;
    private String contato;

    public Hospede() {
    }

    public Hospede( int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String cpf, String rg, String razaoSocial, String cnpj, String inscricaoEstdual, String contato, String obs, char status) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, cpf, rg, obs, status);
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.inscricaoEstdual = inscricaoEstdual;
        this.contato = contato;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstdual() {
        return inscricaoEstdual;
    }

    public void setInscricaoEstdual(String inscricaoEstdual) {
        this.inscricaoEstdual = inscricaoEstdual;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return  super.toString() + 
                "\ncnpj   = " + this.getCnpj()+
                "\nCep    = " + this.getCnpj()+
                "\nCidade = " + this.getCidade()+
                "\nBairro = " + this.getBairro()+
                "\nLogradouro = " + this.getLogradouro()+
                "\nComplemento= " + this.getComplemento()+
                "\nContato    = " + this.getContato()+
                "\nStatus = " + this.getStatus();
    }
    
    
    
    
    
}
