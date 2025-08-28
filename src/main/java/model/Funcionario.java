package model;
public class Funcionario extends Pessoa{
   
    private String usuario;
    private String senha;

    public Funcionario() {
  
    }

    public Funcionario( int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String cpf, String rg,String usuario, String senha, String obs, char status) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, cpf, rg, obs, status);
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return  "id   = " + super.toString() + 
                "\nnome = " + this.getNome()+
                "\nUsuário = " + this.getUsuario()+
                "\nSenha   = " + this.getSenha();
    }
    
    
    
    
    
    
}
