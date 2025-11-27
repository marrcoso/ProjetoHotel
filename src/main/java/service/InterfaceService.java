package service;

import java.util.List;


public interface InterfaceService<T> {
    public abstract void Criar(T objeto) throws RuntimeException;
    public abstract T Carregar(int id) throws RuntimeException;
    public abstract List<T> Carregar(String atributo, String valor) throws RuntimeException;
    public abstract void Atualizar(T objeto) throws RuntimeException;
    public abstract void Apagar(T objeto) throws RuntimeException;
    public abstract void AtivarInativar(int id, boolean ativar) throws RuntimeException;
}
