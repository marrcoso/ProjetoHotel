package model.DAO;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<T> {

    public abstract void Create(T objeto) throws SQLException;
    public abstract T Retrieve(int id) throws SQLException;
    public abstract List<T> Retrieve(String atributo, String valor) throws SQLException;
    public abstract void Update(T objeto) throws SQLException;
    public abstract void Delete(T objeto) throws SQLException;
    public abstract void AtivarInativar(int id, boolean ativar) throws SQLException;
}
