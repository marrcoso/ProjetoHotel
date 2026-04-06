package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TelaReserva extends javax.swing.JDialog {

    public TelaReserva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JButton getjButtonBuscar() {
        return jButtonBuscar;
    }

    public JButton getjButtonCancelar() {
        return jButtonCancelar;
    }

    public JButton getjButtonGravar() {
        return jButtonGravar;
    }

    public JButton getjButtonNovo() {
        return jButtonNovo;
    }

    public JButton getjButtonSair() {
        return jButtonSair;
    }

    public JPanel getjPanelBotoes() {
        return jPanelBotoes;
    }

    public JPanel getjPanelDados() {
        return jPanelReserva;
    }

    public JPanel getjPanelQuartos() {
        return jPanelQuartos;
    }

    public JComboBox<String> getjComboBoxStatus() {
        return jComboBoxStatus;
    }

    public JTextField getjTextFieldId() {
        return jTextFieldId;
    }

    public JTextField getjTextFieldObs() {
        return jTextFieldObs;
    }

    public JButton getjButtonAlocarQuarto() {
        return jButtonAlocarQuarto;
    }

    public JButton getjButtonRelacionarQuarto() {
        return jButtonRelacionarQuarto;
    }

    public JTextField getjTextFieldObsQuarto() {
        return jTextFieldObsQuarto;
    }

    public JFormattedTextField getjFormattedTextFieldQuarto() {
        return jFormattedTextFieldQuarto;
    }

    public view.components.DateTimePicker getjFormattedTextFieldDataReserva() {
        return jFormattedTextFieldDataReserva;
    }

    public view.components.DateTimePicker getjFormattedTextFieldDataEntrada() {
        return jFormattedTextFieldDataEntrada;
    }

    public view.components.DateTimePicker getjFormattedTextFieldDataSaida() {
        return jFormattedTextFieldDataSaida;
    }

    public JTable getjTableQuartos() {
        return jTableQuartos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitulo = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jPanelBotoes = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelReserva = new javax.swing.JPanel();
        jTextFieldObs = new javax.swing.JTextField();
        jLabelObs = new javax.swing.JLabel();
        jComboBoxStatus = new javax.swing.JComboBox<>();
        jLabelStatus = new javax.swing.JLabel();
        jFormattedTextFieldDataReserva = new view.components.DateTimePicker(false, false);
        jLabelDataReserva = new javax.swing.JLabel();
        jFormattedTextFieldDataEntrada = new view.components.DateTimePicker(true, false);
        jLabelDataEntrada = new javax.swing.JLabel();
        jFormattedTextFieldDataSaida = new view.components.DateTimePicker(true, false);
        jLabelDataSaida = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabelId = new javax.swing.JLabel();
        jPanelQuartos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableQuartos = new javax.swing.JTable();
        jButtonAlocarQuarto = new javax.swing.JButton();
        jLabelQuarto = new javax.swing.JLabel();
        jFormattedTextFieldQuarto = new javax.swing.JFormattedTextField();
        jButtonRelacionarQuarto = new javax.swing.JButton();
        jLabelObsQuarto = new javax.swing.JLabel();
        jTextFieldObsQuarto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Reservas");
        setResizable(false);

        jPanelTitulo.setBackground(new java.awt.Color(153, 153, 255));
        jPanelTitulo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelTitulo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Reserva");

        javax.swing.GroupLayout jPanelTituloLayout = new javax.swing.GroupLayout(jPanelTitulo);
        jPanelTitulo.setLayout(jPanelTituloLayout);
        jPanelTituloLayout.setHorizontalGroup(
            jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTituloLayout.setVerticalGroup(
            jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );

        jPanelBotoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Create.png"))); // NOI18N
        jButtonNovo.setText("Novo");
        jButtonNovo.setActionCommand("0");
        jPanelBotoes.add(jButtonNovo);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancel.png"))); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setActionCommand("1");
        jButtonCancelar.setEnabled(false);
        jPanelBotoes.add(jButtonCancelar);

        jButtonGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/OK.png"))); // NOI18N
        jButtonGravar.setText("Gravar");
        jButtonGravar.setActionCommand("1");
        jButtonGravar.setEnabled(false);
        jPanelBotoes.add(jButtonGravar);

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Load.png"))); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setActionCommand("0");
        jPanelBotoes.add(jButtonBuscar);

        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Exit.png"))); // NOI18N
        jButtonSair.setText("Sair");
        jButtonSair.setActionCommand("0");
        jPanelBotoes.add(jButtonSair);

        jPanelReserva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelObs.setText("OBS.:");

        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));
        jComboBoxStatus.setEnabled(false);

        jLabelStatus.setText("Status");

        jFormattedTextFieldDataReserva.setEditable(false);

        jLabelDataReserva.setText("Data da Reserva");

        jLabelDataEntrada.setText("Previsão de Entrada");

        jLabelDataSaida.setText("Previsão de Saída");

        jTextFieldId.setEditable(false);
        jTextFieldId.setEnabled(false);

        jLabelId.setText("ID");

        javax.swing.GroupLayout jPanelReservaLayout = new javax.swing.GroupLayout(jPanelReserva);
        jPanelReserva.setLayout(jPanelReservaLayout);
        jPanelReservaLayout.setHorizontalGroup(
            jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReservaLayout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelId)
                    .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelObs)
                        .addGroup(jPanelReservaLayout.createSequentialGroup()
                            .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jFormattedTextFieldDataReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelDataReserva)
                                .addComponent(jFormattedTextFieldDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelDataEntrada))
                            .addGap(32, 32, 32)
                            .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jFormattedTextFieldDataSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelDataSaida)
                                .addComponent(jLabelStatus)
                                .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jTextFieldObs)))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        jPanelReservaLayout.setVerticalGroup(
            jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReservaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelReservaLayout.createSequentialGroup()
                        .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelReservaLayout.createSequentialGroup()
                                .addComponent(jLabelDataReserva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldDataReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelReservaLayout.createSequentialGroup()
                                .addComponent(jLabelStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDataEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelReservaLayout.createSequentialGroup()
                        .addComponent(jLabelDataSaida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelObs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reserva", jPanelReserva);

        jPanelQuartos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableQuartos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Quarto", "Obs", "Status"
            }
        ));
        jScrollPane2.setViewportView(jTableQuartos);
        if (jTableQuartos.getColumnModel().getColumnCount() > 0) {
            jTableQuartos.getColumnModel().getColumn(0).setMaxWidth(40);
            jTableQuartos.getColumnModel().getColumn(3).setMinWidth(100);
            jTableQuartos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jButtonAlocarQuarto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Load.png"))); // NOI18N
        jButtonAlocarQuarto.setText("Alocar Quarto");
        jButtonAlocarQuarto.setActionCommand("0");
        jButtonAlocarQuarto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlocarQuartoActionPerformed(evt);
            }
        });

        jLabelQuarto.setText("Quarto:");

        jFormattedTextFieldQuarto.setEditable(false);
        jFormattedTextFieldQuarto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));

        jButtonRelacionarQuarto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Find.png"))); // NOI18N
        jButtonRelacionarQuarto.setActionCommand("1");
        jButtonRelacionarQuarto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelacionarQuartoActionPerformed(evt);
            }
        });

        jLabelObsQuarto.setText("OBS.:");

        javax.swing.GroupLayout jPanelQuartosLayout = new javax.swing.GroupLayout(jPanelQuartos);
        jPanelQuartos.setLayout(jPanelQuartosLayout);
        jPanelQuartosLayout.setHorizontalGroup(
            jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuartosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanelQuartosLayout.createSequentialGroup()
                        .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQuartosLayout.createSequentialGroup()
                                .addComponent(jLabelQuarto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQuartosLayout.createSequentialGroup()
                                        .addComponent(jFormattedTextFieldQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonRelacionarQuarto)
                                        .addGap(313, 313, 313))
                                    .addComponent(jTextFieldObsQuarto, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)))
                            .addGroup(jPanelQuartosLayout.createSequentialGroup()
                                .addComponent(jLabelObsQuarto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 625, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addComponent(jButtonAlocarQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelQuartosLayout.setVerticalGroup(
            jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuartosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAlocarQuarto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelQuartosLayout.createSequentialGroup()
                        .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFormattedTextFieldQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelQuarto))
                            .addComponent(jButtonRelacionarQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldObsQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelObsQuarto))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quartos", jPanelQuartos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAlocarQuartoActionPerformed(java.awt.event.ActionEvent evt) {
        // Handled by controller
    }

    private void jButtonRelacionarQuartoActionPerformed(java.awt.event.ActionEvent evt) {
        // Handled by controller
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaReserva dialog = new TelaReserva(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlocarQuarto;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonRelacionarQuarto;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JComboBox<String> jComboBoxStatus;
    private view.components.DateTimePicker jFormattedTextFieldDataEntrada;
    private view.components.DateTimePicker jFormattedTextFieldDataReserva;
    private view.components.DateTimePicker jFormattedTextFieldDataSaida;
    private javax.swing.JFormattedTextField jFormattedTextFieldQuarto;
    private javax.swing.JLabel jLabelDataEntrada;
    private javax.swing.JLabel jLabelDataReserva;
    private javax.swing.JLabel jLabelDataSaida;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelObs;
    private javax.swing.JLabel jLabelObsQuarto;
    private javax.swing.JLabel jLabelQuarto;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanelBotoes;
    private javax.swing.JPanel jPanelQuartos;
    private javax.swing.JPanel jPanelReserva;
    private javax.swing.JPanel jPanelTitulo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableQuartos;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldObs;
    private javax.swing.JTextField jTextFieldObsQuarto;
    // End of variables declaration//GEN-END:variables
}
