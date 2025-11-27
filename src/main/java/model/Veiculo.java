package model;

import javax.persistence.*;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "placa", nullable = false)
    private String placa;

    @Column(name = "cor")
    private String cor;

    @Column(name = "status", nullable = false)
    private char status;
    
    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    public Veiculo() {}

    public Veiculo(int id, String placa, String cor, char status, Modelo modelo, Funcionario funcionario) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.status = status;
        this.modelo = modelo;
        this.funcionario = funcionario;
    }

    public Veiculo(int id, String placa, String cor, char status, Modelo modelo, Fornecedor fornecedor) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.status = status;
        this.modelo = modelo;
        this.fornecedor = fornecedor;
    }

    public Veiculo(int id, String placa, String cor, char status, Modelo modelo, Hospede hospede) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.status = status;
        this.modelo = modelo;
        this.hospede = hospede;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Pessoa getProprietario() {
        if (funcionario instanceof Pessoa) {
            return funcionario;
        } else if (fornecedor instanceof Pessoa) {
            return fornecedor;
        } else if (hospede instanceof Pessoa) {
            return hospede;
        }
        return null;
    }

    public void setProprietario(Pessoa proprietario) {
        if (proprietario instanceof Funcionario) {
            this.funcionario = (Funcionario) proprietario;
            this.fornecedor = null;
            this.hospede = null;
        } else if (proprietario instanceof Fornecedor) {
            this.fornecedor = (Fornecedor) proprietario;
            this.funcionario = null;
            this.hospede = null;
        } else if (proprietario instanceof Hospede) {
            this.hospede = (Hospede) proprietario;
            this.funcionario = null;
            this.fornecedor = null;
        }
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    @Override
    public String toString() {
        return 
        "id       = " + id + 
        "\nplaca  = " + placa + 
        "\ncor    = " + cor + 
        "\nstatus = " + status + 
        "\nmodelo = " + modelo;
    }
    
}
