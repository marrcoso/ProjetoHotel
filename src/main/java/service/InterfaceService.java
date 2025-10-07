package service;

import java.sql.SQLException;
import java.util.List;


public interface InterfaceService<T> {
    public abstract void Criar(T objeto) throws SQLException;
    public abstract T Carregar(int id) throws SQLException;
    public abstract List<T> Carregar(String atributo, String valor) throws SQLException;
    public abstract void Atualizar(T objeto) throws SQLException;
    public abstract void Apagar(T objeto) throws SQLException;
    public abstract void AtivarInativar(int id, boolean ativar) throws SQLException;
}
