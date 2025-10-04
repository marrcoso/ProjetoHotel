package controller;

public interface InterfaceControllerBusca <T>{
    void initListeners();
	void handleCarregar();
    void handleFiltrar();
    void handleSair();
    void adicionarLinhaTabela(javax.swing.table.DefaultTableModel tabela, T item);
}
