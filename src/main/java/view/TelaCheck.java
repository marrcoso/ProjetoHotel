/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author house
 */
public class TelaCheck extends javax.swing.JDialog {

    /**
     * Creates new form TemplateCadastros2025
     */
    public TelaCheck(java.awt.Frame parent, boolean modal) {
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
        return jPanelCheck;
    }

    public JPanel getjPanelCheck() {
        return jPanelCheck;
    }

    public JPanel getjPanelHospedes() {
        return jPanelHospedes;
    }

    public JPanel getjPanelReserva() {
        return jPanelReserva;
    }

    public JPanel getjPanelVaga() {
        return jPanelVaga;
    }

    public JPanel getjPanelRecebimento() {
        return jPanelRecebimento;
    }

    public JComboBox<String> getjComboBoxStatus() {
        return jComboBoxStatus;
    }

    public void setjComboBoxStatus(JComboBox<String> jComboBoxStatus) {
        this.jComboBoxStatus = jComboBoxStatus;
    }

    public JTextField getjTextFieldId() {
        return jTextFieldId;
    }

    public void setjTextFieldId(JTextField jTextFieldId) {
        this.jTextFieldId = jTextFieldId;
    }

    public JTextField getjTextFieldObs() {
        return jTextFieldObs;
    }

    public void setjTextFieldObs(JTextField jTextFieldObs) {
        this.jTextFieldObs = jTextFieldObs;
    }

    public JButton getjButtonAlocarHospede() {
        return jButtonAlocarHospede;
    }

    public JButton getjButtonRelacionarHospede() {
        return jButtonRelacionarHospede;
    }

    public JComboBox<String> getjComboBoxTipoHospede() {
        return jComboBoxTipoHospede;
    }

    public JTextField getjTextFieldObsHospede() {
        return jTextFieldObsHospede;
    }

    public JFormattedTextField getjFormattedTextFieldHospede() {
        return jFormattedTextFieldHospede;
    }

    public JButton getjButtonBuscarQuarto() {
        return jButtonBuscarQuarto;
    }

    public JButton getjButtonAlocarVaga() {
        return jButtonAlocarVaga;
    }

    public JButton getjButtonRelacionarVaga() {
        return jButtonRelacionarVaga;
    }

    public JButton getjButtonRelacionarVeiculo() {
        return jButtonRelacionarVeiculo;
    }

    public JFormattedTextField getjTextFieldVeiculo() {
        return jFormattedTextFieldVeiculo;
    }

    public JFormattedTextField getjTextFieldVaga() {
        return jFormattedTextFieldVaga;
    }

    public JTextField getjTextFieldObsVaga() {
        return jTextFieldObsVaga;
    }

    public JComboBox<String> getjComboBoxStatusRecebimento() {
        return jComboBoxStatusRecebimento;
    }

    public JComboBox<String> getjComboBoxStatusReserva() {
        return jComboBoxStatusReserva;
    }

    public JFormattedTextField getjFormattedTextFieldDataCadastro() {
        return jFormattedTextFieldDataCadastro;
    }

    public JFormattedTextField getjFormattedTextFieldDataEntrada() {
        return jFormattedTextFieldDataEntrada;
    }

    public JFormattedTextField getjFormattedTextFieldDataEntradaReserva() {
        return jFormattedTextFieldDataEntradaReserva;
    }

    public JFormattedTextField getjFormattedTextFieldDataReserva() {
        return jFormattedTextFieldDataReserva;
    }

    public JFormattedTextField getjFormattedTextFieldDataSaida() {
        return jFormattedTextFieldDataSaida;
    }

    public JFormattedTextField getjFormattedTextFieldDataSaidaReserva() {
        return jFormattedTextFieldDataSaidaReserva;
    }

    public JTable getjTableHospedes() {
        return jTableHospedes;
    }

    public JTable getjTableQuartos() {
        return jTableQuartos;
    }

    public JTable getjTableVaga() {
        return jTableAlocacoesVagas;
    }

    public JTextField getjTextFieldAcrescimo() {
        return jTextFieldAcrescimo;
    }

    public JTextField getjTextFieldDesconto() {
        return jTextFieldDesconto;
    }

    public JTextField getjTextFieldIdReserva() {
        return jTextFieldIdReserva;
    }

    public JTextField getjTextFieldObsRecebimento() {
        return jTextFieldObsRecebimento;
    }

    public JTextField getjTextFieldObsReserva() {
        return jTextFieldObsReserva;
    }

    public JTextField getjTextFieldValorOriginal() {
        return jTextFieldValorOriginal;
    }

    public JTextField getjTextFieldValorPagar() {
        return jTextFieldValorPagar;
    }

    public JTextField getjTextFieldValorPago() {
        return jTextFieldValorPago;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitulo = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabelId = new javax.swing.JLabel();
        jButtonNovo = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jPanelBotoes = new javax.swing.JPanel();
        jButtonCancelar = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelCheck = new javax.swing.JPanel();
        jTextFieldObs = new javax.swing.JTextField();
        jLabelObs = new javax.swing.JLabel();
        jComboBoxStatus = new javax.swing.JComboBox<>();
        jLabelStatus = new javax.swing.JLabel();
        jFormattedTextFieldDataCadastro = new javax.swing.JFormattedTextField();
        jLabelDataCadastro = new javax.swing.JLabel();
        jFormattedTextFieldDataEntrada = new javax.swing.JFormattedTextField();
        jLabelDataEntrada = new javax.swing.JLabel();
        jFormattedTextFieldDataSaida = new javax.swing.JFormattedTextField();
        jLabelDataSaida = new javax.swing.JLabel();
        jPanelHospedes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableHospedes = new javax.swing.JTable();
        jButtonAlocarHospede = new javax.swing.JButton();
        jLabelHospede = new javax.swing.JLabel();
        jFormattedTextFieldHospede = new javax.swing.JFormattedTextField();
        jButtonRelacionarHospede = new javax.swing.JButton();
        jLabelTipoHospede = new javax.swing.JLabel();
        jLabelObsHospede = new javax.swing.JLabel();
        jTextFieldObsHospede = new javax.swing.JTextField();
        jComboBoxTipoHospede = new javax.swing.JComboBox<>();
        jPanelQuartos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableQuartos = new javax.swing.JTable();
        jButtonBuscarQuarto = new javax.swing.JButton();
        jPanelVaga = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableAlocacoesVagas = new javax.swing.JTable();
        jButtonAlocarVaga = new javax.swing.JButton();
        jFormattedTextFieldVaga = new javax.swing.JFormattedTextField();
        jButtonRelacionarVaga = new javax.swing.JButton();
        jLabelVaga = new javax.swing.JLabel();
        jLabelVeiculo = new javax.swing.JLabel();
        jFormattedTextFieldVeiculo = new javax.swing.JFormattedTextField();
        jButtonRelacionarVeiculo = new javax.swing.JButton();
        jTextFieldObsVaga = new javax.swing.JTextField();
        jLabelObsVaga = new javax.swing.JLabel();
        jPanelReserva = new javax.swing.JPanel();
        jTextFieldIdReserva = new javax.swing.JTextField();
        jLabelIdReserva = new javax.swing.JLabel();
        jFormattedTextFieldDataReserva = new javax.swing.JFormattedTextField();
        jLabelDataReserva = new javax.swing.JLabel();
        jFormattedTextFieldDataEntradaReserva = new javax.swing.JFormattedTextField();
        jLabelDataEntradaReserva = new javax.swing.JLabel();
        jFormattedTextFieldDataSaidaReserva = new javax.swing.JFormattedTextField();
        jLabelDataSaidaReserva = new javax.swing.JLabel();
        jComboBoxStatusReserva = new javax.swing.JComboBox<>();
        jLabelStatusReserva = new javax.swing.JLabel();
        jTextFieldObsReserva = new javax.swing.JTextField();
        jLabelObsReserva = new javax.swing.JLabel();
        jPanelRecebimento = new javax.swing.JPanel();
        jComboBoxStatusRecebimento = new javax.swing.JComboBox<>();
        jLabelStatusRecebimento = new javax.swing.JLabel();
        jTextFieldObsRecebimento = new javax.swing.JTextField();
        jLabelObsRecebimento = new javax.swing.JLabel();
        jTextFieldDesconto = new javax.swing.JTextField();
        jLabelValorOriginal = new javax.swing.JLabel();
        jLabelObsDesconto = new javax.swing.JLabel();
        jTextFieldValorOriginal = new javax.swing.JTextField();
        jTextFieldAcrescimo = new javax.swing.JTextField();
        jLabelAcrescimo = new javax.swing.JLabel();
        jLabelValorPagar = new javax.swing.JLabel();
        jTextFieldValorPago = new javax.swing.JTextField();
        jLabelValorPago = new javax.swing.JLabel();
        jTextFieldValorPagar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Hóspedes");
        setResizable(false);

        jPanelTitulo.setBackground(new java.awt.Color(153, 153, 255));
        jPanelTitulo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelTitulo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Check-in/Check-out");

        jTextFieldId.setEditable(false);
        jTextFieldId.setEnabled(false);

        jLabelId.setText("ID");

        jButtonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Create.png"))); // NOI18N
        jButtonNovo.setText("Novo");
        jButtonNovo.setActionCommand("0");

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Load.png"))); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setActionCommand("0");

        javax.swing.GroupLayout jPanelTituloLayout = new javax.swing.GroupLayout(jPanelTitulo);
        jPanelTitulo.setLayout(jPanelTituloLayout);
        jPanelTituloLayout.setHorizontalGroup(
            jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTituloLayout.createSequentialGroup()
                        .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBuscar))
                    .addComponent(jLabelId))
                .addContainerGap())
        );
        jPanelTituloLayout.setVerticalGroup(
            jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNovo)
                    .addComponent(jButtonBuscar))
                .addGap(0, 13, Short.MAX_VALUE))
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelBotoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Exit.png"))); // NOI18N
        jButtonSair.setText("Sair");
        jButtonSair.setActionCommand("0");
        jPanelBotoes.add(jButtonSair);

        jPanelCheck.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelObs.setText("OBS.:");

        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));
        jComboBoxStatus.setEnabled(false);

        jLabelStatus.setText("Status");

        jFormattedTextFieldDataCadastro.setEditable(false);
        try {
            jFormattedTextFieldDataCadastro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataCadastro.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabelDataCadastro.setText("Data de Cadastro");

        jFormattedTextFieldDataEntrada.setEditable(false);
        try {
            jFormattedTextFieldDataEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataEntrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldDataEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldDataEntradaActionPerformed(evt);
            }
        });

        jLabelDataEntrada.setText("Data de Entrada");

        jFormattedTextFieldDataSaida.setEditable(false);
        try {
            jFormattedTextFieldDataSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabelDataSaida.setText("Data de Saída");

        javax.swing.GroupLayout jPanelCheckLayout = new javax.swing.GroupLayout(jPanelCheck);
        jPanelCheck.setLayout(jPanelCheckLayout);
        jPanelCheckLayout.setHorizontalGroup(
            jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCheckLayout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addGroup(jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelObs)
                    .addGroup(jPanelCheckLayout.createSequentialGroup()
                        .addGroup(jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextFieldDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDataCadastro)
                            .addComponent(jFormattedTextFieldDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDataEntrada))
                        .addGap(32, 32, 32)
                        .addGroup(jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFormattedTextFieldDataSaida)
                            .addComponent(jLabelDataSaida)
                            .addComponent(jLabelStatus)
                            .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextFieldObs))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        jPanelCheckLayout.setVerticalGroup(
            jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCheckLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelCheckLayout.createSequentialGroup()
                        .addGroup(jPanelCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCheckLayout.createSequentialGroup()
                                .addComponent(jLabelDataCadastro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelCheckLayout.createSequentialGroup()
                                .addComponent(jLabelStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDataEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCheckLayout.createSequentialGroup()
                        .addComponent(jLabelDataSaida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelObs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Check", jPanelCheck);

        jPanelHospedes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableHospedes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Tipo", "Obs", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTableHospedes);
        if (jTableHospedes.getColumnModel().getColumnCount() > 0) {
            jTableHospedes.getColumnModel().getColumn(0).setMaxWidth(40);
            jTableHospedes.getColumnModel().getColumn(2).setMinWidth(150);
            jTableHospedes.getColumnModel().getColumn(2).setMaxWidth(150);
            jTableHospedes.getColumnModel().getColumn(4).setMinWidth(150);
            jTableHospedes.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jButtonAlocarHospede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Load.png"))); // NOI18N
        jButtonAlocarHospede.setText("Alocar Hóspede");
        jButtonAlocarHospede.setActionCommand("0");
        jButtonAlocarHospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlocarHospedeActionPerformed(evt);
            }
        });

        jLabelHospede.setText("Hóspede");

        jFormattedTextFieldHospede.setEditable(false);
        jFormattedTextFieldHospede.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        jFormattedTextFieldHospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldHospedeActionPerformed(evt);
            }
        });

        jButtonRelacionarHospede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Find.png"))); // NOI18N
        jButtonRelacionarHospede.setActionCommand("1");
        jButtonRelacionarHospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelacionarHospedeActionPerformed(evt);
            }
        });

        jLabelTipoHospede.setText("Tipo");

        jLabelObsHospede.setText("OBS.:");

        jComboBoxTipoHospede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Titular", "Acompanhante" }));
        jComboBoxTipoHospede.setSelectedIndex(-1);
        jComboBoxTipoHospede.setSelectedItem(-1);

        javax.swing.GroupLayout jPanelHospedesLayout = new javax.swing.GroupLayout(jPanelHospedes);
        jPanelHospedes.setLayout(jPanelHospedesLayout);
        jPanelHospedesLayout.setHorizontalGroup(
            jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHospedesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHospedesLayout.createSequentialGroup()
                        .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelHospedesLayout.createSequentialGroup()
                                .addComponent(jLabelHospede)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelHospedesLayout.createSequentialGroup()
                                        .addComponent(jFormattedTextFieldHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonRelacionarHospede)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelTipoHospede)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxTipoHospede, 0, 261, Short.MAX_VALUE))
                                    .addComponent(jTextFieldObsHospede))
                                .addGap(34, 34, 34))
                            .addGroup(jPanelHospedesLayout.createSequentialGroup()
                                .addComponent(jLabelObsHospede)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jButtonAlocarHospede)))
                .addContainerGap())
        );
        jPanelHospedesLayout.setVerticalGroup(
            jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHospedesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelTipoHospede)
                        .addComponent(jComboBoxTipoHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextFieldHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelHospede))
                    .addComponent(jButtonRelacionarHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHospedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldObsHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelObsHospede)
                    .addComponent(jButtonAlocarHospede))
                .addGap(13, 13, 13))
        );

        jTabbedPane1.addTab("Hóspedes", jPanelHospedes);

        jPanelQuartos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableQuartos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "Status"
            }
        ));
        jScrollPane2.setViewportView(jTableQuartos);
        if (jTableQuartos.getColumnModel().getColumnCount() > 0) {
            jTableQuartos.getColumnModel().getColumn(0).setMaxWidth(40);
            jTableQuartos.getColumnModel().getColumn(3).setMinWidth(100);
            jTableQuartos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jButtonBuscarQuarto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Load.png"))); // NOI18N
        jButtonBuscarQuarto.setActionCommand("0");
        jButtonBuscarQuarto.setLabel("Buscar Quarto");
        jButtonBuscarQuarto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarQuartoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelQuartosLayout = new javax.swing.GroupLayout(jPanelQuartos);
        jPanelQuartos.setLayout(jPanelQuartosLayout);
        jPanelQuartosLayout.setHorizontalGroup(
            jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuartosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelQuartosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonBuscarQuarto)))
                .addContainerGap())
        );
        jPanelQuartosLayout.setVerticalGroup(
            jPanelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuartosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscarQuarto)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Quartos", jPanelQuartos);

        jPanelVaga.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableAlocacoesVagas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Vaga", "Placa Veiculo", "Obs", "Status"
            }
        ));
        jScrollPane3.setViewportView(jTableAlocacoesVagas);
        if (jTableAlocacoesVagas.getColumnModel().getColumnCount() > 0) {
            jTableAlocacoesVagas.getColumnModel().getColumn(0).setMaxWidth(100);
            jTableAlocacoesVagas.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        jButtonAlocarVaga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Load.png"))); // NOI18N
        jButtonAlocarVaga.setText("Alocar Vaga");
        jButtonAlocarVaga.setActionCommand("0");
        jButtonAlocarVaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlocarVagaActionPerformed(evt);
            }
        });

        jFormattedTextFieldVaga.setEditable(false);
        jFormattedTextFieldVaga.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        jFormattedTextFieldVaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldVagaActionPerformed(evt);
            }
        });

        jButtonRelacionarVaga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Find.png"))); // NOI18N
        jButtonRelacionarVaga.setActionCommand("1");
        jButtonRelacionarVaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelacionarVagaActionPerformed(evt);
            }
        });

        jLabelVaga.setText("Vaga");

        jLabelVeiculo.setText("Veiculo");

        jFormattedTextFieldVeiculo.setEditable(false);
        jFormattedTextFieldVeiculo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        jFormattedTextFieldVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldVeiculoActionPerformed(evt);
            }
        });

        jButtonRelacionarVeiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Find.png"))); // NOI18N
        jButtonRelacionarVeiculo.setActionCommand("1");
        jButtonRelacionarVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelacionarVeiculoActionPerformed(evt);
            }
        });

        jLabelObsVaga.setText("OBS.:");

        javax.swing.GroupLayout jPanelVagaLayout = new javax.swing.GroupLayout(jPanelVaga);
        jPanelVaga.setLayout(jPanelVagaLayout);
        jPanelVagaLayout.setHorizontalGroup(
            jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVagaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                    .addGroup(jPanelVagaLayout.createSequentialGroup()
                        .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelObsVaga)
                            .addGroup(jPanelVagaLayout.createSequentialGroup()
                                .addComponent(jLabelVeiculo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelVagaLayout.createSequentialGroup()
                                        .addComponent(jFormattedTextFieldVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonRelacionarVeiculo)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelVaga)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jFormattedTextFieldVaga)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonRelacionarVaga))
                                    .addComponent(jTextFieldObsVaga))))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAlocarVaga)))
                .addContainerGap())
        );
        jPanelVagaLayout.setVerticalGroup(
            jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVagaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextFieldVaga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelVaga))
                    .addComponent(jButtonRelacionarVaga, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextFieldVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelVeiculo))
                    .addComponent(jButtonRelacionarVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelVagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldObsVaga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelObsVaga)
                    .addComponent(jButtonAlocarVaga))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Vagas", jPanelVaga);

        jPanelReserva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextFieldIdReserva.setEnabled(false);

        jLabelIdReserva.setText("ID");

        try {
            jFormattedTextFieldDataReserva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataReserva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldDataReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldDataReservaActionPerformed(evt);
            }
        });

        jLabelDataReserva.setText("Data da Reserva");

        try {
            jFormattedTextFieldDataEntradaReserva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataEntradaReserva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldDataEntradaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldDataEntradaReservaActionPerformed(evt);
            }
        });

        jLabelDataEntradaReserva.setText("Previsão de Entrada");

        try {
            jFormattedTextFieldDataSaidaReserva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataSaidaReserva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldDataSaidaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldDataSaidaReservaActionPerformed(evt);
            }
        });

        jLabelDataSaidaReserva.setText("Previsão de Saída");

        jComboBoxStatusReserva.setEditable(true);
        jComboBoxStatusReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));
        jComboBoxStatusReserva.setEnabled(false);

        jLabelStatusReserva.setText("Status");

        jLabelObsReserva.setText("OBS.:");

        javax.swing.GroupLayout jPanelReservaLayout = new javax.swing.GroupLayout(jPanelReserva);
        jPanelReserva.setLayout(jPanelReservaLayout);
        jPanelReservaLayout.setHorizontalGroup(
            jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReservaLayout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelIdReserva)
                    .addComponent(jTextFieldIdReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanelReservaLayout.createSequentialGroup()
                            .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextFieldDataReserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextFieldDataEntradaReserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelDataReserva)
                                .addComponent(jLabelDataEntradaReserva))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jFormattedTextFieldDataSaidaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelDataSaidaReserva)
                                .addComponent(jLabelStatusReserva)
                                .addComponent(jComboBoxStatusReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelObsReserva)
                            .addComponent(jTextFieldObsReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanelReservaLayout.setVerticalGroup(
            jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReservaLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabelIdReserva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldIdReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelReservaLayout.createSequentialGroup()
                        .addComponent(jLabelDataReserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDataEntradaReserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataEntradaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelReservaLayout.createSequentialGroup()
                        .addComponent(jLabelStatusReserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxStatusReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDataSaidaReserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataSaidaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelObsReserva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldObsReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reserva", jPanelReserva);

        jPanelRecebimento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBoxStatusRecebimento.setEditable(true);
        jComboBoxStatusRecebimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));
        jComboBoxStatusRecebimento.setEnabled(false);

        jLabelStatusRecebimento.setText("Status");

        jTextFieldObsRecebimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldObsRecebimentoActionPerformed(evt);
            }
        });

        jLabelObsRecebimento.setText("OBS.:");

        jTextFieldDesconto.setText("R$");

        jLabelValorOriginal.setText("Valor original");

        jLabelObsDesconto.setText("Desconto");

        jTextFieldValorOriginal.setText("R$");
        jTextFieldValorOriginal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldValorOriginalActionPerformed(evt);
            }
        });

        jTextFieldAcrescimo.setText("R$");
        jTextFieldAcrescimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAcrescimoActionPerformed(evt);
            }
        });

        jLabelAcrescimo.setText("Acréscimo");

        jLabelValorPagar.setText("Valor a pagar");

        jTextFieldValorPago.setText("R$");

        jLabelValorPago.setText("Valor pago");

        jTextFieldValorPagar.setEditable(false);
        jTextFieldValorPagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextFieldValorPagar.setForeground(new java.awt.Color(0, 103, 37));
        jTextFieldValorPagar.setText("R$");

        javax.swing.GroupLayout jPanelRecebimentoLayout = new javax.swing.GroupLayout(jPanelRecebimento);
        jPanelRecebimento.setLayout(jPanelRecebimentoLayout);
        jPanelRecebimentoLayout.setHorizontalGroup(
            jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRecebimentoLayout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                        .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelValorPagar)
                            .addComponent(jTextFieldValorPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                                .addComponent(jLabelValorPago)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldValorPago)))
                    .addComponent(jTextFieldObsRecebimento)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelObsRecebimento)
                        .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                            .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldDesconto, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldValorOriginal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelValorOriginal, javax.swing.GroupLayout.Alignment.LEADING))
                                .addComponent(jLabelObsDesconto))
                            .addGap(20, 20, 20)
                            .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabelStatusRecebimento)
                                .addComponent(jLabelAcrescimo)
                                .addComponent(jTextFieldAcrescimo)
                                .addComponent(jComboBoxStatusRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(256, 256, 256))
        );
        jPanelRecebimentoLayout.setVerticalGroup(
            jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                        .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxStatusRecebimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                                .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelValorOriginal)
                                    .addComponent(jLabelStatusRecebimento))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldValorOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelObsDesconto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                        .addComponent(jLabelAcrescimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAcrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelObsRecebimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldObsRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelValorPagar)
                        .addComponent(jLabelValorPago))
                    .addGroup(jPanelRecebimentoLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanelRecebimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldValorPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Recebimento", jPanelRecebimento);

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

    private void jFormattedTextFieldDataSaidaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDataSaidaReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldDataSaidaReservaActionPerformed

    private void jFormattedTextFieldDataEntradaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDataEntradaReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldDataEntradaReservaActionPerformed

    private void jFormattedTextFieldDataReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDataReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldDataReservaActionPerformed

    private void jButtonAlocarHospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlocarHospedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAlocarHospedeActionPerformed

    private void jFormattedTextFieldDataEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDataEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldDataEntradaActionPerformed

    private void jButtonAlocarVagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlocarVagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAlocarVagaActionPerformed

    private void jTextFieldAcrescimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAcrescimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAcrescimoActionPerformed

    private void jTextFieldValorOriginalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldValorOriginalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldValorOriginalActionPerformed

    private void jTextFieldObsRecebimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldObsRecebimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldObsRecebimentoActionPerformed

    private void jButtonBuscarQuartoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarQuartoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscarQuartoActionPerformed

    private void jFormattedTextFieldVagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldVagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldVagaActionPerformed

    private void jButtonRelacionarVagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelacionarVagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRelacionarVagaActionPerformed

    private void jFormattedTextFieldVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldVeiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldVeiculoActionPerformed

    private void jButtonRelacionarVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelacionarVeiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRelacionarVeiculoActionPerformed

    private void jFormattedTextFieldHospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldHospedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldHospedeActionPerformed

    private void jButtonRelacionarHospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelacionarHospedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRelacionarHospedeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TemplateCadastros2025.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TemplateCadastros2025.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TemplateCadastros2025.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TemplateCadastros2025.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaCheck dialog = new TelaCheck(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonAlocarHospede;
    private javax.swing.JButton jButtonAlocarVaga;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonBuscarQuarto;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonRelacionarHospede;
    private javax.swing.JButton jButtonRelacionarVaga;
    private javax.swing.JButton jButtonRelacionarVeiculo;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JComboBox<String> jComboBoxStatus;
    private javax.swing.JComboBox<String> jComboBoxStatusRecebimento;
    private javax.swing.JComboBox<String> jComboBoxStatusReserva;
    private javax.swing.JComboBox<String> jComboBoxTipoHospede;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataCadastro;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataEntrada;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataEntradaReserva;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataReserva;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataSaida;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataSaidaReserva;
    private javax.swing.JFormattedTextField jFormattedTextFieldHospede;
    private javax.swing.JFormattedTextField jFormattedTextFieldVaga;
    private javax.swing.JFormattedTextField jFormattedTextFieldVeiculo;
    private javax.swing.JLabel jLabelAcrescimo;
    private javax.swing.JLabel jLabelDataCadastro;
    private javax.swing.JLabel jLabelDataEntrada;
    private javax.swing.JLabel jLabelDataEntradaReserva;
    private javax.swing.JLabel jLabelDataReserva;
    private javax.swing.JLabel jLabelDataSaida;
    private javax.swing.JLabel jLabelDataSaidaReserva;
    private javax.swing.JLabel jLabelHospede;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelIdReserva;
    private javax.swing.JLabel jLabelObs;
    private javax.swing.JLabel jLabelObsDesconto;
    private javax.swing.JLabel jLabelObsHospede;
    private javax.swing.JLabel jLabelObsRecebimento;
    private javax.swing.JLabel jLabelObsReserva;
    private javax.swing.JLabel jLabelObsVaga;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelStatusRecebimento;
    private javax.swing.JLabel jLabelStatusReserva;
    private javax.swing.JLabel jLabelTipoHospede;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVaga;
    private javax.swing.JLabel jLabelValorOriginal;
    private javax.swing.JLabel jLabelValorPagar;
    private javax.swing.JLabel jLabelValorPago;
    private javax.swing.JLabel jLabelVeiculo;
    private javax.swing.JPanel jPanelBotoes;
    private javax.swing.JPanel jPanelCheck;
    private javax.swing.JPanel jPanelHospedes;
    private javax.swing.JPanel jPanelQuartos;
    private javax.swing.JPanel jPanelRecebimento;
    private javax.swing.JPanel jPanelReserva;
    private javax.swing.JPanel jPanelTitulo;
    private javax.swing.JPanel jPanelVaga;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAlocacoesVagas;
    private javax.swing.JTable jTableHospedes;
    private javax.swing.JTable jTableQuartos;
    private javax.swing.JTextField jTextFieldAcrescimo;
    private javax.swing.JTextField jTextFieldDesconto;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldIdReserva;
    private javax.swing.JTextField jTextFieldObs;
    private javax.swing.JTextField jTextFieldObsHospede;
    private javax.swing.JTextField jTextFieldObsRecebimento;
    private javax.swing.JTextField jTextFieldObsReserva;
    private javax.swing.JTextField jTextFieldObsVaga;
    private javax.swing.JTextField jTextFieldValorOriginal;
    private javax.swing.JTextField jTextFieldValorPagar;
    private javax.swing.JTextField jTextFieldValorPago;
    // End of variables declaration//GEN-END:variables
}
