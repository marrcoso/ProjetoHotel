package utilities;

import java.util.Map;

public class ValidadorCampos {

    public static boolean validarCampoTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarCampoNumero(String numero) {
        return numero != null && numero.matches("\\d+");
    }

    public static boolean validarCampoValor(String valor) {
        return valor != null && valor.matches("\\d+(\\.\\d+)?");
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

    public static boolean validarStatus(Object status) {
        return status != null && (status.toString().equals("Ativo") || status.toString().equals("Inativo"));
    }

    public static boolean validarSexo(Object sexo) {
        return sexo != null && (sexo.toString().equals("Masculino") || sexo.toString().equals("Feminino"));
    }

    public static boolean validarData(String data) {
        String padraoData = "^\\d{4}-\\d{2}-\\d{2}$";

        if (data != null) {
            if (data.matches(padraoData)) {
                String[] partes = data.split("-");
                int ano = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int dia = Integer.parseInt(partes[2]);
                Map<Integer, Integer> diasPorMes = new java.util.HashMap<>();
                diasPorMes.put(1, 31);
                diasPorMes.put(2, 28);
                diasPorMes.put(3, 31);
                diasPorMes.put(4, 30);
                diasPorMes.put(5, 31);
                diasPorMes.put(6, 30);
                diasPorMes.put(7, 31);
                diasPorMes.put(8, 31);
                diasPorMes.put(9, 30);
                diasPorMes.put(10, 31);
                diasPorMes.put(11, 30);
                diasPorMes.put(12, 31);

                Integer diasNoMes = diasPorMes.get(mes);
                if (diasNoMes == null || dia < 1) {
                    return false;
                }

                if (mes == 2) {
                    boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
                    diasNoMes = bissexto ? 29 : 28;
                }

                return dia <= diasNoMes;
            }
            return false;
        }
        return false;
    }

    public static boolean compararDatas(String data1, String data2) {
        return data1 != null && data2 != null && data1.compareTo(data2) <= 0;
    }

    public static boolean validarCpf(String cpf) {
        String apenasNumeros = Utilities.apenasNumeros(cpf);
        if (apenasNumeros.length() != 11 || apenasNumeros.matches("(\\d)\\1{10}")) return false;
    
        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(apenasNumeros.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }
    
        int dv1 = soma1 % 11;
        dv1 = (dv1 < 2) ? 0 : 11 - dv1;
        soma2 += dv1 * 2;
        int dv2 = soma2 % 11;
        dv2 = (dv2 < 2) ? 0 : 11 - dv2;
    
        return dv1 == Character.getNumericValue(apenasNumeros.charAt(9)) &&
               dv2 == Character.getNumericValue(apenasNumeros.charAt(10));
    }

    public static boolean validarCnpj(String cnpj) {
        String apenasNumeros = Utilities.apenasNumeros(cnpj);
        if (apenasNumeros.length() != 14 || apenasNumeros.matches("(\\d)\\1{13}")) return false;
    
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    
        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(apenasNumeros.charAt(i));
            soma1 += digito * pesos1[i];
            soma2 += digito * pesos2[i];
        }
    
        int dv1 = soma1 % 11;
        dv1 = (dv1 < 2) ? 0 : 11 - dv1;
        soma2 += dv1 * pesos2[12];
        int dv2 = soma2 % 11;
        dv2 = (dv2 < 2) ? 0 : 11 - dv2;
    
        return dv1 == Character.getNumericValue(apenasNumeros.charAt(12)) &&
               dv2 == Character.getNumericValue(apenasNumeros.charAt(13));
    }

    public static boolean validarFone(String fone) {
        String apenasNumeros = Utilities.apenasNumeros(fone);
        return apenasNumeros.length() == 11;
    }

    public static boolean validarCep(String cep) {
        String apenasNumeros = Utilities.apenasNumeros(cep);
        return apenasNumeros.length() == 8;
    }

    public static boolean validarPlaca(String placa) {
        return validarCampoTexto(placa) && placa.length() == 7;
    }
}
