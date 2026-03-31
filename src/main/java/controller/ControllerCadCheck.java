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
import model.Hospede;
import model.Quarto;
import model.VagaEstacionamento;
import model.Veiculo;
import service.AlocacaoVagaService;
import service.CheckHospedeService;
import service.CheckQuartoService;
import service.CheckService;
import service.HospedeService;
import service.QuartoService;
import service.VagaEstacionamentoService;
import service.VeiculoService;
import utilities.Utilities;
import utilities.ValidadorCampos;
import view.TelaBuscaHospede;
import view.TelaBuscaQuarto;
import view.TelaBuscaVaga;
import view.TelaBuscaVeiculo;
import view.TelaCheck;

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
    private final List<Hospede> hospedesSelecionados;
    private final List<Quarto> quartosSelecionados;
    private final List<AlocacaoVaga> alocacoesVagasSelecionadas;
    private Veiculo veiculoSelecionado;
    private VagaEstacionamento vagaSelecionada;
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
        this.hospedesSelecionados = new ArrayList<>();
        this.quartosSelecionados = new ArrayList<>();
        this.alocacoesVagasSelecionadas = new ArrayList<>();
        this.veiculoSelecionado = null;
        this.vagaSelecionada = null;

        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjComboBoxStatus(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldVaga(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldVeiculo(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjComboBoxStatusReserva(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjComboBoxStatusRecebimento(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldIdReserva(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldValorPagar(), true);

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
        this.telaCheck.getjButtonBuscarHospede().addActionListener(this);
        this.telaCheck.getjButtonBuscarQuarto().addActionListener(this);
        this.telaCheck.getjButtonAlocarVaga().addActionListener(this);
        this.telaCheck.getjButtonRelacionarVeiculo().addActionListener(this);   
        this.telaCheck.getjButtonRelacionarVaga().addActionListener(this);
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
        if (source == telaCheck.getjButtonBuscarHospede()) {
            handleBuscarHospede();
            return;
        }
        if (source == telaCheck.getjButtonBuscarQuarto()) {
            handleBuscarQuarto();
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
        }
    }

    @Override
    public void handleNovo() {
        this.codigo = 0;
        this.hospedesSelecionados.clear();
        this.quartosSelecionados.clear();
        this.alocacoesVagasSelecionadas.clear();

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
        if (!validarDataObrigatoria(this.telaCheck.getjFormattedTextFieldDataSaida().getText(), "Data de Saída")) {
            this.telaCheck.getjFormattedTextFieldDataSaida().requestFocus();
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
        if (!ValidadorCampos.compararDatas(dataEntrada, dataSaida)) {
            JOptionPane.showMessageDialog(this.telaCheck, "A data de saída não pode ser menor que a data de entrada.");
            this.telaCheck.getjFormattedTextFieldDataSaida().requestFocus();
            return false;
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
            lerValorMonetario(this.telaCheck.getjTextFieldValorOriginal().getText());
            lerValorMonetario(this.telaCheck.getjTextFieldDesconto().getText());
            lerValorMonetario(this.telaCheck.getjTextFieldAcrescimo().getText());
            lerValorMonetario(this.telaCheck.getjTextFieldValorPago().getText());
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
            this.checkHospedeService.substituirHospedesDoCheck(check.getId(), this.hospedesSelecionados);
            this.alocacaoVagaService.substituirAlocacoesDoCheck(check.getId(), this.alocacoesVagasSelecionadas);
            this.checkQuartoService.substituirQuartosDoCheck(check.getId(), this.quartosSelecionados);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), true);
        limparFormulario(false);
        this.hospedesSelecionados.clear();
        this.quartosSelecionados.clear();
        this.alocacoesVagasSelecionadas.clear();
        this.codigo = 0;
    }

    @Override
    public Check construirDoFormulario() {
        Check check = new Check();
        check.setDataHoraCadastro(parseData(this.telaCheck.getjFormattedTextFieldDataCadastro().getText()));
        check.setDataHoraEntrada(parseData(this.telaCheck.getjFormattedTextFieldDataEntrada().getText()));
        check.setDataHoraSaida(parseData(this.telaCheck.getjFormattedTextFieldDataSaida().getText()));
        check.setObs(this.telaCheck.getjTextFieldObs().getText().trim());
        check.setStatus("Ativo".equals(this.telaCheck.getjComboBoxStatus().getSelectedItem()) ? 'A' : 'I');
        return check;
    }

    @Override
    public void handleBuscar() {
        String valor = JOptionPane.showInputDialog(this.telaCheck, "Informe o ID do Check:");
        if (valor == null) {
            return;
        }

        valor = valor.trim();
        if (!ValidadorCampos.validarCampoNumero(valor)) {
            JOptionPane.showMessageDialog(this.telaCheck, "Informe um ID numérico válido.");
            return;
        }

        Check check;
        try {
            check = this.checkService.Carregar(Integer.parseInt(valor));
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

    private void handleBuscarHospede() {
        final int[] codigoHospede = {0};
        TelaBuscaHospede telaBuscaHospede = new TelaBuscaHospede(null, true);
        new ControllerBuscaHospede(telaBuscaHospede, valor -> codigoHospede[0] = valor);
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

    private void handleBuscarQuarto() {
        final int[] codigoQuarto = {0};
        TelaBuscaQuarto telaBuscaQuarto = new TelaBuscaQuarto(null, true);
        new ControllerBuscaQuarto(telaBuscaQuarto, valor -> codigoQuarto[0] = valor);
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
        new ControllerBuscaVeiculo(telaBuscaVeiculo, valor -> codigoVeiculo[0] = valor);
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
        new ControllerBuscaVagaEstacionamento(telaBuscaVaga, valor -> codigoVaga[0] = valor);
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
        model.AlocacaoVaga alocacao = new model.AlocacaoVaga();
        alocacao.setVagaEstacionamento(vaga);
        alocacao.setVeiculo(veiculo);
        alocacao.setStatus('A');
        alocacao.setObs("");
        this.alocacoesVagasSelecionadas.add(alocacao);
        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableVaga().getModel();
        tabela.addRow(new Object[]{
            alocacao.getId() == 0 ? "" : alocacao.getId(),
            vaga.getDescricao(),
            veiculo.getPlaca(),
            vaga.getStatus(),
        });
    }

    private void adicionarHospede(Hospede hospede) {
        if (hospede == null) {
            return;
        }

        for (Hospede selecionado : this.hospedesSelecionados) {
            if (selecionado.getId() == hospede.getId()) {
                JOptionPane.showMessageDialog(this.telaCheck, "Este hóspede já foi adicionado ao check.");
                return;
            }
        }

        this.hospedesSelecionados.add(hospede);
        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableHospedes().getModel();
        tabela.addRow(new Object[]{
            hospede.getId(),
            hospede.getNome(),
            hospede.getCpf(),
            hospede.getStatus()
        });
    }

    private void adicionarQuarto(Quarto quarto) {
        if (quarto == null) {
            return;
        }

        for (Quarto selecionado : this.quartosSelecionados) {
            if (selecionado.getId() == quarto.getId()) {
                JOptionPane.showMessageDialog(this.telaCheck, "Este quarto já foi adicionado ao check.");
                return;
            }
        }

        this.quartosSelecionados.add(quarto);
        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableQuartos().getModel();
        tabela.addRow(new Object[]{
            quarto.getId(),
            quarto.getDescricao(),
            quarto.getObs(),
            quarto.getStatus()
        });
    }

    private void carregarCheckNaTela(Check check) {
        this.telaCheck.getjTextFieldId().setText(String.valueOf(check.getId()));
        this.telaCheck.getjFormattedTextFieldDataCadastro().setText(formatarData(check.getDataHoraCadastro()));
        this.telaCheck.getjFormattedTextFieldDataEntrada().setText(formatarData(check.getDataHoraEntrada()));
        this.telaCheck.getjFormattedTextFieldDataSaida().setText(formatarData(check.getDataHoraSaida()));
        this.telaCheck.getjTextFieldObs().setText(check.getObs());
        this.telaCheck.getjComboBoxStatus().setSelectedItem(check.getStatus() == 'A' ? "Ativo" : "Inativo");

        this.telaCheck.getjComboBoxStatusReserva().setSelectedItem("Ativo");
        this.telaCheck.getjComboBoxStatusRecebimento().setSelectedItem("Ativo");
        this.telaCheck.getjFormattedTextFieldDataEntrada().requestFocus();

        carregarHospedesDoCheck(check.getId());
        carregarQuartosDoCheck(check.getId());
        carregarVagasDoCheck(check.getId());
        recalcularValorPagar();

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
                    aloc.getVagaEstacionamento().getId(),
                    aloc.getVagaEstacionamento().getDescricao(),
                    aloc.getVagaEstacionamento().getObs(),
                    aloc.getVagaEstacionamento().getStatus(),
                    aloc.getVeiculo().getId(),
                    aloc.getVeiculo().getPlaca()
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
            List<CheckHospede> vinculos = this.checkHospedeService.carregarPorCheck(checkId);
            for (CheckHospede vinculo : vinculos) {
                adicionarHospede(vinculo.getHospede());
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
            for (model.CheckQuarto vinculo : vinculos) {
                adicionarQuarto(vinculo.getQuarto());
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario(boolean habilitarEdicao) {
        Utilities.limpaComponentes(this.telaCheck.getjPanelCheck(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelReserva(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelRecebimento(), habilitarEdicao);

        limparTabela(this.telaCheck.getjTableHospedes());
        limparTabela(this.telaCheck.getjTableQuartos());
        limparTabela(this.telaCheck.getjTableVaga());
        this.alocacoesVagasSelecionadas.clear();

        this.telaCheck.getjButtonBuscarHospede().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonBuscarQuarto().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonAlocarVaga().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarVaga().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonRelacionarVeiculo().setEnabled(habilitarEdicao);

        setEditavel(this.telaCheck.getjFormattedTextFieldDataEntrada(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataSaida(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataReserva(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataEntradaReserva(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataSaidaReserva(), habilitarEdicao);

        this.telaCheck.getjTextFieldId().setText("");
        this.telaCheck.getjTextFieldIdReserva().setText("");
        this.telaCheck.getjTextFieldVaga().setText("");
        this.telaCheck.getjTextFieldVeiculo().setText("");
        this.telaCheck.getjComboBoxStatus().setSelectedIndex(-1);
        this.telaCheck.getjComboBoxStatusReserva().setSelectedIndex(-1);
        this.telaCheck.getjComboBoxStatusRecebimento().setSelectedIndex(-1);

        this.telaCheck.getjTextFieldValorOriginal().setText("");
        this.telaCheck.getjTextFieldDesconto().setText("");
        this.telaCheck.getjTextFieldAcrescimo().setText("");
        this.telaCheck.getjTextFieldValorPago().setText("");
        this.telaCheck.getjTextFieldValorPagar().setText("");
    }

    private void preencherPadroesNovoCadastro() {
        String hoje = Utilities.getDataHoje();

        this.telaCheck.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaCheck.getjComboBoxStatusReserva().setSelectedItem("Ativo");
        this.telaCheck.getjComboBoxStatusRecebimento().setSelectedItem("Ativo");

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

    private void setEditavel(javax.swing.JFormattedTextField campo, boolean editavel) {
        campo.setEnabled(editavel);
        campo.setEditable(editavel);
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
