package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Reserva;
import service.ReservaService;
import view.TelaBuscaReserva;

public final class ControllerBuscaReserva implements ActionListener, InterfaceControllerBusca<Reserva> {

    private final TelaBuscaReserva telaBuscaReserva;
    private final ReservaService reservaService;
    private final Consumer<Integer> atualizaCodigo;
    private final boolean apenasDisponiveis;

    public ControllerBuscaReserva(TelaBuscaReserva telaBuscaReserva, Consumer<Integer> atualizaCodigo) {
        this(telaBuscaReserva, atualizaCodigo, false);
    }

    public ControllerBuscaReserva(TelaBuscaReserva telaBuscaReserva, Consumer<Integer> atualizaCodigo, boolean apenasDisponiveis) {
        this.telaBuscaReserva = telaBuscaReserva;
        this.reservaService = new ReservaService();
        this.atualizaCodigo = atualizaCodigo;
        this.apenasDisponiveis = apenasDisponiveis;
        initListeners();
        if (this.apenasDisponiveis) {
            carregarReservasDisponiveis();
        }
    }

    @Override
    public void initListeners() {
        this.telaBuscaReserva.getjButtonCarregar().addActionListener(this);
        this.telaBuscaReserva.getjButtonFiltar().addActionListener(this);
        this.telaBuscaReserva.getjButtonSair().addActionListener(this);
        this.telaBuscaReserva.getjButtonAtivar().addActionListener(this);
        this.telaBuscaReserva.getjButtonInativar().addActionListener(this);
        this.telaBuscaReserva.getjTableDados().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && telaBuscaReserva.getjTableDados().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    private void handleSelecionarItem() {
        int row = telaBuscaReserva.getjTableDados().getSelectedRow();
        Object statusObj = telaBuscaReserva.getjTableDados().getValueAt(row, 5); // coluna status
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            telaBuscaReserva.getjButtonAtivar().setEnabled(status == 'I');
            telaBuscaReserva.getjButtonInativar().setEnabled(status == 'A');
        }
    }

    private void carregarReservasDisponiveis() {
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaReserva.getjTableDados().getModel();
        tabela.setRowCount(0);
        try {
            List<Reserva> reservas = reservaService.carregarReservasDisponiveis();
            for (Reserva r : reservas) {
                adicionarLinhaTabela(tabela, r);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar reservas disponíveis: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaReserva.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaReserva.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaReserva.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaBuscaReserva.getjButtonAtivar()) {
            handleAtivarInativar(true);
            return;
        }
        if (source == telaBuscaReserva.getjButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaReserva.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um registro para carregar!");
        } else {
            int codigo = (int) telaBuscaReserva.getjTableDados()
                .getValueAt(telaBuscaReserva.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaReserva.dispose();
        }
    }

    private enum FiltroReserva {
        ID, DATA_RESERVA, DATA_ENTRADA, DATA_SAIDA, OBS, STATUS;

        public static FiltroReserva fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DATA_RESERVA;
                case 2: return DATA_ENTRADA;
                case 3: return DATA_SAIDA;
                case 4: return OBS;
                case 5: return STATUS;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Reserva reserva) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tabela.addRow(new Object[]{
            reserva.getId(),
            sdf.format(reserva.getDataHoraReserva()),
            sdf.format(reserva.getDataPrevistaEntrada()),
            sdf.format(reserva.getDataPrevistaSaida()),
            reserva.getObs(),
            reserva.getStatus()
        });
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) throws RuntimeException {
        List<Reserva> listaReservas = reservaService.Carregar(atributo, valor);
        for (Reserva r : listaReservas) {
            adicionarLinhaTabela(tabela, r);
        }
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaReserva.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaReserva.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaReserva.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaReserva.getjTFFiltro().getText();

        FiltroReserva filtro = FiltroReserva.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Reserva reserva = reservaService.Carregar(Integer.parseInt(filtroTexto));
                    if (reserva != null) {
                        adicionarLinhaTabela(tabela, reserva);
                    }
                    break;
                }
                case DATA_RESERVA: {
                    carregarPorAtributo("dataHoraReserva", filtroTexto, tabela);
                    break;
                }
                case DATA_ENTRADA: {
                    carregarPorAtributo("dataPrevistaEntrada", filtroTexto, tabela);
                    break;
                }
                case DATA_SAIDA: {
                    carregarPorAtributo("dataPrevistaSaida", filtroTexto, tabela);
                    break;
                }
                case OBS: {
                    carregarPorAtributo("obs", filtroTexto, tabela);
                    break;
                }
                case STATUS: {
                    carregarPorAtributo("status", filtroTexto, tabela);
                    break;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaReserva, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        telaBuscaReserva.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (telaBuscaReserva.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um registro!");
            return;
        }

        int codigo = (int) telaBuscaReserva.getjTableDados()
            .getValueAt(telaBuscaReserva.getjTableDados().getSelectedRow(), 0);

        char statusAtual = (char) telaBuscaReserva.getjTableDados()
            .getValueAt(telaBuscaReserva.getjTableDados().getSelectedRow(), 5);

        try {
            if (statusAtual == (ativar ? 'A' : 'I')) {
                JOptionPane.showMessageDialog(null, String.format("A Reserva já está %s.", ativar ? "Ativa" : "Inativa"));
                return;
            }

            reservaService.AtivarInativar(codigo, ativar);
            int selectedRow = telaBuscaReserva.getjTableDados().getSelectedRow();
            DefaultTableModel tabela = (DefaultTableModel) telaBuscaReserva.getjTableDados().getModel();
            tabela.setValueAt(ativar ? 'A' : 'I', selectedRow, 5);
            telaBuscaReserva.getjButtonAtivar().setEnabled(!ativar);
            telaBuscaReserva.getjButtonInativar().setEnabled(ativar);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaBuscaReserva, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
