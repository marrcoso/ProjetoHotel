package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Hospede;
import service.HospedeService;
import view.TelaBuscaHospede;

public final class ControllerBuscaHospede implements ActionListener, InterfaceControllerBusca<Hospede> {

    private final TelaBuscaHospede telaBuscaHospede;
    private final HospedeService hospedeService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaHospede(TelaBuscaHospede telaBuscaHospede, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaHospede = telaBuscaHospede;
        this.hospedeService = new HospedeService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaHospede.getjButtonCarregar().addActionListener(this);
        this.telaBuscaHospede.getjButtonFiltar().addActionListener(this);
        this.telaBuscaHospede.getjButtonSair().addActionListener(this);
        this.telaBuscaHospede.getjButtonAtivar().addActionListener(this);
        this.telaBuscaHospede.getjButtonInativar().addActionListener(this);
        this.telaBuscaHospede.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaHospede.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaHospede.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaHospede.getjTableDados().getValueAt(row, 3); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaHospede.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaHospede.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaHospede.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaHospede.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaHospede.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaHospede.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaHospede.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaHospede.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaHospede.getjTableDados()
                .getValueAt(telaBuscaHospede.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaHospede.dispose();
        }
    }

    private enum FiltroHospede {
        ID, NOME, CPF, RAZAO_SOCIAL, CNPJ, OBSERVACAO, TELEFONE, EMAIL, CEP, CIDADE, BAIRRO, LOGRADOURO;

        public static FiltroHospede fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return NOME;
                case 2: return CPF;
                case 3: return RAZAO_SOCIAL;
                case 4: return CNPJ;
                case 5: return OBSERVACAO;
                case 6: return TELEFONE;
                case 7: return EMAIL;
                case 8: return CEP;
                case 9: return CIDADE;
                case 10: return BAIRRO;
                case 11: return LOGRADOURO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Hospede hospede) {
        tabela.addRow(new Object[]{
            hospede.getId(),
            hospede.getNome(),
            hospede.getCpf(),
            hospede.getStatus(),
            hospede.getRazaoSocial(),
            hospede.getCnpj(),
            hospede.getFone1(),
            hospede.getEmail(),
            hospede.getCidade(),
            hospede.getObs()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws SQLException {
        List<Hospede> listaHospedes = hospedeService.Carregar(atributo, valor);
        for (Hospede h : listaHospedes) {
            adicionarLinhaTabela(tabela, h);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaHospede.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaHospede.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaHospede.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaHospede.getjTFFiltro().getText();

        FiltroHospede filtro = FiltroHospede.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Hospede hospede = hospedeService.Carregar(Integer.parseInt(filtroTexto));
                    if (hospede != null) {
                        adicionarLinhaTabela(tabela, hospede);
                    }
                    break;
                }
                case NOME: {
                    carregarPorAtributo("nome", filtroTexto, tabela);
                    break;
                }
                case CPF: {
                    carregarPorAtributo("cpf", filtroTexto, tabela);
                    break;
                }
                case RAZAO_SOCIAL: {
                    carregarPorAtributo("razao_social", filtroTexto, tabela);
                    break;
                }
                case CNPJ: {
                    carregarPorAtributo("cnpj", filtroTexto, tabela);
                    break;
                }
                case OBSERVACAO: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
                case TELEFONE: {
                    carregarPorAtributo("fone", filtroTexto, tabela);
                    break;
                }
                case EMAIL: {
                    carregarPorAtributo("email", filtroTexto, tabela);
                    break;
                }
                case CEP: {
                    carregarPorAtributo("cep", filtroTexto, tabela);
                    break;
                }
                case CIDADE: {
                    carregarPorAtributo("cidade", filtroTexto, tabela);
                    break;
                }
                case BAIRRO: {
                    carregarPorAtributo("bairro", filtroTexto, tabela);
                    break;
                }
                case LOGRADOURO: {
                    carregarPorAtributo("logradouro", filtroTexto, tabela);
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaHospede, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaHospede.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaHospede.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaHospede.getjTableDados()
            .getValueAt(telaBuscaHospede.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaHospede.getjTableDados()
            .getValueAt(telaBuscaHospede.getjTableDados().getSelectedRow(), 3);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Hóspede já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            hospedeService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaHospede.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaHospede.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 3);
            telaBuscaHospede.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaHospede.getjButtonInativar().setEnabled(ativar);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaHospede, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
