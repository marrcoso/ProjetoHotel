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

import model.Check;
import model.CheckHospede;
import model.Hospede;
import model.VagaEstacionamento;
import service.CheckHospedeService;
import service.CheckService;
import service.HospedeService;
import service.VagaEstacionamentoService;
import utilities.Utilities;
import utilities.ValidadorCampos;
import view.TelaBuscaHospede;
import view.TelaBuscaVaga;
import view.TelaCheck;

public final class ControllerCadCheck implements ActionListener, InterfaceControllerCad<Check> {

    private final TelaCheck telaCheck;
    private final CheckService checkService;
    private final CheckHospedeService checkHospedeService;
    private final HospedeService hospedeService;
    private final VagaEstacionamentoService vagaService;
    private final List<Hospede> hospedesSelecionados;
    private VagaEstacionamento vagaSelecionada;
    private int codigo;

    public ControllerCadCheck(TelaCheck telaCheck) {
        this.telaCheck = telaCheck;
        this.checkService = new CheckService();
        this.checkHospedeService = new CheckHospedeService();
        this.hospedeService = new HospedeService();
        this.vagaService = new VagaEstacionamentoService();
        this.hospedesSelecionados = new ArrayList<>();

        Utilities.setAlwaysDisabled(this.telaCheck.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(this.telaCheck.getjComboBoxStatus(), true);
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
        this.telaCheck.getjButtonBuscarVaga().addActionListener(this);
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
        if (source == telaCheck.getjButtonBuscarVaga()) {
            handleBuscarVaga();
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
        this.vagaSelecionada = null;

        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), false);
        limparFormulario(true);
        preencherPadroesNovoCadastro();
        this.telaCheck.getjFormattedTextFieldDataEntrada().requestFocus();
    }

    @Override
    public void handleCancelar() {
        this.codigo = 0;
        this.hospedesSelecionados.clear();
        this.vagaSelecionada = null;

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
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilities.ativaDesativa(this.telaCheck.getjPanelBotoes(), true);
        limparFormulario(false);
        this.hospedesSelecionados.clear();
        this.vagaSelecionada = null;
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

    private void handleBuscarVaga() {
        final int[] codigoVaga = {0};
        TelaBuscaVaga telaBuscaVaga = new TelaBuscaVaga(null, true);
        new ControllerBuscaVagaEstacionamento(telaBuscaVaga, valor -> codigoVaga[0] = valor);
        telaBuscaVaga.setVisible(true);

        if (codigoVaga[0] == 0) {
            return;
        }

        try {
            this.vagaSelecionada = this.vagaService.Carregar(codigoVaga[0]);
            preencherTabelaVaga();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this.telaCheck, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
        preencherTabelaVaga();
        recalcularValorPagar();
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

    private void preencherTabelaVaga() {
        limparTabela(this.telaCheck.getjTableVaga());
        if (this.vagaSelecionada == null) {
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaCheck.getjTableVaga().getModel();
        tabela.addRow(new Object[]{
            this.vagaSelecionada.getId(),
            this.vagaSelecionada.getDescricao(),
            this.vagaSelecionada.getObs(),
            this.vagaSelecionada.getStatus()
        });
    }

    private void limparFormulario(boolean habilitarEdicao) {
        Utilities.limpaComponentes(this.telaCheck.getjPanelCheck(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelReserva(), habilitarEdicao);
        Utilities.limpaComponentes(this.telaCheck.getjPanelRecebimento(), habilitarEdicao);

        limparTabela(this.telaCheck.getjTableHospedes());
        limparTabela(this.telaCheck.getjTableVaga());

        this.telaCheck.getjButtonBuscarHospede().setEnabled(habilitarEdicao);
        this.telaCheck.getjButtonBuscarVaga().setEnabled(habilitarEdicao);

        setEditavel(this.telaCheck.getjFormattedTextFieldDataEntrada(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataSaida(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataReserva(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataEntradaReserva(), habilitarEdicao);
        setEditavel(this.telaCheck.getjFormattedTextFieldDataSaidaReserva(), habilitarEdicao);

        this.telaCheck.getjTextFieldId().setText("");
        this.telaCheck.getjTextFieldIdReserva().setText("");
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
