package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Quarto;
import model.Reserva;
import model.ReservaQuarto;
import service.QuartoService;
import service.ReservaQuartoService;
import service.ReservaService;
import utilities.Utilities;
import utilities.ValidadorCampos;
import view.TelaBuscaQuarto;
import view.TelaBuscaReserva;
import view.TelaReserva;

public final class ControllerCadReserva implements ActionListener, InterfaceControllerCad<Reserva> {

    private final TelaReserva telaReserva;
    private final ReservaService reservaService;
    private final ReservaQuartoService reservaQuartoService;
    private final QuartoService quartoService;
    private final List<ReservaQuarto> quartosSelecionados;
    private Quarto quartoSelecionado;
    private int codigo;

    public ControllerCadReserva(TelaReserva telaReserva) {
        this.telaReserva = telaReserva;
        this.reservaService = new ReservaService();
        this.reservaQuartoService = new ReservaQuartoService();
        this.quartoService = new QuartoService();
        this.quartosSelecionados = new ArrayList<>();
        this.quartoSelecionado = null;

        Utilities.setAlwaysDisabled(this.telaReserva.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaReserva.getjComboBoxStatus(), true);
        Utilities.setAlwaysDisabled(this.telaReserva.getjFormattedTextFieldQuarto(), true);
        Utilities.setAlwaysDisabled(this.telaReserva.getjFormattedTextFieldDataReserva(), true);

        Utilities.ativaDesativa(this.telaReserva.getjPanelBotoes(), true);
        limparFormulario(false);
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaReserva.getjButtonNovo().addActionListener(this);
        this.telaReserva.getjButtonCancelar().addActionListener(this);
        this.telaReserva.getjButtonGravar().addActionListener(this);
        this.telaReserva.getjButtonBuscar().addActionListener(this);
        this.telaReserva.getjButtonSair().addActionListener(this);
        this.telaReserva.getjButtonRelacionarQuarto().addActionListener(this);
        this.telaReserva.getjButtonAlocarQuarto().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();

        if (source == telaReserva.getjButtonNovo()) {
            handleNovo();
        } else if (source == telaReserva.getjButtonCancelar()) {
            handleCancelar();
        } else if (source == telaReserva.getjButtonGravar()) {
            handleGravar();
        } else if (source == telaReserva.getjButtonBuscar()) {
            handleBuscar();
        } else if (source == telaReserva.getjButtonRelacionarQuarto()) {
            handleRelacionarQuarto();
        } else if (source == telaReserva.getjButtonAlocarQuarto()) {
            handleAlocarQuarto();
        } else if (source == telaReserva.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleNovo() {
        this.codigo = 0;
        this.quartosSelecionados.clear();
        this.quartoSelecionado = null;

        Utilities.ativaDesativa(this.telaReserva.getjPanelBotoes(), false);
        limparFormulario(true);
        preencherPadroesNovoCadastro();
        this.telaReserva.getjFormattedTextFieldDataEntrada().requestFocus();
    }

    @Override
    public void handleCancelar() {
        this.codigo = 0;
        this.quartosSelecionados.clear();
        this.quartoSelecionado = null;

        Utilities.ativaDesativa(this.telaReserva.getjPanelBotoes(), true);
        limparFormulario(false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!validarDataObrigatoria(this.telaReserva.getjFormattedTextFieldDataEntrada().getText(), "Previsão de Entrada")) {
            return false;
        }
        if (!validarDataObrigatoria(this.telaReserva.getjFormattedTextFieldDataSaida().getText(), "Previsão de Saída")) {
            return false;
        }

        String dataEntrada = Utilities.formatarDataToSqlData(this.telaReserva.getjFormattedTextFieldDataEntrada().getText());
        String dataSaida = Utilities.formatarDataToSqlData(this.telaReserva.getjFormattedTextFieldDataSaida().getText());

        if (!ValidadorCampos.compararDatas(dataEntrada, dataSaida)) {
            JOptionPane.showMessageDialog(this.telaReserva, "A data de saída não pode ser menor que a data de entrada.");
            return false;
        }

        if (this.quartosSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this.telaReserva, "Selecione ao menos um quarto para a reserva.");
            return false;
        }

        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }

        Reserva reserva = construirDoFormulario();
        boolean novoCadastro = this.telaReserva.getjTextFieldId().getText().trim().isEmpty();

        try {
            if (novoCadastro) {
                this.reservaService.Criar(reserva);
            } else {
                reserva.setId(Integer.parseInt(this.telaReserva.getjTextFieldId().getText()));
                this.reservaService.Atualizar(reserva);
            }
            this.reservaQuartoService.substituirReservaQuartos(reserva.getId(), this.quartosSelecionados);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaReserva, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(this.telaReserva.getjPanelBotoes(), true);
        limparFormulario(false);
        this.quartosSelecionados.clear();
        this.codigo = 0;
    }

    @Override
    public Reserva construirDoFormulario() {
        Reserva reserva = new Reserva();
        reserva.setDataHoraReserva(parseData(this.telaReserva.getjFormattedTextFieldDataReserva().getText()));
        reserva.setDataPrevistaEntrada(parseData(this.telaReserva.getjFormattedTextFieldDataEntrada().getText()));
        reserva.setDataPrevistaSaida(parseData(this.telaReserva.getjFormattedTextFieldDataSaida().getText()));
        reserva.setObs(this.telaReserva.getjTextFieldObs().getText().trim());
        reserva.setStatus("Ativo".equals(this.telaReserva.getjComboBoxStatus().getSelectedItem()) ? 'A' : 'I');
        return reserva;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaReserva telaBusca = new TelaBuscaReserva(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaReserva ControllerBuscaReserva = new ControllerBuscaReserva(telaBusca, valor -> this.codigo = valor, true);
        telaBusca.setVisible(true);

        if (codigo == 0) {
            return;
        }

        Reserva reserva;
        try {
            reserva = this.reservaService.Carregar(codigo);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaReserva, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (reserva == null) {
            JOptionPane.showMessageDialog(this.telaReserva, "Reserva não encontrada.");
            return;
        }

        Utilities.ativaDesativa(this.telaReserva.getjPanelBotoes(), false);
        limparFormulario(true);
        carregarReservaNaTela(reserva);
    }

    @Override
    public void handleSair() {
        this.telaReserva.dispose();
    }

    private void handleRelacionarQuarto() {
        final int[] codigoQuarto = {0};
        TelaBuscaQuarto telaBuscaQuarto = new TelaBuscaQuarto(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaQuarto controllerBuscaQuarto = new ControllerBuscaQuarto(telaBuscaQuarto, valor -> codigoQuarto[0] = valor);
        telaBuscaQuarto.setVisible(true);

        if (codigoQuarto[0] == 0) {
            return;
        }

        try {
            Quarto quarto = this.quartoService.Carregar(codigoQuarto[0]);
            this.quartoSelecionado = quarto;
            this.telaReserva.getjFormattedTextFieldQuarto().setText(quarto.getDescricao());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaReserva, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAlocarQuarto() {
        if (this.quartoSelecionado == null) {
            JOptionPane.showMessageDialog(this.telaReserva, "Selecione um quarto para alocar.");
            return;
        }

        for (ReservaQuarto selecionado : this.quartosSelecionados) {
            if (selecionado.getQuarto().getId() == this.quartoSelecionado.getId()) {
                JOptionPane.showMessageDialog(this.telaReserva, "Este quarto já foi adicionado.");
                return;
            }
        }

        ReservaQuarto rq = new ReservaQuarto();
        rq.setQuarto(this.quartoSelecionado);
        rq.setObs(this.telaReserva.getjTextFieldObsQuarto().getText().trim());
        rq.setStatus('A');

        String dataEntrada = this.telaReserva.getjFormattedTextFieldDataEntrada().getText();
        String dataSaida = this.telaReserva.getjFormattedTextFieldDataSaida().getText();
        String dataEntradaSql = Utilities.formatarDataToSqlData(dataEntrada);
        String dataSaidaSql = Utilities.formatarDataToSqlData(dataSaida);

        if (!ValidadorCampos.validarData(dataEntradaSql) || !ValidadorCampos.validarData(dataSaidaSql)) {
            JOptionPane.showMessageDialog(this.telaReserva, "As datas de entrada e saída devem ser preenchidas para alocar um quarto.");
            return;
        }

        if (!ValidadorCampos.compararDatas(dataEntradaSql, dataSaidaSql)) {
            JOptionPane.showMessageDialog(this.telaReserva, "A data de saída do quarto não pode ser menor que a data de entrada.");
            return;
        }

        rq.setDataHoraInicio(parseData(dataEntrada));
        rq.setDataHoraFim(parseData(dataSaida));

        this.quartosSelecionados.add(rq);
        DefaultTableModel tabela = (DefaultTableModel) this.telaReserva.getjTableQuartos().getModel();
        tabela.addRow(new Object[]{
            rq.getId() == 0 ? "" : rq.getId(),
            rq.getQuarto().getDescricao(),
            rq.getObs(),
            rq.getStatus()
        });

        this.quartoSelecionado = null;
        this.telaReserva.getjFormattedTextFieldQuarto().setText("");
        this.telaReserva.getjTextFieldObsQuarto().setText("");
    }

    private void carregarReservaNaTela(Reserva reserva) {
        this.telaReserva.getjTextFieldId().setText(String.valueOf(reserva.getId()));
        this.telaReserva.getjFormattedTextFieldDataReserva().setText(formatarData(reserva.getDataHoraReserva()));
        this.telaReserva.getjFormattedTextFieldDataEntrada().setText(formatarData(reserva.getDataPrevistaEntrada()));
        this.telaReserva.getjFormattedTextFieldDataSaida().setText(formatarData(reserva.getDataPrevistaSaida()));
        this.telaReserva.getjTextFieldObs().setText(reserva.getObs());
        this.telaReserva.getjComboBoxStatus().setSelectedItem(reserva.getStatus() == 'A' ? "Ativo" : "Inativo");

        carregarQuartosDaReserva(reserva.getId());
    }

    private void carregarQuartosDaReserva(int reservaId) {
        this.quartosSelecionados.clear();
        limparTabela(this.telaReserva.getjTableQuartos());

        try {
            List<ReservaQuarto> vinculos = this.reservaQuartoService.carregarPorReserva(reservaId);
            for (ReservaQuarto rq : vinculos) {
                this.quartosSelecionados.add(rq);
                DefaultTableModel tabela = (DefaultTableModel) this.telaReserva.getjTableQuartos().getModel();
                tabela.addRow(new Object[]{
                    rq.getId() == 0 ? "" : rq.getId(),
                    rq.getQuarto().getDescricao(),
                    rq.getObs(),
                    rq.getStatus()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaReserva, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario(boolean habilitarEdicao) {
        Utilities.limpaComponentes(this.telaReserva.getjPanelDados(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaReserva.getjPanelQuartos(), habilitarEdicao);
        limparTabela(this.telaReserva.getjTableQuartos());

        this.telaReserva.getjButtonAlocarQuarto().setEnabled(habilitarEdicao);
        this.telaReserva.getjButtonRelacionarQuarto().setEnabled(habilitarEdicao);

        this.telaReserva.getjTextFieldId().setText("");
        this.telaReserva.getjComboBoxStatus().setSelectedIndex(-1);
    }

    private void preencherPadroesNovoCadastro() {
        this.telaReserva.getjFormattedTextFieldDataReserva().setText(formatarData(new Date()));
        this.telaReserva.getjComboBoxStatus().setSelectedItem("Ativo");
    }

    private boolean validarDataObrigatoria(String data, String nomeCampo) {
        if (data == null || data.trim().equals("/  /") || data.trim().length() < 10) {
            JOptionPane.showMessageDialog(this.telaReserva, "O campo " + nomeCampo + " é obrigatório.");
            return false;
        }
        return true;
    }

    private Date parseData(String data) {
        try {
            if (data == null || data.trim().isEmpty() || data.contains(" ")) return null;
            return new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException e) {
            return null;
        }
    }

    private String formatarData(Date data) {
        if (data == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    private void limparTabela(javax.swing.JTable tabela) {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
    }
}
