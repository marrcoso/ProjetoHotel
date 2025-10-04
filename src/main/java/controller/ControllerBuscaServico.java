package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Servico;
import service.ServicoService;
import view.TelaBuscaServico;

public final class ControllerBuscaServico implements ActionListener, InterfaceControllerBusca<Servico> {

    private final TelaBuscaServico telaBuscaServico;
    private final ServicoService servicoService;
    private final Consumer<Integer> atualizaCodigo;

    public ControllerBuscaServico(TelaBuscaServico telaBuscaServico, Consumer<Integer> atualizaCodigo) {
        this.telaBuscaServico = telaBuscaServico;
        this.servicoService = new ServicoService();
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        this.telaBuscaServico.getjButtonCarregar().addActionListener(this);
        this.telaBuscaServico.getjButtonFiltar().addActionListener(this);
        this.telaBuscaServico.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaServico.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaServico.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaServico.getjButtonSair()) {
            handleSair();
        }
    }

    @Override
    public void handleCarregar() {
        if (telaBuscaServico.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            int codigo = (int) telaBuscaServico.getjTableDados()
                .getValueAt(telaBuscaServico.getjTableDados().getSelectedRow(), 0);
            atualizaCodigo.accept(codigo);
            telaBuscaServico.dispose();
        }
    }

    private enum FiltroServico {
        ID, DESCRICAO;

        public static FiltroServico fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Servico servico) {
        tabela.addRow(new Object[]{
            servico.getId(),
            servico.getDescricao(),
            servico.getObs(),
            servico.getStatus()
        });
    }

    @Override
    public void handleFiltrar() {
        if (telaBuscaServico.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaServico.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaServico.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaServico.getjTFFiltro().getText();

        FiltroServico filtro = FiltroServico.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID: {
                    Servico servico = servicoService.Carregar(Integer.parseInt(filtroTexto));
                    if (servico != null) {
                        adicionarLinhaTabela(tabela, servico);
                    }
                    break;
                }
                case DESCRICAO: {
                    List<Servico> listaPorDescricao = servicoService.Carregar("descricao", filtroTexto);
                    for (Servico s : listaPorDescricao) {
                        adicionarLinhaTabela(tabela, s);
                    }
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaServico, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSair() {
        this.telaBuscaServico.dispose();
    }
}
