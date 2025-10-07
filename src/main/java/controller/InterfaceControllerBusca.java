package controller;

import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public interface InterfaceControllerBusca <T>{
    void initListeners();
	void handleCarregar();
    void handleFiltrar();
    void handleSair();
    void handleAtivarInativar(boolean ativar);
    void adicionarLinhaTabela(DefaultTableModel tabela, T item);
    void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException;
}
