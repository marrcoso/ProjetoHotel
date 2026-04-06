package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.AlocacaoVaga;
import model.Check;
import model.CheckHospede;
import model.CheckQuarto;
import model.Hospede;
import model.Quarto;
import model.Receber;
import model.VagaEstacionamento;
import model.Veiculo;
import service.AlocacaoVagaService;
import service.CheckHospedeService;
import service.CheckQuartoService;
import service.CheckService;
import service.HospedeService;
import service.QuartoService;
import service.ReceberService;
import service.VagaEstacionamentoService;
import service.VeiculoService;
import utilities.Utilities;
import utilities.ValidadorCampos;
import view.TelaBuscaCheck;
import view.TelaBuscaHospede;
import view.TelaBuscaQuarto;
import view.TelaBuscaVaga;
import view.TelaBuscaVeiculo;
import view.TelaBuscaReserva;
import view.TelaCheck;
import service.ReservaService;
import model.Reserva;

public final class ControllerCadCheck implements ActionListener, InterfaceControllerCad<Check> {

    private final TelaCheck telaCheck;
    private final CheckService checkService;
    private final CheckHospedeService checkHospedeService;
    private final CheckQuartoService checkQuartoService;
    private final AlocacaoVagaService alocacaoVagaService;
    private final HospedeService hospedeService;
    private final QuartoService quartoService;
    private final VagaEstacionamentoService vagaService;
    private final VeiculoService veiculoService;
    private final ReceberService receberService;
    private final List<CheckHospede> hospedesSelecionados;
    private final List<CheckQuarto> quartosSelecionados;
    private final List<AlocacaoVaga> alocacoesVagasSelecionadas;
    private Veiculo veiculoSelecionado;
    private VagaEstacionamento vagaSelecionada;
    private Hospede hospedeSelecionado;
    private Quarto QuartoSelecionado;
    private final ReservaService reservaService;
    private Reserva reservaSelecionada;
    private int codigo;

    public ControllerCadCheck(TelaCheck telaCheck) {
        this.telaCheck = telaCheck;
        this.checkService = new CheckService();
        this.checkHospedeService = new CheckHospedeService();
        this.checkQuartoService = new CheckQuartoService();
        this.alocacaoVagaService = new AlocacaoVagaService();
        this.hospedeService = new HospedeService();
        this.quartoService = new QuartoService();
        this.vagaService = new VagaEstacionamentoService();
        this.veiculoService = new VeiculoService();
        this.receberService = new ReceberService();
        this.reservaService = new ReservaService();
        this.hospedesSelecionados = new ArrayList<>();
        this.quartosSelecionados = new ArrayList<>();
        this.alocacoesVagasSelecionadas = new ArrayList<>();
        this.veiculoSelecionado = null;
        this.vagaSelecionada = null;
        this.hospedeSelecionado = null;
        this.QuartoSelecionado = null;

        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjComboBoxStatus(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldVaga(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldVeiculo(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjComboBoxStatusRecebimento(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldValorPagar(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjFormattedTextFieldHospede(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjFormattedTextFieldQuarto(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjFormattedTextFieldReserva(), true);
        
        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), true);
        limparFormulario(false);
        configurarCalculoRecebimento();
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaCheck.getjButtonNovo().addActionListener(this);
        this.telaCheck.getjButtonCancelar().addActionListener(this);
        this.telaCheck.getjButtonGravar().addActionListener(this);
        this.telaCheck.getjButtonBuscar().addActionListener(this);
        this.telaCheck.getjButtonSair().addActionListener(this);
        this.telaCheck.getjButtonRelacionarHospede().addActionListener(this);
        this.telaCheck.getjButtonAlocarHospede().addActionListener(this);
        this.telaCheck.getjButtonRelacionarQuarto().addActionListener(this);
        this.telaCheck.getjButtonAlocarQuarto().addActionListener(this);
        this.telaCheck.getjButtonAlocarVaga().addActionListener(this);
        this.telaCheck.getjButtonRelacionarVeiculo().addActionListener(this);   
        this.telaCheck.getjButtonRelacionarVaga().addActionListener(this);
        this.telaCheck.getjButtonRelacionarReserva().addActionListener(this);
        // Adicionar botão para remover vaga se existir
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();

        if (source == telaCheck.getjButtonNovo()) {
            handleNovo();
            return;
        }
        if (source == telaCheck.getjButtonCancelar()) {
            handleCancelar();
            return;
        }
        if (source == telaCheck.getjButtonGravar()) {
            handleGravar();
            return;
        }
        if (source == telaCheck.getjButtonBuscar()) {
            handleBuscar();
            return;
        }
        if (source == telaCheck.getjButtonRelacionarHospede()) {
            handleRelacionarHospede();
            return;
        }
        if (source == telaCheck.getjButtonAlocarHospede()) {
            handleAlocarHospede();
            return;
        }
        if (source == telaCheck.getjButtonRelacionarQuarto()) {
            handleRelacionarQuarto();
            return;
         }
        if (source == telaCheck.getjButtonAlocarQuarto()) {
            handleAlocarQuarto();
            return;
        }
        if (source == telaCheck.getjButtonAlocarVaga()) {
            handleAlocarVaga();
            return;
        }
        if (source == telaCheck.getjButtonRelacionarVaga()) {
            handleRelacionarVaga();
            return;
        }
        if (source == telaCheck.getjButtonRelacionarVeiculo()) {
            handleRelacionarVeiculo();
            return;
        }
        if (source == telaCheck.getjButtonSair()) {
            handleSair();
            return;
        }
        if (source == telaCheck.getjButtonRelacionarReserva()) {
            handleRelacionarReserva();
        }
    }

    @Override
    public void handleNovo() {
        this.codigo = 0;
        this.hospedesSelecionados.clear();
        this.quartosSelecionados.clear();
        this.alocacoesVagasSelecionadas.clear();
        this.hospedeSelecionado = null;
        this.QuartoSelecionado = null;
        this.veiculoSelecionado = null;
        this.vagaSelecionada = null;
        this.reservaSelecionada = null;

        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), false);
        limparFormulario(true);
        preencherPadroesNovoCadastro();
        this.telaCheck.getjFormattedTextFieldDataEntrada().requestFocus();
    }

    @Override
    public void handleCancelar() {
        this.codigo = 0;
        this.hospedesSelecionados.clear();
        this.quartosSelecionados.clear();
        this.alocacoesVagasSelecionadas.clear();
        this.hospedeSelecionado = null;
        this.QuartoSelecionado = null;
        this.veiculoSelecionado = null;
        this.vagaSelecionada = null;
        this.reservaSelecionada = null;

        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), true);
        limparFormulario(false);
    }

    @Override
    public boolean isFormularioValido() {
        if (!ValidadorCampos.validarStatus(this.telaCheck.getjComboBoxStatus().getSelectedItem())) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione um status válido para o check.");
            return false;
        }

        if (!validarDataObrigatoria(this.telaCheck.getjFormattedTextFieldDataCadastro().getText(), "Data de Cadastro")) {
            this.telaCheck.getjFormattedTextFieldDataCadastro().requestFocus();
            return false;
        }
        if (!validarDataObrigatoria(this.telaCheck.getjFormattedTextFieldDataEntrada().getText(), "Data de Entrada")) {
            this.telaCheck.getjFormattedTextFieldDataEntrada().requestFocus();
            return false;
        }

        String dataCadastro = Utilities.formatarDataToSqlData(this.telaCheck.getjFormattedTextFieldDataCadastro().getText());
        String dataEntrada = Utilities.formatarDataToSqlData(this.telaCheck.getjFormattedTextFieldDataEntrada().getText());
        String dataSaida = Utilities.formatarDataToSqlData(this.telaCheck.getjFormattedTextFieldDataSaida().getText());

        if (!ValidadorCampos.compararDatas(dataCadastro, dataEntrada)) {
            JOptionPane.showMessageDialog(this.telaCheck, "A data de cadastro não pode ser maior que a data de entrada.");
            this.telaCheck.getjFormattedTextFieldDataEntrada().requestFocus();
            return false;
        }

        if (this.telaCheck.getjFormattedTextFieldDataSaida().getText().trim().length() > 0) {
            if (!ValidadorCampos.compararDatas(dataEntrada, dataSaida)) {
                JOptionPane.showMessageDialog(this.telaCheck, "A data de saída não pode ser menor que a data de entrada.");
                this.telaCheck.getjFormattedTextFieldDataSaida().requestFocus();
                return false;
            }
        }

        if (this.hospedesSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione ao menos um hóspede para o check.");
            return false;
        }
        if (this.quartosSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione ao menos um quarto para o check.");
            return false;
        }
        if (this.alocacoesVagasSelecionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione ao menos uma vaga e veículo para o check.");
            return false;
        }

        try {
            float valorOriginal = lerValorMonetario(this.telaCheck.getjTextFieldValorOriginal().getText());
            float desconto = lerValorMonetario(this.telaCheck.getjTextFieldDesconto().getText());
            float acrescimo = lerValorMonetario(this.telaCheck.getjTextFieldAcrescimo().getText());
            float valorPago = lerValorMonetario(this.telaCheck.getjTextFieldValorPago().getText());

            if (valorOriginal < 0 || desconto < 0 || acrescimo < 0 || valorPago < 0) {
                JOptionPane.showMessageDialog(this.telaCheck, "Os valores monetários não podem ser negativos.");
                return false;
            }

            float valorPagar = this.checkService.calcularValorPagar(valorOriginal, desconto, acrescimo);
            if (valorPagar < 0) {
                JOptionPane.showMessageDialog(this.telaCheck, "O valor a pagar não pode ser negativo.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, "Os valores do recebimento devem ser numéricos.");
            return false;
        }

        return true;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }

        Check check = construirDoFormulario();
        boolean novoCadastro = this.telaCheck.getjTextFieldId().getText().trim().isEmpty();

        try {
            if (novoCadastro) {
                this.checkService.Criar(check);
            } else {
                check.setId(Integer.parseInt(this.telaCheck.getjTextFieldId().getText()));
                this.checkService.Atualizar(check);
            }
            this.checkHospedeService.substituirCheckHospedes(check.getId(), this.hospedesSelecionados);
            this.alocacaoVagaService.substituirAlocacoesDoCheck(check.getId(), this.alocacoesVagasSelecionadas);
            this.checkQuartoService.substituirCheckQuartos(check.getId(), this.quartosSelecionados);

            Receber receber = construirReceberDoFormulario(check);
            Receber existing = this.receberService.CarregarPorCheck(check.getId());
            if (existing != null) {
                receber.setId(existing.getId());
                this.receberService.Atualizar(receber);
            } else {
                this.receberService.Criar(receber);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), true);
        limparFormulario(false);
        this.hospedesSelecionados.clear();
        this.quartosSelecionados.clear();
        this.alocacoesVagasSelecionadas.clear();
        this.hospedeSelecionado = null;
        this.QuartoSelecionado = null;
        this.veiculoSelecionado = null;
        this.vagaSelecionada = null;
        this.reservaSelecionada = null;
        this.codigo = 0;
    }

    private Receber construirReceberDoFormulario(Check check) {
        Receber receber = new Receber();
        receber.setCheck(check);
        receber.setDataHoraCadastro(new Date());
        receber.setValorOriginal(lerValorMonetario(this.telaCheck.getjTextFieldValorOriginal().getText()));
        receber.setDesconto(lerValorMonetario(this.telaCheck.getjTextFieldDesconto().getText()));
        receber.setAcrescimo(lerValorMonetario(this.telaCheck.getjTextFieldAcrescimo().getText()));
        receber.setValorPago(lerValorMonetario(this.telaCheck.getjTextFieldValorPago().getText()));
        receber.setObs(this.telaCheck.getjTextFieldObsRecebimento().getText().trim());
        receber.setStatus('A');
        return receber;
    }

    @Override
    public Check construirDoFormulario() {
        Check check = new Check();
        check.setDataHoraCadastro(parseData(this.telaCheck.getjFormattedTextFieldDataCadastro().getText()));
        check.setDataHoraEntrada(parseData(this.telaCheck.getjFormattedTextFieldDataEntrada().getText()));
        check.setDataHoraSaida(parseData(this.telaCheck.getjFormattedTextFieldDataSaida().getText()));
        check.setObs(this.telaCheck.getjTextFieldObs().getText().trim());
        check.setStatus("Ativo".equals(this.telaCheck.getjComboBoxStatus().getSelectedItem()) ? 'A' : 'I');
        check.setReserva(this.reservaSelecionada);
        return check;
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        TelaBuscaCheck telaBuscaCheck = new TelaBuscaCheck(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaCheck ControllerBuscaCheck = new ControllerBuscaCheck(telaBuscaCheck, valor -> this.codigo = valor, true);
        telaBuscaCheck.setVisible(true);

        if (codigo == 0) {
            return;
        }

        Check check;
        try {
            check = this.checkService.Carregar(codigo);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (check == null) {
            JOptionPane.showMessageDialog(this.telaCheck, "Check não encontrado.");
            return;
        }

        this.codigo = check.getId();
        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), false);
        limparFormulario(true);
        carregarCheckNaTela(check);
    }

    @Override
    public void handleSair() {
        this.telaCheck.dispose();
    }

    private void handleRelacionarHospede() {
        final int[] codigoHospede = {0};
        TelaBuscaHospede telaBuscaHospede = new TelaBuscaHospede(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaHospede ControllerBuscaHospede = new ControllerBuscaHospede(telaBuscaHospede, valor -> codigoHospede[0] = valor, true);
        telaBuscaHospede.setVisible(true);

        if (codigoHospede[0] == 0) {
            return;
        }

        try {
            Hospede hospede = this.hospedeService.Carregar(codigoHospede[0]);
            adicionarHospede(hospede);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAlocarHospede() {
        if (this.hospedeSelecionado == null || this.telaCheck.getjComboBoxTipoHospede().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione um hóspede e um tipo para alocar.");
            return;
        }

        Object tipoSelecionado = this.telaCheck.getjComboBoxTipoHospede().getSelectedItem();

        if (this.hospedesSelecionados.isEmpty() && !tipoSelecionado.toString().equals("Titular")) {
            JOptionPane.showMessageDialog(this.telaCheck, "O primeiro hóspede deve ser selecionado como Titular.");
            return;
        }

        try {
            adicionarAlocacaoHospede(this.hospedeSelecionado);
            this.hospedeSelecionado = null;
            this.telaCheck.getjComboBoxTipoHospede().setSelectedIndex(-1);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRelacionarQuarto() {
        final int[] codigoQuarto = {0};
        TelaBuscaQuarto telaBuscaQuarto = new TelaBuscaQuarto(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaQuarto ControllerBuscaQuarto = new ControllerBuscaQuarto(telaBuscaQuarto, valor -> codigoQuarto[0] = valor, true);
        telaBuscaQuarto.setVisible(true);

        if (codigoQuarto[0] == 0) {
            return;
        }

        try {
            Quarto quarto = this.quartoService.Carregar(codigoQuarto[0]);
            adicionarQuarto(quarto);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleAlocarQuarto() {
        if (this.QuartoSelecionado == null) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione um quarto para alocar.");
            return;
        }

        try {
            adicionarAlocacaoQuarto(this.QuartoSelecionado);
            this.QuartoSelecionado = null;
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAlocarVaga() {

        if (vagaSelecionada == null || veiculoSelecionado == null) {
            JOptionPane.showMessageDialog(this.telaCheck, "Selecione uma vaga e um veículo para alocar.");
            return;
        }

        try {
            adicionarAlocacaoVaga(vagaSelecionada, veiculoSelecionado);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRelacionarVeiculo() {
        final int[] codigoVeiculo = {0};
        TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaVeiculo ControllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo, valor -> codigoVeiculo[0] = valor, true);
        telaBuscaVeiculo.setVisible(true);

        if (codigoVeiculo[0] == 0) {
            return;
        }

        try {
            Veiculo veiculo = this.veiculoService.Carregar(codigoVeiculo[0]);
            adicionarVeiculo(veiculo);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRelacionarVaga() {
        final int[] codigoVaga = {0};
        TelaBuscaVaga telaBuscaVaga = new TelaBuscaVaga(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaVagaEstacionamento ControllerBuscaVagaEstacionamento = new ControllerBuscaVagaEstacionamento(telaBuscaVaga, valor -> codigoVaga[0] = valor, true);
        telaBuscaVaga.setVisible(true);

        if (codigoVaga[0] == 0) {
            return;
        }

        try {
            VagaEstacionamento vaga = this.vagaService.Carregar(codigoVaga[0]);
            adicionarVaga(vaga);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarHospede(Hospede hospede) {
        if (hospede == null) {
            return;
        }
        this.hospedeSelecionado = hospede;
        this.telaCheck.getjFormattedTextFieldHospede().setText(hospede.getNome());
    }

    private void adicionarQuarto(Quarto quarto) {
        if (quarto == null) {
            return;
        }
        this.QuartoSelecionado = quarto;
        this.telaCheck.getjFormattedTextFieldQuarto().setText(quarto.getDescricao());
    }

    private void adicionarVaga(VagaEstacionamento vaga) {
        if (vaga == null) {
            return;
        }
        this.vagaSelecionada = vaga;
        this.telaCheck.getjTextFieldVaga().setText(vaga.getDescricao());
    }

    private void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            return;
        }
        this.veiculoSelecionado = veiculo;
        this.telaCheck.getjTextFieldVeiculo().setText(veiculo.getPlaca());
    }

    private void adicionarAlocacaoVaga(VagaEstacionamento vaga, model.Veiculo veiculo) {
        if (vaga == null || veiculo == null) {
            return;
        }
        for (model.AlocacaoVaga aloc : this.alocacoesVagasSelecionadas) {
            if (aloc.getVagaEstacionamento().getId() == vaga.getId() && aloc.getVeiculo().getId() == veiculo.getId()) {
                JOptionPane.showMessageDialog(this.telaCheck, "Esta vaga e veículo já foram adicionados ao check.");
                return;
            }
        }

        String obs = this.telaCheck.getjTextFieldObsVaga().getText().trim();
        model.AlocacaoVaga alocacao = new model.AlocacaoVaga();
        alocacao.setVagaEstacionamento(vaga);
        alocacao.setVeiculo(veiculo);
        alocacao.setStatus('A');
        alocacao.setObs(obs);
        this.alocacoesVagasSelecionadas.add(alocacao);
        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableVaga().getModel();
        tabela.addRow(new Object[]{
            alocacao.getId() == 0 ? "" : alocacao.getId(),
            alocacao.getVagaEstacionamento().getDescricao(),
            alocacao.getVeiculo().getPlaca(),
            alocacao.getObs(),
            alocacao.getStatus(),
        });
        this.vagaSelecionada = null;
        this.veiculoSelecionado = null;
        this.telaCheck.getjTextFieldVaga().setText("");
        this.telaCheck.getjTextFieldVeiculo().setText("");
        this.telaCheck.getjTextFieldObsVaga().setText("");
    }

    private void adicionarAlocacaoHospede(Hospede hospede) {
        if (hospede == null) {
            return;
        }

        for (CheckHospede selecionado : this.hospedesSelecionados) {
            if (selecionado.getHospede().getId() == hospede.getId()) {
                JOptionPane.showMessageDialog(this.telaCheck, "Este hóspede já foi adicionado ao check.");
                return;
            }
        }

        String tipoSelecionado = this.telaCheck.getjComboBoxTipoHospede().getSelectedItem().toString();
        if ("Titular".equals(tipoSelecionado)) {
            boolean jaTemTitular = this.hospedesSelecionados.stream()
                .anyMatch(ch -> "Titular".equals(ch.getTipoHospede()));
            if (jaTemTitular) {
                JOptionPane.showMessageDialog(this.telaCheck, "Só é permitido um hóspede titular por check-in.");
                return;
            }
        }

        CheckHospede checkHospede = new CheckHospede();
        checkHospede.setHospede(hospede);
        checkHospede.setTipoHospede(this.telaCheck.getjComboBoxTipoHospede().getSelectedItem().toString());
        checkHospede.setObs(this.telaCheck.getjTextFieldObsHospede().getText().trim());
        checkHospede.setStatus('A');
        this.hospedesSelecionados.add(checkHospede);
        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableHospedes().getModel();
        tabela.addRow(new Object[]{
            checkHospede.getId() == 0 ? "" : checkHospede.getId(),
            checkHospede.getHospede().getNome(),
            checkHospede.getTipoHospede(),
            checkHospede.getObs(),
            checkHospede.getHospede().getStatus()
        });
        this.telaCheck.getjFormattedTextFieldHospede().setText("");
        this.telaCheck.getjComboBoxTipoHospede().setSelectedIndex(-1);
        this.telaCheck.getjTextFieldObsHospede().setText("");
        this.hospedeSelecionado = null;
    }

    private void adicionarAlocacaoQuarto(Quarto quarto) {
        if (quarto == null) {
            return;
        }

        for (CheckQuarto selecionado : this.quartosSelecionados) {
            if (selecionado.getQuarto().getId() == quarto.getId()) {
                JOptionPane.showMessageDialog(this.telaCheck, "Este quarto já foi adicionado ao check.");
                return;
            }
        }

        CheckQuarto checkQuarto = new CheckQuarto();
        checkQuarto.setQuarto(quarto);
        checkQuarto.setObs(this.telaCheck.getjTextFieldObsQuarto().getText().trim());
        checkQuarto.setStatus('A');


        String dataEntrada = this.telaCheck.getjFormattedTextFieldDataEntrada().getText();
        String dataSaida = this.telaCheck.getjFormattedTextFieldDataSaida().getText();
        String dataEntradaSql = Utilities.formatarDataToSqlData(dataEntrada);
        String dataSaidaSql = Utilities.formatarDataToSqlData(dataSaida);

        if (!ValidadorCampos.validarData(dataEntradaSql) || !ValidadorCampos.validarData(dataSaidaSql)) {
            JOptionPane.showMessageDialog(this.telaCheck, "As datas de entrada e saída devem ser preenchidas para alocar um quarto.");
            return;
        }

        if (!ValidadorCampos.compararDatas(dataEntradaSql, dataSaidaSql)) {
            JOptionPane.showMessageDialog(this.telaCheck, "A data de saída do quarto não pode ser menor que a data de entrada.");
            return;
        }

        checkQuarto.setDataHoraInicio(parseData(dataEntrada));
        checkQuarto.setDataHoraFim(parseData(dataSaida));
        this.quartosSelecionados.add(checkQuarto);
        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableQuartos().getModel();
        tabela.addRow(new Object[]{
            checkQuarto.getId() == 0 ? "" : checkQuarto.getId(),
            checkQuarto.getQuarto().getDescricao(),
            checkQuarto.getObs(),
            checkQuarto.getStatus()
        });
        this.telaCheck.getjFormattedTextFieldQuarto().setText("");
        this.telaCheck.getjTextFieldObsQuarto().setText("");
        this.QuartoSelecionado = null;
    }

    private void handleRelacionarReserva() {
        final int[] codigoReserva = {0};
        TelaBuscaReserva telaBuscaReserva = new TelaBuscaReserva(null, true);
        @SuppressWarnings("unused")
        ControllerBuscaReserva controllerBuscaReserva = new ControllerBuscaReserva(telaBuscaReserva, valor -> codigoReserva[0] = valor, true);
        telaBuscaReserva.setVisible(true);

        if (codigoReserva[0] == 0) {
            return;
        }

        try {
            Reserva reserva = this.reservaService.Carregar(codigoReserva[0]);
            adicionarReserva(reserva);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarReserva(Reserva reserva) {
        if (reserva == null) {
            return;
        }
        this.reservaSelecionada = reserva;
        this.telaCheck.getjFormattedTextFieldReserva().setText(String.valueOf(reserva.getId()));
    }

    private void carregarCheckNaTela(Check check) {
        this.telaCheck.getjTextFieldId().setText(String.valueOf(check.getId()));
        this.telaCheck.getjFormattedTextFieldDataCadastro().setText(formatarData(check.getDataHoraCadastro()));
        this.telaCheck.getjFormattedTextFieldDataEntrada().setText(formatarData(check.getDataHoraEntrada()));
        this.telaCheck.getjFormattedTextFieldDataSaida().setText(formatarData(check.getDataHoraSaida()));
        this.telaCheck.getjTextFieldObs().setText(check.getObs());
        this.telaCheck.getjComboBoxStatus().setSelectedItem(check.getStatus() == 'A' ? "Ativo" : "Inativo");

        if (check.getReserva() != null) {
            adicionarReserva(check.getReserva());
        } else {
            this.reservaSelecionada = null;
            this.telaCheck.getjFormattedTextFieldReserva().setText("");
        }

        this.telaCheck.getjComboBoxStatusRecebimento().setSelectedItem("Ativo");
        this.telaCheck.getjFormattedTextFieldDataEntrada().requestFocus();

        carregarHospedesDoCheck(check.getId());
        carregarQuartosDoCheck(check.getId());
        carregarVagasDoCheck(check.getId());
        carregarRecebimentoDoCheck(check.getId());
        recalcularValorPagar();

    }

    private void carregarRecebimentoDoCheck(int checkId) {
        try {
            Receber receber = this.receberService.CarregarPorCheck(checkId);
            if (receber != null) {
                this.telaCheck.getjTextFieldValorOriginal().setText(String.format(Locale.US, "%.2f", receber.getValorOriginal()));
                this.telaCheck.getjTextFieldDesconto().setText(String.format(Locale.US, "%.2f", receber.getDesconto()));
                this.telaCheck.getjTextFieldAcrescimo().setText(String.format(Locale.US, "%.2f", receber.getAcrescimo()));
                this.telaCheck.getjTextFieldValorPago().setText(String.format(Locale.US, "%.2f", receber.getValorPago()));
            } else {
                this.telaCheck.getjTextFieldValorOriginal().setText("0.00");
                this.telaCheck.getjTextFieldDesconto().setText("0.00");
                this.telaCheck.getjTextFieldAcrescimo().setText("0.00");
                this.telaCheck.getjTextFieldValorPago().setText("0.00");
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, "Erro ao carregar dados do recebimento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarVagasDoCheck(int checkId) {
        this.alocacoesVagasSelecionadas.clear();
        limparTabela(this.telaCheck.getjTableVaga());
        try {
            List<model.AlocacaoVaga> alocacoes = this.alocacaoVagaService.carregarPorCheck(checkId);
            for (model.AlocacaoVaga aloc : alocacoes) {
                this.alocacoesVagasSelecionadas.add(aloc);
                DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableVaga().getModel();
                tabela.addRow(new Object[]{
                    aloc.getId() == 0 ? "" : aloc.getId(),
                    aloc.getVagaEstacionamento().getDescricao(),
                    aloc.getVeiculo().getPlaca(),
                    aloc.getObs(),
                    aloc.getStatus(),
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarHospedesDoCheck(int checkId) {
        this.hospedesSelecionados.clear();
        limparTabela(this.telaCheck.getjTableHospedes());

        try {
            List<CheckHospede> checkHospedes = this.checkHospedeService.carregarPorCheck(checkId);
            for (CheckHospede checkHospede : checkHospedes) {
                this.hospedesSelecionados.add(checkHospede);
                DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableHospedes().getModel();
                tabela.addRow(new Object[]{
                    checkHospede.getId() == 0 ? "" : checkHospede.getId(),
                    checkHospede.getHospede().getNome(),
                    checkHospede.getTipoHospede(),
                    checkHospede.getObs(),
                    checkHospede.getHospede().getStatus()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarQuartosDoCheck(int checkId) {
        this.quartosSelecionados.clear();
        limparTabela(this.telaCheck.getjTableQuartos());

        try {
            List<model.CheckQuarto> vinculos = this.checkQuartoService.carregarPorCheck(checkId);
            for (model.CheckQuarto checkQuarto : vinculos) {
                this.quartosSelecionados.add(checkQuarto);
                DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableQuartos().getModel();
                tabela.addRow(new Object[]{
                    checkQuarto.getId() == 0 ? "" : checkQuarto.getId(),
                    checkQuarto.getQuarto().getDescricao(),
                    checkQuarto.getQuarto().getObs(),
                    checkQuarto.getQuarto().getStatus()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario(boolean habilitarEdicao) {
        Utilities.limpaComponentes(this.telaCheck.getjPanelCheck(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelHospedes(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelQuartos(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelVaga(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelDados(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelRecebimento(), habilitarEdicao);

        limparTabela(this.telaCheck.getjTableHospedes());
        limparTabela(this.telaCheck.getjTableQuartos());
        limparTabela(this.telaCheck.getjTableVaga());
        this.alocacoesVagasSelecionadas.clear();

        this.telaCheck.getjButtonAlocarHospede().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarHospede().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonAlocarQuarto().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarQuarto().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonAlocarVaga().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarVaga().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarVeiculo().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarReserva().setEnabled(habilitarEdicao);

        this.telaCheck.getjTextFieldId().setText("");
        this.telaCheck.getjTextFieldVaga().setText("");
        this.telaCheck.getjTextFieldVeiculo().setText("");
        this.telaCheck.getjComboBoxStatus().setSelectedIndex(-1);
        this.telaCheck.getjComboBoxStatusRecebimento().setSelectedIndex(-1);
        this.telaCheck.getjComboBoxTipoHospede().setSelectedIndex(-1);

        this.telaCheck.getjTextFieldValorOriginal().setText("");
        this.telaCheck.getjTextFieldDesconto().setText("");
        this.telaCheck.getjTextFieldAcrescimo().setText("");
        this.telaCheck.getjTextFieldValorPago().setText("");
        this.telaCheck.getjTextFieldValorPagar().setText("");
        this.telaCheck.getjFormattedTextFieldReserva().setText("");
        this.reservaSelecionada = null;
    }

    private void preencherPadroesNovoCadastro() {
        String hoje = Utilities.getDataHoje();

        this.telaCheck.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCheck.getjComboBoxStatusRecebimento().setSelectedItem("Ativo");
        this.telaCheck.getjComboBoxTipoHospede().setSelectedIndex(-1);

        this.telaCheck.getjFormattedTextFieldDataCadastro().setText(hoje);

        this.telaCheck.getjTextFieldValorOriginal().setText("0.00");
        this.telaCheck.getjTextFieldDesconto().setText("0.00");
        this.telaCheck.getjTextFieldAcrescimo().setText("0.00");
        this.telaCheck.getjTextFieldValorPago().setText("0.00");
        this.telaCheck.getjTextFieldValorPagar().setText("0.00");
    }

    private void limparTabela(JTable tabela) {
        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
    }

    private boolean validarDataObrigatoria(String valor, String nomeCampo) {
        String dataSql = Utilities.formatarDataToSqlData(valor);
        if (!ValidadorCampos.validarData(dataSql)) {
            JOptionPane.showMessageDialog(this.telaCheck, "O campo " + nomeCampo + " está inválido.");
            return false;
        }
        return true;
    }

    private Date parseData(String texto) {
        if (texto == null || !texto.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return null;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            format.setLenient(false);
            return format.parse(texto);
        } catch (ParseException ex) {
            return null;
        }
    }

    private String formatarData(Date data) {
        if (data == null) {
            return "";
        }

        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(data);
    }

    private void configurarCalculoRecebimento() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalcularValorPagar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recalcularValorPagar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recalcularValorPagar();
            }
        };

        this.telaCheck.getjTextFieldValorOriginal().getDocument().addDocumentListener(listener);
        this.telaCheck.getjTextFieldDesconto().getDocument().addDocumentListener(listener);
        this.telaCheck.getjTextFieldAcrescimo().getDocument().addDocumentListener(listener);
    }

    private void recalcularValorPagar() {
        try {
            float valorOriginal = lerValorMonetario(this.telaCheck.getjTextFieldValorOriginal().getText());
            float desconto = lerValorMonetario(this.telaCheck.getjTextFieldDesconto().getText());
            float acrescimo = lerValorMonetario(this.telaCheck.getjTextFieldAcrescimo().getText());
            float valorPagar = this.checkService.calcularValorPagar(valorOriginal, desconto, acrescimo);
            this.telaCheck.getjTextFieldValorPagar().setText(String.format(Locale.US, "%.2f", valorPagar));
        } catch (NumberFormatException ex) {
            this.telaCheck.getjTextFieldValorPagar().setText("");
        }
    }

    private float lerValorMonetario(String texto) throws NumberFormatException {
        if (texto == null) {
            return 0f;
        }

        String valorTratado = texto.trim()
            .replace("R$", "")
            .replace(" ", "");

        if (valorTratado.isEmpty()) {
            return 0f;
        }

        if (valorTratado.contains(",") && valorTratado.contains(".")) {
            valorTratado = valorTratado.replace(".", "").replace(",", ".");
        } else if (valorTratado.contains(",")) {
            valorTratado = valorTratado.replace(",", ".");
        }

        return Float.parseFloat(valorTratado);
    }
}
