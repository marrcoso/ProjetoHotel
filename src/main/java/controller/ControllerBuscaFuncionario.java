package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import view.TelaBuscaFuncionario;

public class ControllerBuscaFuncionario implements ActionListener {

    TelaBuscaFuncionario telaBuscaFuncionario;

    public ControllerBuscaFuncionario(TelaBuscaFuncionario telaBuscaFuncionario) {

        this.telaBuscaFuncionario = telaBuscaFuncionario;

        this.telaBuscaFuncionario.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonSair().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaFuncionario.getjButtonCarregar()) {
            JOptionPane.showMessageDialog(null, "Botão Carregar Pressionado...");
            if (this.telaBuscaFuncionario.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados!");
            } else {
                ControllerCadFuncionario.codigo = (int) this.telaBuscaFuncionario.getjTableDados().
                        getValueAt(this.telaBuscaFuncionario.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaFuncionario.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaFuncionario.getjButtonFiltar()) {
            JOptionPane.showMessageDialog(null, "Botão Filtrar Pressionado...");
            if (this.telaBuscaFuncionario.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            } else {
                JOptionPane.showMessageDialog(null, "Filtrando informações...");
                if (this.telaBuscaFuncionario.getjCBFiltro().getSelectedIndex() == 0) {
                    // Criando objeto para receber o dado que virá do banco de dados
                    Funcionario funcionario = new Funcionario();

                    //Carregando o registro do funcionario na entidade para o objeto funcionario
                    funcionario = service.FuncionarioService.Carregar(Integer.parseInt(this.telaBuscaFuncionario.getjTFFiltro().getText()));

                    //Criando um objeto tabela do tipo defaulttablemodel e atribuindo o nosso modelo de tabela a ele
                    DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFuncionario.getjTableDados().getModel();
                    tabela.setRowCount(0);

                    //Adicionado o funcionario na tabela
                    tabela.addRow(new Object[]{funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getStatus()});

                } else if (this.telaBuscaFuncionario.getjCBFiltro().getSelectedIndex() == 1) {
                    //Criando a lista para receber os funcionarios
                    List<Funcionario> listaFuncionarios = new ArrayList<>();
                    //Carregando os funcionarios via sl para dentro da lista
                    listaFuncionarios = service.FuncionarioService.Carregar("nome", this.telaBuscaFuncionario.getjTFFiltro().getText());

                    //Criando um objeto tabela do tipo defaulttablemodel e atribuindo o nosso modelo de tabela a ele
                    DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFuncionario.getjTableDados().getModel();
                    tabela.setRowCount(0);

                    for (Funcionario funcionarioAtualDaLista : listaFuncionarios) {
                        //Adicionado o funcionario na tabela
                        tabela.addRow(new Object[]{funcionarioAtualDaLista.getId(),
                            funcionarioAtualDaLista.getNome(),
                            funcionarioAtualDaLista.getCpf(),
                            funcionarioAtualDaLista.getStatus()});
                    }
                } else if (this.telaBuscaFuncionario.getjCBFiltro().getSelectedIndex() == 2) {
                    //Criando a lista para receber os funcionarios
                    List<Funcionario> listaFuncionarios = new ArrayList<>();
                    //Carregando os funcionarios via sl para dentro da lista
                    listaFuncionarios = service.FuncionarioService.Carregar("cpf", this.telaBuscaFuncionario.getjTFFiltro().getText());

                    //Criando um objeto tabela do tipo defaulttablemodel e atribuindo o nosso modelo de tabela a ele
                    DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFuncionario.getjTableDados().getModel();
                    tabela.setRowCount(0);

                    for (Funcionario funcionarioAtualDaLista : listaFuncionarios) {
                        //Adicionado o funcionario na tabela
                        tabela.addRow(new Object[]{funcionarioAtualDaLista.getId(),
                            funcionarioAtualDaLista.getNome(),
                            funcionarioAtualDaLista.getCpf(),
                            funcionarioAtualDaLista.getStatus()});
                    }
                }
            }
        } else if (evento.getSource() == this.telaBuscaFuncionario.getjButtonSair()) {
            this.telaBuscaFuncionario.dispose();
        }
    }
}
