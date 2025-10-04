package controller;

public interface InterfaceControllerCad<T> {
    void initListeners();
    void handleNovo();
    void handleCancelar();
    void handleGravar();
    void handleBuscar();
    void handleSair();
    boolean isFormularioValido();
    T construirDoFormulario();
}
