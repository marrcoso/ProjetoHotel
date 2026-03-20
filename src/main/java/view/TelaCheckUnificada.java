package view;

import javax.swing.*;
import java.awt.*;

public class TelaCheckUnificada extends TemplateCadastros2025 {

    private JTabbedPane tabbedPane;
    
    // Componentes da aba Check
    private JTextField jTextFieldCheckId;
    private JTextField jTextFieldDataEntrada;
    private JTextField jTextFieldDataSaida;
    private JComboBox<String> jComboBoxStatusCheck;
    
    // Componentes da aba Check Quarto
    private JTextField jTextFieldQuartoId;
    private JTextField jTextFieldQuartoDescricao;
    private JTextField jTextFieldDataInicioQuarto;
    private JTextField jTextFieldDataFimQuarto;
    
    // Componentes da aba Check Hospede
    private JTable jTableHospedes;
    private JButton jButtonAdicionarHospede;
    private JButton jButtonRemoverHospede;

    public TelaCheckUnificada(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponentsCustom();
    }

    private void initComponentsCustom() {
        setTitle("Gerenciamento Unificado de Check");
        
        tabbedPane = new JTabbedPane();
        
        // --- Aba 1: Dados do Check ---
        JPanel panelCheck = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCheck.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCheck.add(new JLabel("ID Check:"));
        jTextFieldCheckId = new JTextField();
        jTextFieldCheckId.setEditable(false);
        panelCheck.add(jTextFieldCheckId);
        
        panelCheck.add(new JLabel("Data Entrada:"));
        jTextFieldDataEntrada = new JTextField();
        panelCheck.add(jTextFieldDataEntrada);
        
        panelCheck.add(new JLabel("Data Saída:"));
        jTextFieldDataSaida = new JTextField();
        panelCheck.add(jTextFieldDataSaida);
        
        panelCheck.add(new JLabel("Status:"));
        jComboBoxStatusCheck = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        panelCheck.add(jComboBoxStatusCheck);
        
        tabbedPane.addTab("Check Principal", panelCheck);
        
        // --- Aba 2: Check Quarto ---
        JPanel panelQuarto = new JPanel(new GridLayout(4, 2, 10, 10));
        panelQuarto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelQuarto.add(new JLabel("Quarto:"));
        jTextFieldQuartoDescricao = new JTextField();
        jTextFieldQuartoDescricao.setEditable(false);
        panelQuarto.add(jTextFieldQuartoDescricao);
        
        panelQuarto.add(new JLabel("Data Início:"));
        jTextFieldDataInicioQuarto = new JTextField();
        panelQuarto.add(jTextFieldDataInicioQuarto);
        
        panelQuarto.add(new JLabel("Data Fim:"));
        jTextFieldDataFimQuarto = new JTextField();
        panelQuarto.add(jTextFieldDataFimQuarto);
        
        tabbedPane.addTab("Quarto", panelQuarto);
        
        // --- Aba 3: Check Hospedes ---
        JPanel panelHospedes = new JPanel(new BorderLayout(10, 10));
        panelHospedes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        jTableHospedes = new JTable(); // Simplificado para exemplo
        JScrollPane scrollPaneHospedes = new JScrollPane(jTableHospedes);
        panelHospedes.add(scrollPaneHospedes, BorderLayout.CENTER);
        
        JPanel panelBotoesHospede = new JPanel();
        jButtonAdicionarHospede = new JButton("Adicionar Hóspede");
        jButtonRemoverHospede = new JButton("Remover Hóspede");
        panelBotoesHospede.add(jButtonAdicionarHospede);
        panelBotoesHospede.add(jButtonRemoverHospede);
        panelHospedes.add(panelBotoesHospede, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Hóspedes", panelHospedes);
        
        // Adiciona o TabbedPane ao painel de dados do template
        getjPanelDados().setLayout(new BorderLayout());
        getjPanelDados().add(tabbedPane, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
    }

    // Getters para os componentes (seguindo o padrão do projeto)
    public JTextField getjTextFieldCheckId() { return jTextFieldCheckId; }
    public JTextField getjTextFieldDataEntrada() { return jTextFieldDataEntrada; }
    public JTextField getjTextFieldDataSaida() { return jTextFieldDataSaida; }
    public JComboBox<String> getjComboBoxStatusCheck() { return jComboBoxStatusCheck; }
    public JTable getjTableHospedes() { return jTableHospedes; }
    public JButton getjButtonAdicionarHospede() { return jButtonAdicionarHospede; }
    public JButton getjButtonRemoverHospede() { return jButtonRemoverHospede; }
}
