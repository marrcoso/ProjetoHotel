package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Quarto;
import service.QuartoService;
import view.TelaBuscaQuarto;

public class ControllerBuscaQuarto implements ActionListener, InterfaceControllerBusca {

    private final TelaBuscaQuarto telaBuscaQuarto;
    private final QuartoService quartoService;

    public ControllerBuscaQuarto(TelaBuscaQuarto telaBuscaQuarto) {
        this.telaBuscaQuarto = telaBuscaQuarto;
        this.quartoService = new QuartoService();
        initListeners();
    }

    private void initListeners() {
        this.telaBuscaQuarto.getjButtonCarregar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonFiltar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();
        if (source == telaBuscaQuarto.getjButtonCarregar()) {
            handleCarregar();
            return;
        }
        if (source == telaBuscaQuarto.getjButtonFiltar()) {
            handleFiltrar();
            return;
        }
        if (source == telaBuscaQuarto.getjButtonSair()) {
            handleSair();
        }
    }

    private void handleCarregar() {
        if (telaBuscaQuarto.getjTableDados().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Edição!");
        } else {
            ControllerCadQuarto.codigo = (int) telaBuscaQuarto.getjTableDados()
                .getValueAt(telaBuscaQuarto.getjTableDados().getSelectedRow(), 0);
            telaBuscaQuarto.dispose();
        }
    }

    private enum FiltroQuarto {
        ID, DESCRICAO;

        public static FiltroQuarto fromIndex(int index) {
            switch (index) {
                case 0: return ID;
                case 1: return DESCRICAO;
                default: throw new IllegalArgumentException("Filtro inválido");
            }
        }
    }

    private void handleFiltrar() {
        if (telaBuscaQuarto.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem Dados para a Seleção...");
            return;
        }
        DefaultTableModel tabela = (DefaultTableModel) telaBuscaQuarto.getjTableDados().getModel();
        tabela.setRowCount(0);

        int filtroIndex = telaBuscaQuarto.getjCBFiltro().getSelectedIndex();
        String filtroTexto = telaBuscaQuarto.getjTFFiltro().getText();

        FiltroQuarto filtro = FiltroQuarto.fromIndex(filtroIndex);

        try {
            switch (filtro) {
                case ID:
                    Quarto quarto = quartoService.Carregar(Integer.parseInt(filtroTexto));
                    if (quarto != null) {
                        tabela.addRow(new Object[]{
                            quarto.getId(), quarto.getDescricao(), quarto.getCapacidadeHospedes(),
                            quarto.getMetragem(), quarto.getIdentificacao(), quarto.getAndar(),
                            quarto.isFlagAnimais(), quarto.getObs(), quarto.getStatus()
                        });
                    }
                    break;
                case DESCRICAO:
                    List<Quarto> listaPorDescricao = quartoService.Carregar("descricao", filtroTexto);
                    for (Quarto q : listaPorDescricao) {
                        tabela.addRow(new Object[]{
                            q.getId(), q.getDescricao(), q.getCapacidadeHospedes(),
                            q.getMetragem(), q.getIdentificacao(), q.getAndar(),
                            q.isFlagAnimais(), q.getObs(), q.getStatus()
                        });
                    }
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(telaBuscaQuarto, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSair() {
        this.telaBuscaQuarto.dispose();
    }
}
