package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import model.Check;
import model.OrdemServico;
import model.Quarto;
import model.Servico;
import service.CheckService;
import service.OrdemServicoService;
import service.QuartoService;
import service.ServicoService;
import utilities.Utilities;
import view.TelaBuscaCheck;
import view.TelaBuscaOrdemServico;
import view.TelaBuscaQuarto;
import view.TelaBuscaServico;
import view.TelaCadastroOrdemServico;

public class ControllerCadOrdemServico implements ActionListener, InterfaceControllerCad<OrdemServico> {

    private final TelaCadastroOrdemServico telaCad;
    private final OrdemServicoService service;
    private final CheckService checkService;
    private final ServicoService servicoService;
    private final QuartoService quartoService;

    private Check checkSelecionado;
    private Servico servicoSelecionado;
    private Quarto quartoSelecionado;
    private int codigo;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ControllerCadOrdemServico(TelaCadastroOrdemServico telaCad) {
        this.telaCad = telaCad;
        this.service = new OrdemServicoService();
        this.checkService = new CheckService();
        this.servicoService = new ServicoService();
        this.quartoService = new QuartoService();

        initListeners();
        
        Utilities.setAlwaysDisabled(telaCad.getjTextFieldId(), true);
        Utilities.setAlwaysDisabled(telaCad.getjFormattedTextFieldCadastro(), true);
        Utilities.setAlwaysDisabled(telaCad.getjFormattedTextFieldCheck(), true);
        Utilities.setAlwaysDisabled(telaCad.getjFormattedTextFieldServico(), true);
        Utilities.setAlwaysDisabled(telaCad.getjFormattedTextFieldQuarto(), true);
        Utilities.setAlwaysDisabled(telaCad.getjComboBoxStatus(), true);
        
        Utilities.ativaDesativa(telaCad.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCad.getjPanelDados(), false);
    }

    @Override
    public void initListeners() {
        this.telaCad.getjButtonNovo().addActionListener(this);
        this.telaCad.getjButtonCancelar().addActionListener(this);
        this.telaCad.getjButtonGravar().addActionListener(this);
        this.telaCad.getjButtonBuscar().addActionListener(this);
        this.telaCad.getjButtonSair().addActionListener(this);
        this.telaCad.getjButtonRelacionarCheck().addActionListener(this);
        this.telaCad.getjButtonRelacionarServico().addActionListener(this);
        this.telaCad.getjButtonRelacionarQuarto().addActionListener(this);
        this.telaCad.getjButtonStatusAvancar().addActionListener(this);
        this.telaCad.getjButtonStatusCancelar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == telaCad.getjButtonNovo()) handleNovo();
        else if (source == telaCad.getjButtonCancelar()) handleCancelar();
        else if (source == telaCad.getjButtonGravar()) handleGravar();
        else if (source == telaCad.getjButtonBuscar()) handleBuscar();
        else if (source == telaCad.getjButtonSair()) handleSair();
        else if (source == telaCad.getjButtonRelacionarCheck()) handleBuscarCheck();
        else if (source == telaCad.getjButtonRelacionarServico()) handleBuscarServico();
        else if (source == telaCad.getjButtonRelacionarQuarto()) handleBuscarQuarto();
        else if (source == telaCad.getjButtonStatusAvancar()) handleStatusAvancar();
        else if (source == telaCad.getjButtonStatusCancelar()) handleStatusCancelar();
    }

    @Override
    public void handleNovo() {
        this.codigo = 0;
        Utilities.ativaDesativa(telaCad.getjPanelBotoes(), false);
        Utilities.limpaComponentes(telaCad.getjPanelDados(), true);
        telaCad.getjTextFieldId().setText("");
        telaCad.getjFormattedTextFieldCadastro().setText(sdf.format(new Date()));
        telaCad.getjComboBoxStatus().setSelectedIndex(0); // Pendente
        checkSelecionado = null;
        servicoSelecionado = null;
        quartoSelecionado = null;
        configurarEstadoBotoesStatus('P');
        bloquearCamposPorStatus('P');
    }

    @Override
    public void handleCancelar() {
        this.codigo = 0;
        Utilities.ativaDesativa(telaCad.getjPanelBotoes(), true);
        Utilities.limpaComponentes(telaCad.getjPanelDados(), false);
        checkSelecionado = null;
        servicoSelecionado = null;
        quartoSelecionado = null;
        configurarEstadoBotoesStatus(' ');
    }

    @Override
    public void handleSair() {
        telaCad.dispose();
    }

    private void handleBuscarCheck() {
        TelaBuscaCheck telaBusca = new TelaBuscaCheck(null, true);
        // "começar mostrando as abertas mas permitir filtro antigo"
        // Passamos 'true' para carregar apenas disponíveis inicialmente
        new ControllerBuscaCheck(telaBusca, id -> {
            checkSelecionado = checkService.Carregar(id);
            telaCad.getjFormattedTextFieldCheck().setText(String.valueOf(id));
        }, true); 
        telaBusca.setVisible(true);
    }

    private void handleBuscarServico() {
        TelaBuscaServico telaBusca = new TelaBuscaServico(null, true);
        new ControllerBuscaServico(telaBusca, id -> {
            servicoSelecionado = servicoService.Carregar(id);
            telaCad.getjFormattedTextFieldServico().setText(servicoSelecionado.getDescricao());
        }, true);
        telaBusca.setVisible(true);
    }

    private void handleBuscarQuarto() {
        TelaBuscaQuarto telaBusca = new TelaBuscaQuarto(null, true);
        new ControllerBuscaQuarto(telaBusca, id -> {
            quartoSelecionado = quartoService.Carregar(id);
            telaCad.getjFormattedTextFieldQuarto().setText(quartoSelecionado.getIdentificacao());
        }, true);
        telaBusca.setVisible(true);
    }

    @Override
    public boolean isFormularioValido() {
        if (checkSelecionado == null) {
            JOptionPane.showMessageDialog(telaCad, "Selecione um Check!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (servicoSelecionado == null) {
            JOptionPane.showMessageDialog(telaCad, "Selecione um Serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (telaCad.getjFormattedTextFieldPrevInicio().getText().contains(" ")) {
            String checkDay = telaCad.getjFormattedTextFieldPrevInicio().getText().split(" ")[0].replace("/", "").trim();
            if (checkDay.isEmpty()) {
                JOptionPane.showMessageDialog(telaCad, "Informe a data de início prevista!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (telaCad.getjFormattedTextFieldPrevTermino().getText().contains(" ")) {
            String checkDay = telaCad.getjFormattedTextFieldPrevTermino().getText().split(" ")[0].replace("/", "").trim();
            if (checkDay.isEmpty()) {
                JOptionPane.showMessageDialog(telaCad, "Informe a data de término prevista!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        try {
            java.util.Date inicio = sdf.parse(telaCad.getjFormattedTextFieldPrevInicio().getText());
            java.util.Date termino = sdf.parse(telaCad.getjFormattedTextFieldPrevTermino().getText());

            if (termino.before(inicio)) {
                JOptionPane.showMessageDialog(telaCad, "A data de término não pode ser antes da data de início!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            // Erro de parse ignorado aqui, pois a validação de campos obrigatórios já reporta se estiver incompleto
        }

        return true;
    }

    @Override
    public OrdemServico construirDoFormulario() {
        OrdemServico ordem = new OrdemServico();
        String idStr = telaCad.getjTextFieldId().getText();
        if (!idStr.isEmpty()) {
            ordem.setId(Integer.parseInt(idStr));
        }

        ordem.setCheck(checkSelecionado);
        ordem.setServico(servicoSelecionado);
        ordem.setQuarto(quartoSelecionado);
        ordem.setObs(telaCad.getjTextFieldObservacao().getText());
        
        int statusIdx = telaCad.getjComboBoxStatus().getSelectedIndex();
        char s = 'P';
        if (statusIdx == 1) s = 'E';
        else if (statusIdx == 2) s = 'F';
        else if (statusIdx == 3) s = 'C';
        ordem.setStatus(s);
        
        try {
            ordem.setDataHoraCadastro(sdf.parse(telaCad.getjFormattedTextFieldCadastro().getText()));
            ordem.setDataHoraPrevistaInicio(sdf.parse(telaCad.getjFormattedTextFieldPrevInicio().getText()));
            ordem.setDataHoraPrevistaTermino(sdf.parse(telaCad.getjFormattedTextFieldPrevTermino().getText()));
        } catch (Exception e) {
            // Se falhar o parse do cadastro, usa data atual. As outras deveriam ter sido validadas.
            if (ordem.getDataHoraCadastro() == null) ordem.setDataHoraCadastro(new Date());
        }
        
        return ordem;
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) return;

        try {
            OrdemServico ordem = construirDoFormulario();
            if (ordem.getId() == 0) service.Criar(ordem);
            else service.Atualizar(ordem);

            JOptionPane.showMessageDialog(telaCad, "Ordem de serviço gravada com sucesso!");
            handleCancelar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(telaCad, "Erro ao gravar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleBuscar() {
        this.codigo = 0;
        TelaBuscaOrdemServico telaBusca = new TelaBuscaOrdemServico(null, true);
        new ControllerBuscaOrdemServico(telaBusca, id -> this.codigo = id);
        telaBusca.setVisible(true);

        if (this.codigo != 0) {
            OrdemServico ordem = service.Carregar(this.codigo);
            if (ordem != null) carregarDados(ordem);
        }
    }

    private void carregarDados(OrdemServico ordem) {
        Utilities.ativaDesativa(telaCad.getjPanelBotoes(), false);
        Utilities.limpaComponentes(telaCad.getjPanelDados(), true);
        
        checkSelecionado = ordem.getCheck();
        servicoSelecionado = ordem.getServico();
        quartoSelecionado = ordem.getQuarto();
        char s = ordem.getStatus();
        
        telaCad.getjTextFieldId().setText(String.valueOf(ordem.getId()));
        telaCad.getjFormattedTextFieldCheck().setText(String.valueOf(checkSelecionado.getId()));
        telaCad.getjFormattedTextFieldServico().setText(servicoSelecionado.getDescricao());
        if (quartoSelecionado != null) {
            telaCad.getjFormattedTextFieldQuarto().setText(quartoSelecionado.getIdentificacao());
        }
        
        telaCad.getjFormattedTextFieldCadastro().setText(sdf.format(ordem.getDataHoraCadastro()));
        telaCad.getjFormattedTextFieldPrevInicio().setText(sdf.format(ordem.getDataHoraPrevistaInicio()));
        telaCad.getjFormattedTextFieldPrevTermino().setText(sdf.format(ordem.getDataHoraPrevistaTermino()));
        telaCad.getjTextFieldObservacao().setText(ordem.getObs());
        
        int statusIdx = 0;
        if (s == 'E') statusIdx = 1;
        else if (s == 'F') statusIdx = 2;
        else if (s == 'C') statusIdx = 3;
        telaCad.getjComboBoxStatus().setSelectedIndex(statusIdx);
        
        configurarEstadoBotoesStatus(s);
        bloquearCamposPorStatus(s);
    }

    private void configurarEstadoBotoesStatus(char status) {
        String idStr = telaCad.getjTextFieldId().getText();
        boolean hasId = !idStr.isEmpty() && !idStr.equals("0");
        
        boolean canAvancar = (status == 'P' || status == 'E') && hasId;
        boolean canCancelar = (status != 'C' && status != ' ') && hasId;
        
        telaCad.getjButtonStatusAvancar().setEnabled(canAvancar);
        telaCad.getjButtonStatusCancelar().setEnabled(canCancelar);
    }

    private void bloquearCamposPorStatus(char status) {
        boolean pendente = (status == 'P');
        telaCad.getjButtonGravar().setEnabled(pendente);
        telaCad.getjFormattedTextFieldPrevInicio().setEnabled(pendente);
        telaCad.getjFormattedTextFieldPrevTermino().setEnabled(pendente);
        telaCad.getjTextFieldObservacao().setEnabled(pendente);
        telaCad.getjButtonRelacionarCheck().setEnabled(pendente);
        telaCad.getjButtonRelacionarServico().setEnabled(pendente);
        telaCad.getjButtonRelacionarQuarto().setEnabled(pendente);
    }

    private void handleStatusAvancar() {
        String idStr = telaCad.getjTextFieldId().getText();
        if (idStr.isEmpty()) return;

        if (JOptionPane.showConfirmDialog(telaCad, 
                "Deseja avançar o status desta OS?", "Confirmação", 
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        
        int id = Integer.parseInt(idStr);
        int statusIdx = telaCad.getjComboBoxStatus().getSelectedIndex();
        char novoStatus = ' ';
        
        if (statusIdx == 0) novoStatus = 'E'; // Pendente -> Em Processo
        else if (statusIdx == 1) novoStatus = 'F'; // Em Processo -> Finalizado
        
        if (novoStatus != ' ') {
            service.mudarStatus(id, novoStatus);
            carregarDados(service.Carregar(id));
        }
    }

    private void handleStatusCancelar() {
        String idStr = telaCad.getjTextFieldId().getText();
        if (idStr.isEmpty()) return;

        if (JOptionPane.showConfirmDialog(telaCad, 
                "Deseja realmente CANCELAR esta OS?", "Confirmação", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) return;
        
        service.mudarStatus(Integer.parseInt(idStr), 'C');
        carregarDados(service.Carregar(Integer.parseInt(idStr)));
    }
}
