package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import view.TelaBuscaFuncionario;

public class ControllerBuscaFuncionario implements ActionListener {

    TelaBuscaFuncionario telaBuscaFuncionario;

    public ControllerBuscaFuncionario(TelaBuscaFuncionario telaBuscaFuncionario) {
        this.telaBuscaFuncionario = telaBuscaFuncionario;
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaFuncionario.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaFuncionario.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaFuncionario.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaFuncionario.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaFuncionario.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadFuncionario.codigo = (int) telaBuscaFuncionario.getjTableDados()
                .getValueAt(telaBuscaFuncionario.getjTableDados().getSelectedRow(), 0);
            telaBuscaFuncionario.dispose();
        }
    }

    private enum FiltroFuncionario {
        ID, NOME, CPF;

        public static FiltroFuncionario fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CPF;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaFuncionario.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaFuncionario.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaFuncionario.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaFuncionario.getjTFFiltro().getText();

        FiltroFuncionario filtro = FiltroFuncionario.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                Funcionario funcionario = service.FuncionarioService.Carregar(Integer.parseInt(filtroTexto));
                if (funcionario != null) {
                    tabela.addRow(new Object[]{funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getStatus()});
                }
                break;
            case NOME:
                List<Funcionario> listaPorNome = service.FuncionarioService.Carregar("nome", filtroTexto);
                for (Funcionario f : listaPorNome) {
                    tabela.addRow(new Object[]{f.getId(), f.getNome(), f.getCpf(), f.getStatus()});
                }
                break;
            case CPF:
                List<Funcionario> listaPorCpf = service.FuncionarioService.Carregar("cpf", filtroTexto);
                for (Funcionario f : listaPorCpf) {
                    tabela.addRow(new Object[]{f.getId(), f.getNome(), f.getCpf(), f.getStatus()});
                }
                break;
        }
    }

    private void handleSair() {
        telaBuscaFuncionario.dispose();
    }
}
