/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MarcusVinicius
 */
public class VagaEstacionamento {
    private int id;
    private String descricao;
    private String obs;
    private float metragemVaga;
    private char status;

    public VagaEstacionamento() {
    }
    
    public VagaEstacionamento(int id, String descricao, String obs, float metragemVaga, char status) {
        this.id = id;
        this.descricao = descricao;
        this.obs = obs;
        this.metragemVaga = metragemVaga;
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

    public float getMetragemVaga() {
        return metragemVaga;
    }

    public void setMetragemVaga(float metragemVaga) {
        this.metragemVaga = metragemVaga;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return 
        "id             = " + id + 
        "\ndescricao    = " + descricao + 
        "\nobs          = " + obs + 
        "\nmetragemVaga = " + metragemVaga + 
        "\nstatus       = " + status;
    }
    
    
    
    
}
