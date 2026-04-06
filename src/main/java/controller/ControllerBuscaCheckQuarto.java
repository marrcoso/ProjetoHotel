package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.table.DefaultTableModel;
import model.DTO.CheckQuartoBuscaDTO;
import service.CheckQuartoService;
import view.TelaBuscaCheckQuarto;

public class ControllerBuscaCheckQuarto implements ActionListener {

    private final TelaBuscaCheckQuarto telaBusca;
    private final CheckQuartoService service;
    private final Consumer<Integer> callback;
    private List<CheckQuartoBuscaDTO> listaDados;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ControllerBuscaCheckQuarto(TelaBuscaCheckQuarto telaBusca, Consumer<Integer> callback) {
        this.telaBusca = telaBusca;
        this.service = new CheckQuartoService();
        this.callback = callback;
        this.listaDados = new ArrayList<>();

        this.telaBusca.getjButtonCarregar().addActionListener(this);
        this.telaBusca.getjButtonFiltar().addActionListener(this);
        this.telaBusca.getjButtonSair().addActionListener(this);
        
        // Carga inicial
        filtrar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == telaBusca.getjButtonCarregar()) {
            int row = telaBusca.getjTableDados().getSelectedRow();
            if (row != -1) {
                int id = listaDados.get(row).getId();
                callback.accept(id);
                telaBusca.dispose();
            }
        } else if (e.getSource() == telaBusca.getjButtonFiltar()) {
            filtrar();
        } else if (e.getSource() == telaBusca.getjButtonSair()) {
            telaBusca.dispose();
        }
    }

    private void filtrar() {
        String filtroTexto = telaBusca.getjTFFiltro().getText().trim();
        String tipoFiltro = (String) telaBusca.getjCBFiltro().getSelectedItem();
        
        listaDados = service.carregarAtivosComFiltro(tipoFiltro, filtroTexto);
        
        DefaultTableModel model = (DefaultTableModel) telaBusca.getjTableDados().getModel();
        model.setRowCount(0);

        for (CheckQuartoBuscaDTO cq : listaDados) {
            model.addRow(new Object[]{
                cq.getId(),
                cq.getQuartoIdentificacao(),
                sdf.format(cq.getDataEntrada()),
                cq.getHospedeTitular() != null ? cq.getHospedeTitular() : "---",
                cq.getCheckId()
            });
        }
    }
}
