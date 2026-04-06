package view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utilities.Utilities;

public class DateTimePicker extends JPanel {

    private JFormattedTextField textField;
    private JButton button;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public DateTimePicker() {
        this(true);
    }

    public DateTimePicker(boolean showButton) {
        initComponents(showButton);
    }

    private void initComponents(boolean showButton) {
        setLayout(new BorderLayout());

        textField = new JFormattedTextField(Utilities.getMascaraDataHora());
        textField.setColumns(14);
        textField.setHorizontalAlignment(JFormattedTextField.CENTER);

        button = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(30, 20));

        button.addActionListener(e -> {
            LocalDateTime initial = null;
            String text = textField.getText().replace("/", "").replace(":", "").trim();
            if (text.length() == 12) { // ddMMyyyyHHmm
                try {
                    initial = LocalDateTime.parse(textField.getText(), formatter);
                } catch (Exception ex) {}
            }
            if (initial == null) initial = LocalDateTime.now();

            Window parent = SwingUtilities.getWindowAncestor(this);
            LocalDateTime selected = DateTimeChooser.showDialog(parent, initial);
            if (selected != null) {
                textField.setText(selected.format(formatter));
            }
        });

        add(textField, BorderLayout.CENTER);
        if (showButton) {
            add(button, BorderLayout.EAST);
        }
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public String getText() {
        return textField.getText();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
        button.setEnabled(enabled);
    }

    public void setEditable(boolean editable) {
        textField.setEditable(editable);
        button.setEnabled(editable);
    }

    public JFormattedTextField getTextField() {
        return textField;
    }

    public JButton getButton() {
        return button;
    }
}
