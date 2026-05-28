import javax.swing.*;
import javax.swing.table.*;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.*;

public class GUI_Module extends JFrame {

    JTextField subjectField, hoursField;

    JComboBox<String> difficultyBox;

    JButton generateButton;
    JButton clearButton;
    JButton deleteButton;
    JButton editButton;
    JButton chatButton;

    JTable table;

    DefaultTableModel tableModel;

    JLabel totalSubjectsLabel;
    JLabel dailyHoursLabel;
    JLabel upcomingExamsLabel;

    int selectedRow = -1;

    public GUI_Module() {

        setTitle("Smart Study Scheduler");

        ImageIcon icon =
                new ImageIcon("icon.png");

        setIconImage(icon.getImage());

        try {

                Taskbar.getTaskbar().setIconImage(
                        icon.getImage()
                );

        }

        catch (Exception e) {

        }

        setSize(1280, 760);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        Color background = new Color(15, 15, 15);

        Color panelColor = new Color(28, 28, 28);

        Color cardColor = new Color(35, 35, 35);

        Color fieldColor = new Color(45, 45, 45);

        Color accent = new Color(0, 153, 255);

        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(background);

        mainPanel.setLayout(null);

        JPanel header = new JPanel();

        header.setBounds(30, 20, 1180, 75);

        header.setBackground(panelColor);

        header.setLayout(null);

        JLabel heading =
                new JLabel("Smart Study Scheduler Dashboard");

        heading.setBounds(35, 18, 700, 35);

        heading.setForeground(Color.WHITE);

        heading.setFont(
                new Font("Arial", Font.BOLD, 34)
        );

        header.add(heading);

        chatButton =
                createButton(
                        "AI Assistant",
                        accent
                );

        chatButton.setBounds(
                980,
                18,
                170,
                40
        );

        header.add(chatButton);

        JPanel dashboardPanel = new JPanel();

        dashboardPanel.setBounds(30, 120, 280, 310);

        dashboardPanel.setBackground(panelColor);

        dashboardPanel.setLayout(null);

        JPanel card1 =
                createCard(20, 20, 240, 75, cardColor);

        JPanel card2 =
                createCard(20, 115, 240, 75, cardColor);

        JPanel card3 =
                createCard(20, 210, 240, 75, cardColor);

        totalSubjectsLabel = createCardValue("0");

        dailyHoursLabel = createCardValue("0");

        upcomingExamsLabel = createCardValue("0");

        card1.add(createCardTitle("Total Subjects"));

        card1.add(totalSubjectsLabel);

        card2.add(createCardTitle("Daily Study Hours"));

        card2.add(dailyHoursLabel);

        card3.add(createCardTitle("Upcoming Exams"));

        card3.add(upcomingExamsLabel);

        dashboardPanel.add(card1);

        dashboardPanel.add(card2);

        dashboardPanel.add(card3);

        JPanel inputPanel = new JPanel();

        inputPanel.setBounds(340, 120, 870, 310);

        inputPanel.setBackground(panelColor);

        inputPanel.setLayout(null);

        JLabel formTitle =
                new JLabel("Study Details");

        formTitle.setBounds(35, 20, 300, 30);

        formTitle.setForeground(accent);

        formTitle.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        inputPanel.add(formTitle);

        inputPanel.add(
                createLabel(
                        "Subject Name",
                        60,
                        90
                )
        );

        inputPanel.add(
                createLabel(
                        "Exam Date",
                        60,
                        165
                )
        );

        inputPanel.add(
                createLabel(
                        "Difficulty",
                        470,
                        90
                )
        );

        inputPanel.add(
                createLabel(
                        "Daily Hours",
                        470,
                        165
                )
        );

        subjectField =
                createTextField(60, 120);

        SpinnerDateModel dateModel =
                new SpinnerDateModel();

        JSpinner dateSpinner =
                new JSpinner(dateModel);

        dateSpinner.setBounds(
                60,
                195,
                280,
                38
        );

        JSpinner.DateEditor editor =
                new JSpinner.DateEditor(
                        dateSpinner,
                        "dd-MM-yyyy"
                );

        dateSpinner.setEditor(editor);

        inputPanel.add(dateSpinner);

        hoursField =
                createTextField(470, 195);

        String[] levels = {

                "Easy",
                "Medium",
                "Hard"
        };

        difficultyBox =
                new JComboBox<>(levels);

        difficultyBox.setBounds(
                470,
                120,
                280,
                38
        );

        difficultyBox.setBackground(fieldColor);

        difficultyBox.setForeground(Color.WHITE);

        difficultyBox.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        difficultyBox.setRenderer(

                new DefaultListCellRenderer() {

                    @Override
                    public Component
                    getListCellRendererComponent(

                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus

                    ) {

                        Component c =
                                super.getListCellRendererComponent(

                                        list,
                                        value,
                                        index,
                                        isSelected,
                                        cellHasFocus
                                );

                        c.setBackground(
                                new Color(45, 45, 45)
                        );

                        c.setForeground(Color.WHITE);

                        return c;
                    }
                }
        );

        generateButton =
                createButton(
                        "Generate Schedule",
                        accent
                );

        generateButton.setBounds(
                70,
                255,
                200,
                45
        );

        editButton =
                createButton(
                        "Edit Subject",
                        new Color(255, 170, 0)
                );

        editButton.setBounds(
                290,
                255,
                170,
                45
        );

        deleteButton =
                createButton(
                        "Delete Subject",
                        new Color(255, 80, 80)
                );

        deleteButton.setBounds(
                480,
                255,
                170,
                45
        );

        clearButton =
                createButton(
                        "Clear Schedule",
                        new Color(120, 120, 120)
                );

        clearButton.setBounds(
                670,
                255,
                170,
                45
        );

        inputPanel.add(subjectField);

        inputPanel.add(hoursField);

        inputPanel.add(difficultyBox);

        inputPanel.add(generateButton);

        inputPanel.add(editButton);

        inputPanel.add(deleteButton);

        inputPanel.add(clearButton);

        JPanel tablePanel = new JPanel();

        tablePanel.setBounds(30, 460, 1180, 230);

        tablePanel.setBackground(panelColor);

        tablePanel.setLayout(new BorderLayout());

        String[] columns = {

                "Date",
                "Subject",
                "Hours",
                "Priority",
                "Completed"
        };

        tableModel =
                new DefaultTableModel(columns, 0);

        table = new JTable(tableModel) {
                @Override
                public Class<?> getColumnClass(int column) {
                    return column == 4 ? Boolean.class : String.class;
                }
        };

        tableModel.addTableModelListener(e -> {

                int row = e.getFirstRow();

                int column = e.getColumn();

                if (column == 4 && row >= 0) {

                        boolean completed =
                                (boolean) tableModel.getValueAt(
                                        row,
                                        4
                                );

                        Integration_Module.subjects
                                .get(row)[3] =
                                String.valueOf(completed);

                        Integration_Module.saveData();
                }
        });

        table.setBackground(
                new Color(35, 35, 35)
        );

        table.setForeground(Color.WHITE);

        table.setGridColor(
                new Color(55, 55, 55)
        );

        table.setSelectionBackground(
                new Color(0, 153, 255)
        );

        table.setSelectionForeground(Color.WHITE);

        table.setRowHeight(38);

        table.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        table.setShowVerticalLines(false);

        table.setIntercellSpacing(
                new Dimension(0, 1)
        );

        table.setFillsViewportHeight(true);

        JTableHeader tableHeader =
                table.getTableHeader();

        tableHeader.setBackground(accent);

        tableHeader.setForeground(Color.WHITE);

        tableHeader.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.getViewport().setBackground(
                new Color(35, 35, 35)
        );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(header);

        mainPanel.add(dashboardPanel);

        mainPanel.add(inputPanel);

        mainPanel.add(tablePanel);

        add(mainPanel);

        Integration_Module.loadData(

                4,

                tableModel,

                totalSubjectsLabel,
                dailyHoursLabel,
                upcomingExamsLabel
        );

        Integration_Module.checkReminders();

        generateButton.addActionListener(e -> {

            try {

                String subject =
                        subjectField
                                .getText()
                                .trim();

                String examDate =
                        ((JSpinner.DateEditor)
                                dateSpinner.getEditor())

                                .getFormat()

                                .format(
                                        dateSpinner.getValue()
                                );

                String difficulty =
                        difficultyBox
                                .getSelectedItem()
                                .toString();

                int dailyHours =
                        Integer.parseInt(

                                hoursField
                                        .getText()
                                        .trim()
                        );

                Integration_Module.addSubject(

                        subject,
                        examDate,
                        difficulty,
                        dailyHours,

                        tableModel,

                        totalSubjectsLabel,
                        dailyHoursLabel,
                        upcomingExamsLabel
                );

                subjectField.setText("");

                hoursField.setText("");

            }

            catch (Exception ex) {

                JOptionPane.showMessageDialog(

                        null,

                        "Invalid Input"
                );
            }
        });

        table.addMouseListener(

                new MouseAdapter() {

                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        selectedRow =
                                table.getSelectedRow();

                        if (selectedRow >= 0) {

                            subjectField.setText(

                                    tableModel
                                            .getValueAt(
                                                    selectedRow,
                                                    1
                                            )
                                            .toString()
                            );

                            difficultyBox.setSelectedItem(

                                    tableModel
                                            .getValueAt(
                                                    selectedRow,
                                                    3
                                            )
                                            .toString()
                                            .equals("High")
                                            ? "Hard"

                                            :

                                            tableModel
                                                    .getValueAt(
                                                            selectedRow,
                                                            3
                                                    )
                                                    .toString()
                                                    .equals("Medium")
                                            ? "Medium"

                                            : "Easy"
                            );
                        }
                    }
                }
        );

        editButton.addActionListener(e -> {

            try {

                int row =
                        table.getSelectedRow();

                if (row >= 0) {

                    String oldDate =
                            tableModel
                                    .getValueAt(row, 0)
                                    .toString();

                    String newSubject =
                            subjectField
                                    .getText()
                                    .trim();

                    String newDifficulty =
                            difficultyBox
                                    .getSelectedItem()
                                    .toString();

                    int dailyHours =
                            Integer.parseInt(
                                    dailyHoursLabel.getText()
                            );

                    Integration_Module.editSubject(

                            row,

                            newSubject,

                            oldDate,

                            newDifficulty,

                            dailyHours,

                            tableModel,

                            totalSubjectsLabel,

                            dailyHoursLabel,

                            upcomingExamsLabel
                    );

                    JOptionPane.showMessageDialog(

                            null,

                            "Subject Updated Successfully"
                    );
                }
            }

            catch (Exception ex) {

                JOptionPane.showMessageDialog(

                        null,

                        "Edit Failed"
                );
            }
        });

        deleteButton.addActionListener(e -> {

            int row =
                    table.getSelectedRow();

            if (row >= 0) {

                int dailyHours =
                        Integer.parseInt(
                                dailyHoursLabel.getText()
                        );

                Integration_Module.deleteSubject(

                        row,

                        dailyHours,

                        tableModel,

                        totalSubjectsLabel,

                        dailyHoursLabel,

                        upcomingExamsLabel
                );

                JOptionPane.showMessageDialog(

                        null,

                        "Subject Deleted"
                );
            }
        });

        clearButton.addActionListener(e -> {

            Integration_Module.clearAll(

                    tableModel,

                    totalSubjectsLabel,
                    dailyHoursLabel,
                    upcomingExamsLabel
            );
        });

        chatButton.addActionListener(e -> {

            String input =
                    JOptionPane.showInputDialog(

                            null,

                            "Ask Smart Assistant"
                    );

            if (input != null) {

                String reply =
                        Smart_Assistant.chatbotReply(

                                input,

                                Integer.parseInt(
                                        totalSubjectsLabel.getText()
                                ),

                                dailyHoursLabel.getText()
                        );

                JOptionPane.showMessageDialog(

                        null,

                        reply,

                        "AI Assistant",

                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        setVisible(true);
    }

    private JPanel createCard(

            int x,
            int y,
            int width,
            int height,
            Color color

    ) {

        JPanel card = new JPanel();

        card.setBounds(x, y, width, height);

        card.setBackground(color);

        card.setLayout(null);

        return card;
    }

    private JLabel createCardTitle(
            String text
    ) {

        JLabel label = new JLabel(text);

        label.setBounds(18, 10, 220, 22);

        label.setForeground(Color.LIGHT_GRAY);

        label.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        return label;
    }

    private JLabel createCardValue(
            String text
    ) {

        JLabel label = new JLabel(text);

        label.setBounds(18, 35, 120, 30);

        label.setForeground(Color.WHITE);

        label.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        return label;
    }

    private JLabel createLabel(

            String text,
            int x,
            int y

    ) {

        JLabel label = new JLabel(text);

        label.setBounds(x, y, 250, 25);

        label.setForeground(Color.WHITE);

        label.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        return label;
    }

    private JTextField createTextField(

            int x,
            int y

    ) {

        JTextField field = new JTextField();

        field.setBounds(x, y, 280, 38);

        field.setBackground(
                new Color(45, 45, 45)
        );

        field.setForeground(Color.WHITE);

        field.setCaretColor(Color.WHITE);

        field.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        field.setBorder(

                BorderFactory.createEmptyBorder(

                        5,
                        10,
                        5,
                        10
                )
        );

        return field;
    }

    private JButton createButton(

            String text,
            Color color

    ) {

        JButton button = new JButton(text);

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }
}