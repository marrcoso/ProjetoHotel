package model.DAO;

import java.util.List;

public interface InterfaceDAO<T> {

    public abstract void Create(T objeto) throws RuntimeException;
    public abstract T Retrieve(int id) throws RuntimeException;
    public abstract List<T> Retrieve(String atributo, String valor) throws RuntimeException;
    public abstract void Update(T objeto) throws RuntimeException;
    public abstract void Delete(T objeto) throws RuntimeException;
    public abstract void AtivarInativar(int id, boolean ativar) throws RuntimeException;
}
