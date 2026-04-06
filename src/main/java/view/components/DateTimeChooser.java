package view.components;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DateTimeChooser extends JDialog {

    private LocalDateTime dateTime;
    private JLabel labelMonthYear;
    private JPanel panelDays;
    private JSpinner spinnerHour;
    private JSpinner spinnerMinute;
    private final boolean showTime;
    private boolean confirmed = false;

    public DateTimeChooser(Window parent, LocalDateTime initialDateTime, boolean showTime) {
        super(parent, showTime ? "Selecionar Data e Hora" : "Selecionar Data", DEFAULT_MODALITY_TYPE);
        this.showTime = showTime;
        this.dateTime = (initialDateTime != null) ? initialDateTime : LocalDateTime.now();
        initComponents();
        updateCalendar();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        // Header: Month/Year navigation
        JPanel panelHeader = new JPanel(new BorderLayout());
        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        labelMonthYear = new JLabel("", SwingConstants.CENTER);
        labelMonthYear.setFont(new Font("SansSerif", Font.BOLD, 14));

        btnPrev.addActionListener(e -> {
            dateTime = dateTime.minusMonths(1);
            updateCalendar();
        });
        btnNext.addActionListener(e -> {
            dateTime = dateTime.plusMonths(1);
            updateCalendar();
        });

        panelHeader.add(btnPrev, BorderLayout.WEST);
        panelHeader.add(labelMonthYear, BorderLayout.CENTER);
        panelHeader.add(btnNext, BorderLayout.EAST);
        panelHeader.setBorder(new EmptyBorder(10, 10, 5, 10));

        // Center: Days Grid
        panelDays = new JPanel(new GridLayout(0, 7, 2, 2));
        panelDays.setBorder(new EmptyBorder(5, 10, 5, 10));
        panelDays.setPreferredSize(new Dimension(400, 220));

        // Footer: Time and Buttons
        JPanel panelFooter = new JPanel(new GridBagLayout());
        panelFooter.setBorder(new EmptyBorder(5, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if (showTime) {
            gbc.gridx = 0; gbc.gridy = 0;
            panelFooter.add(new JLabel("Hora:"), gbc);

            spinnerHour = new JSpinner(new SpinnerNumberModel(dateTime.getHour(), 0, 23, 1));
            gbc.gridx = 1;
            panelFooter.add(spinnerHour, gbc);

            gbc.gridx = 2;
            panelFooter.add(new JLabel("Min:"), gbc);

            spinnerMinute = new JSpinner(new SpinnerNumberModel(dateTime.getMinute(), 0, 59, 1));
            gbc.gridx = 3;
            panelFooter.add(spinnerMinute, gbc);
        }

        JButton btnOk = new JButton("OK");
        btnOk.setBackground(new Color(100, 150, 255));
        btnOk.setForeground(Color.WHITE);
        btnOk.addActionListener(e -> {
            confirmed = true;
            if (showTime) {
                dateTime = dateTime.withHour((int) spinnerHour.getValue())
                                 .withMinute((int) spinnerMinute.getValue());
            }
            dispose();
        });
        gbc.gridx = 4;
        panelFooter.add(btnOk, gbc);

        add(panelHeader, BorderLayout.NORTH);
        add(panelDays, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);
    }

    private void updateCalendar() {
        labelMonthYear.setText(getMonthName(dateTime.getMonthValue()) + " " + dateTime.getYear());
        panelDays.removeAll();

        // Week labels
        String[] weeks = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
        for (String day : weeks) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            panelDays.add(lbl);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(dateTime.getYear(), dateTime.getMonthValue() - 1, 1);
        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Blank days
        for (int i = 1; i < startDay; i++) {
            panelDays.add(new JLabel(""));
        }

        // Days
        for (int i = 1; i <= daysInMonth; i++) {
            final int day = i;
            JButton btnDay = new JButton(String.valueOf(i));
            btnDay.setMargin(new Insets(2, 2, 2, 2));
            if (i == dateTime.getDayOfMonth()) {
                btnDay.setBackground(new Color(200, 220, 255));
            } else {
                btnDay.setBackground(Color.WHITE);
            }
            
            btnDay.addActionListener(e -> {
                dateTime = dateTime.withDayOfMonth(day);
                updateCalendar();
            });
            panelDays.add(btnDay);
        }

        panelDays.revalidate();
        panelDays.repaint();
    }

    private String getMonthName(int month) {
        String[] months = {
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        return months[month - 1];
    }

    public LocalDateTime getSelectedDateTime() {
        return confirmed ? dateTime : null;
    }

    public static LocalDateTime showDialog(Window parent, LocalDateTime initial, boolean showTime) {
        DateTimeChooser chooser = new DateTimeChooser(parent, initial, showTime);
        chooser.setVisible(true);
        return chooser.getSelectedDateTime();
    }
}
