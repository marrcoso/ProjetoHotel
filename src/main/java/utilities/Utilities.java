package utilities;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Utilities {
    
    public static void ativaDesativa(JPanel painel, boolean ativa){
        
        Component[] vetComponentes = painel.getComponents();
        for (Component componenteAtual : vetComponentes) {
            
            if(componenteAtual instanceof JButton){
                if(((JButton) componenteAtual).getActionCommand() == "0"){
                    componenteAtual.setEnabled(ativa);
                } else {
                    componenteAtual.setEnabled(!ativa);
                }
            }
        }
    }
    
    public static void limpaComponentes(JPanel painel, boolean ativa) {
        Component[] vetComponentes = painel.getComponents();
        for (Component componenteAtual : vetComponentes) {
            if (componenteAtual instanceof JTextField) {
                ((JTextField) componenteAtual).setText("");
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JFormattedTextField) {
                ((JFormattedTextField) componenteAtual).setText("");
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JComboBox) {
                ((JComboBox) componenteAtual).setSelectedIndex(-1);
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JCheckBox) {
                ((JCheckBox) componenteAtual).setSelected(false);
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JPasswordField) {
                ((JPasswordField) componenteAtual).setText("");
                componenteAtual.setEnabled(ativa);
            }
        }
    }

    public static String apenasNumeros(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    public static String formatarFone(String numero) {
        String apenasNumeros = apenasNumeros(numero);
        if (apenasNumeros.length() == 11) {
            return String.format("(%s) %s-%s",
                apenasNumeros.substring(0, 2),
                apenasNumeros.substring(2, 7),
                apenasNumeros.substring(7, 11)
            );
        }
        return numero;
    }

    public static String formatarCep(String numero) {
        String apenasNumeros = apenasNumeros(numero);
        if (apenasNumeros.length() == 8) {
            return String.format("%s-%s",
                apenasNumeros.substring(0, 5),
                apenasNumeros.substring(5, 8)
            );
        }
        return numero;
    }

    public static String formatarCpf(String numero) {
        String apenasNumeros = apenasNumeros(numero);
        if (apenasNumeros.length() == 11) {
            return String.format("%s.%s.%s-%s",
                apenasNumeros.substring(0, 3),
                apenasNumeros.substring(3, 6),
                apenasNumeros.substring(6, 9),
                apenasNumeros.substring(9, 11)
            );
        }
        return numero;
    }

    public static String formatarCnpj(String numero) {
        String apenasNumeros = apenasNumeros(numero);
        if (apenasNumeros.length() == 14) {
            return String.format("%s.%s.%s/%s-%s",
                apenasNumeros.substring(0, 2),
                apenasNumeros.substring(2, 5),
                apenasNumeros.substring(5, 8),
                apenasNumeros.substring(8, 12),
                apenasNumeros.substring(12, 14)
            );
        }
        return numero;
    }

    public static String getDataHoje() {
        java.time.LocalDate hoje = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return hoje.format(formatter);
    }

    public static String formatarDataFromSqlData(String data) {
        if (data != null && data.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] partes = data.split("-");
            return String.format("%s/%s/%s", partes[2], partes[1], partes[0]);
        }
        return data;
    }
}