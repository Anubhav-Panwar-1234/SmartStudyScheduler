import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchedulerGUI extends JFrame {

    private JTextField subjectField;
    private JTextField dateField;
    private JComboBox<String> difficultyBox;
    private JTextField hoursField;
    private DefaultTableModel tableModel;

    private List<Subject> subjects = new ArrayList<>();

    public SchedulerGUI() {

        setTitle("SmartStudyScheduler");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(new Color(230, 236, 242));

        // ===== TITLE =====
        JLabel titleLabel = new JLabel("Smart Study Scheduler", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 60, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ===== LEFT PANEL (CARD STYLE) =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        subjectField = new JTextField();
        dateField = new JTextField("YYYY-MM-DD");
        difficultyBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        hoursField = new JTextField();

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        addField(leftPanel, gbc, 0, "Subject Name:", subjectField, labelFont);
        addField(leftPanel, gbc, 1, "Exam Date:", dateField, labelFont);
        addField(leftPanel, gbc, 2, "Difficulty:", difficultyBox, labelFont);
        addField(leftPanel, gbc, 3, "Daily Study Hours:", hoursField, labelFont);

        // Buttons
        JButton addButton = new JButton("Add Subject");
        JButton generateButton = new JButton("Generate Schedule");

        styleButton(addButton, new Color(76, 175, 80));
        styleButton(generateButton, new Color(33, 150, 243));

        gbc.gridx = 0;
        gbc.gridy = 4;
        leftPanel.add(addButton, gbc);

        gbc.gridx = 1;
        leftPanel.add(generateButton, gbc);

        add(leftPanel, BorderLayout.WEST);

        // ===== TABLE SECTION =====
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Date");
        tableModel.addColumn("Subject");
        tableModel.addColumn("Hours");

        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(220, 220, 220));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(25, 60, 102));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                "Generated Study Plan",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(25, 60, 102)
        ));

        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        addButton.addActionListener(e -> addSubject());
        generateButton.addActionListener(e -> generateSchedule());
    }

    private void addField(JPanel panel, GridBagConstraints gbc,
                          int y, String labelText, JComponent field, Font font) {

        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(160, 40));
    }

    private void addSubject() {
        try {
            String name = subjectField.getText();
            LocalDate examDate = LocalDate.parse(dateField.getText());

            int difficulty = 1;
            if (difficultyBox.getSelectedItem().equals("Medium"))
                difficulty = 2;
            else if (difficultyBox.getSelectedItem().equals("Hard"))
                difficulty = 3;

            subjects.add(new Subject(name, examDate, difficulty));

            JOptionPane.showMessageDialog(this, "Subject Added Successfully!");

            subjectField.setText("");
            dateField.setText("YYYY-MM-DD");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Date Format! Use YYYY-MM-DD");
        }
    }

    private void generateSchedule() {
        try {
            double dailyHours = Double.parseDouble(hoursField.getText());
            tableModel.setRowCount(0);

            List<ScheduleEntry> schedule =
                    SchedulerLogic.generateSchedule(subjects, dailyHours);

            for (ScheduleEntry entry : schedule) {
                tableModel.addRow(new Object[]{
                        entry.getDate(),
                        entry.getSubjectName(),
                        entry.getHours()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid daily hours!");
        }
    }
}
