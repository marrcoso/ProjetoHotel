package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Veiculo;
import service.VeiculoService;
import view.TelaBuscaVeiculo;

public final class ControllerBuscaVeiculo implements ActionListener, InterfaceControllerBusca<Veiculo> {

    private final TelaBuscaVeiculo telaBuscaVeiculo;
    private final VeiculoService veiculoService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaVeiculo(TelaBuscaVeiculo telaBuscaVeiculo, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaVeiculo = telaBuscaVeiculo;
        this.veiculoService = new VeiculoService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaVeiculo.getjButtonCarregar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonFiltar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonSair().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonAtivar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonInativar().addActionListener(this);
        this.telaBuscaVeiculo.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaVeiculo.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaVeiculo.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaVeiculo.getjTableDados().getValueAt(row, 5); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaVeiculo.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaVeiculo.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaVeiculo.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaVeiculo.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaVeiculo.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaVeiculo.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaVeiculo.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaVeiculo.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaVeiculo.getjTableDados()
                .getValueAt(telaBuscaVeiculo.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaVeiculo.dispose();
        }
    }

    private enum FiltroVeiculo {
        ID, PLACA, COR, MODELO, HOSPEDE, FUNCIONARIO, FORNECEDOR;

        public static FiltroVeiculo fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return PLACA;
                case 2: return COR;
                case 3: return MODELO;
                case 4: return HOSPEDE;
                case 5: return FUNCIONARIO;
                case 6: return FORNECEDOR;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Veiculo veiculo) {
        tabela.addRow(new Object[]{
            veiculo.getId(),
            veiculo.getPlaca(),
            veiculo.getCor(),
            veiculo.getModelo().getId(),
            veiculo.getProprietario().getId() + " - " + veiculo.getProprietario().getNome(),
            veiculo.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<Veiculo> listaVeiculos = veiculoService.Carregar(atributo, valor);
        for (Veiculo v : listaVeiculos) {
            adicionarLinhaTabela(tabela, v);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaVeiculo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaVeiculo.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaVeiculo.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaVeiculo.getjTFFiltro().getText();

        FiltroVeiculo filtro = FiltroVeiculo.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Veiculo veiculo = veiculoService.Carregar(Integer.parseInt(filtroTexto));
                    if (veiculo != null) {
                        adicionarLinhaTabela(tabela, veiculo);
                    }
                    break;
                }
                case PLACA: {
                    carregarPorAtributo("placa", filtroTexto, tabela);
                    break;
                }
                case COR: {
                    carregarPorAtributo("cor", filtroTexto, tabela);
                    break;
                }
                case MODELO: {
                    carregarPorAtributo("modelo.id", filtroTexto, tabela);
                    break;
                }
                case HOSPEDE: {
                    carregarPorAtributo("hospede.id", filtroTexto, tabela);
                    break;
                }
                case FUNCIONARIO: {
                    carregarPorAtributo("funcionario.id", filtroTexto, tabela);
                    break;
                }
                case FORNECEDOR: {
                    carregarPorAtributo("fornecedor.id", filtroTexto, tabela);
                    break;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaVeiculo.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaVeiculo.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) telaBuscaVeiculo.getjTableDados()
            .getValueAt(telaBuscaVeiculo.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaVeiculo.getjTableDados()
            .getValueAt(telaBuscaVeiculo.getjTableDados().getSelectedRow(), 5);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("O Veículo já está %s.", ativar ? "Ativo" : "Inativo"));
                return;
            }

            veiculoService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaVeiculo.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaVeiculo.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 5);
            telaBuscaVeiculo.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaVeiculo.getjButtonInativar().setEnabled(ativar);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaVeiculo, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
