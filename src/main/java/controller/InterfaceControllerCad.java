package controller;

@SuppressWarnings("unused")
public interface InterfaceControllerCad<T> {
	private void initListeners() {}
    private void handleNovo() {}
    private void handleCancelar() {}
    private void handleGravar() {}
    private void handleBuscar() {}
    private void handleSair() {}
	private boolean isFormularioValido(){return true;}
    private T construirDoFormulario(){return null;}
}
