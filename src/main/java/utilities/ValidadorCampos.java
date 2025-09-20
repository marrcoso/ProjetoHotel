package utilities;

public class ValidadorCampos {

    public static boolean validarCampoTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarCampoNumero(String numero) {
        return numero != null && numero.matches("\\d+");
    }

    public static boolean validarCampoEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,4}$");
    }

    public static boolean validarCampoUsuario(String usuario) {
        return usuario != null && usuario.matches("^[a-zA-Z0-9._]{5,20}$");
    }

    public static boolean validarSenha(char[] senha) {
        return senha != null && senha.length >= 6;
    }

    public static boolean validarStatus(String status) {
        return status != null && (status.equals("Ativo") || status.equals("Inativo"));
    }

    public static boolean validarSexo(String sexo) {
        return sexo != null && (sexo.equals("Masculino") || sexo.equals("Feminino"));
    }

    public static boolean validarCpf(String cpf) {
        String apenasNumeros = Utilities.apenasNumeros(cpf);
        return apenasNumeros.length() == 11;
    }

    public static boolean validarCnpj(String cnpj) {
        String apenasNumeros = Utilities.apenasNumeros(cnpj);
        return apenasNumeros.length() == 14;
    }

    public static boolean validarFone(String fone) {
        String apenasNumeros = Utilities.apenasNumeros(fone);
        return apenasNumeros.length() == 11;
    }

    public static boolean validarCep(String cep) {
        String apenasNumeros = Utilities.apenasNumeros(cep);
        return apenasNumeros.length() == 8;
    }
}
