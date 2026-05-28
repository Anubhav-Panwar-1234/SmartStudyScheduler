import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.io.*;

public class Integration_Module {

    static ArrayList<String[]> subjects =
            new ArrayList<>();

    static final String FILE_NAME =
            "schedule_data.txt";

    static int currentDailyHours = 4;

    public static void addSubject(

            String subject,
            String examDate,
            String difficulty,
            int dailyHours,

            DefaultTableModel tableModel,

            JLabel totalSubjectsLabel,
            JLabel dailyHoursLabel,
            JLabel upcomingExamsLabel

    ) {

        currentDailyHours = dailyHours;

        subjects.add(new String[] {

                subject,
                examDate,
                difficulty,
                "false"

        });

        saveData();

        rebuildSchedule(

                currentDailyHours,

                tableModel,

                totalSubjectsLabel,
                dailyHoursLabel,
                upcomingExamsLabel
        );

        String notification =
                Smart_Assistant.getNotification(

                        subject,
                        examDate,
                        difficulty
                );

        Smart_Assistant.showPopup(notification);
    }

    public static void editSubject(

            int row,

            String subject,
            String examDate,
            String difficulty,
            int dailyHours,

            DefaultTableModel tableModel,

            JLabel totalSubjectsLabel,
            JLabel dailyHoursLabel,
            JLabel upcomingExamsLabel

    ) {

        if (row < 0 || row >= subjects.size())
            return;

        currentDailyHours = dailyHours;

        subjects.set(

                row,

                new String[] {

                        subject,
                        examDate,
                        difficulty
                }
        );

        saveData();

        rebuildSchedule(

                currentDailyHours,

                tableModel,

                totalSubjectsLabel,
                dailyHoursLabel,
                upcomingExamsLabel
        );

        Smart_Assistant.showPopup(
                "Subject Updated Successfully"
        );
    }

    public static void deleteSubject(

            int row,

            int dailyHours,

            DefaultTableModel tableModel,

            JLabel totalSubjectsLabel,
            JLabel dailyHoursLabel,
            JLabel upcomingExamsLabel

    ) {

        if (row < 0 || row >= subjects.size())
            return;

        currentDailyHours = dailyHours;

        subjects.remove(row);

        saveData();

        rebuildSchedule(

                currentDailyHours,

                tableModel,

                totalSubjectsLabel,
                dailyHoursLabel,
                upcomingExamsLabel
        );

        Smart_Assistant.showPopup(
                "Subject Deleted"
        );
    }

    public static void rebuildSchedule(

            int dailyHours,

            DefaultTableModel tableModel,

            JLabel totalSubjectsLabel,
            JLabel dailyHoursLabel,
            JLabel upcomingExamsLabel

    ) {

        tableModel.setRowCount(0);

        ArrayList<Object[]> rows =
                new ArrayList<>();

        int upcomingExams = 0;

        int totalWeight = 0;

        for (String[] subjectData : subjects) {

            String subject =
                    subjectData[0];

            String examDate =
                    subjectData[1];

            String difficulty =
                    subjectData[2];

            Object[] row =
                    Scheduler_Logic.generateSchedule(

                            subject,
                            examDate,
                            difficulty,
                            dailyHours
                    );

            rows.add(row);

            String priority =
                    row[3].toString();

            totalWeight +=
                    getPriorityValue(priority);

            if (priority.equals("High")
                    || priority.equals("Medium")) {

                upcomingExams++;
            }
        }

        rows.sort((a, b) -> {

            int p1 =
                    getPriorityValue(
                            a[3].toString()
                    );

            int p2 =
                    getPriorityValue(
                            b[3].toString()
                    );

            return Integer.compare(p2, p1);
        });

        int allocatedTotal = 0;

        for (int i = 0; i < rows.size(); i++) {

            Object[] row = rows.get(i);

            String priority =
                    row[3].toString();

            int weight =
                    getPriorityValue(priority);

            int allocated;

            if (i == rows.size() - 1) {

                allocated =
                        dailyHours - allocatedTotal;
            }

            else {

                allocated =
                        Math.max(
                                1,
                                (weight * dailyHours)
                                        / totalWeight
                        );

                allocatedTotal += allocated;
            }

            if (allocated < 1)
                allocated = 1;

            row[2] = allocated;

            
            tableModel.addRow(

                    new Object[] {

                            row[0],
                            row[1],
                            row[2],
                            row[3],
                            Boolean.parseBoolean(subjects.get(i)[3])
                    }
            );

        }

        totalSubjectsLabel.setText(
                String.valueOf(subjects.size())
        );

        dailyHoursLabel.setText(
                String.valueOf(dailyHours)
        );

        upcomingExamsLabel.setText(
                String.valueOf(upcomingExams)
        );
    }

    public static void checkReminders() {

        for (String[] subject : subjects) {

            String message =
                    Smart_Assistant.getNotification(

                            subject[0],
                            subject[1],
                            subject[2]
                    );

            if (
                    message.contains("⚠")
                    || message.contains("🔥")
            ) {

                Smart_Assistant.showPopup(message);
            }
        }
    }

    public static void saveData() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(

                            new FileWriter(FILE_NAME)
                    );

            writer.write(
                    String.valueOf(currentDailyHours)
            );

            writer.newLine();

            for (String[] subject : subjects) {

                writer.write(

                        subject[0] + ","
                                + subject[1] + ","
                                + subject[2] + ","
                                + subject[3]
                );

                writer.newLine();
            }

            writer.close();
        }

        catch (Exception e) {

            System.out.println(
                    "Error saving data"
            );
        }
    }

    public static void loadData(

            int dailyHours,

            DefaultTableModel tableModel,

            JLabel totalSubjectsLabel,
            JLabel dailyHoursLabel,
            JLabel upcomingExamsLabel

    ) {

        try {

            File file =
                    new File(FILE_NAME);

            if (!file.exists())
                return;

            BufferedReader reader =
                    new BufferedReader(

                            new FileReader(file)
                    );

            String line;

            line = reader.readLine();

            if (line != null) {

                currentDailyHours =
                        Integer.parseInt(line);
            }

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split(",");

                subjects.add(data);
            }

            reader.close();

            rebuildSchedule(

                    currentDailyHours,

                    tableModel,

                    totalSubjectsLabel,
                    dailyHoursLabel,
                    upcomingExamsLabel
            );
        }

        catch (Exception e) {

            System.out.println(
                    "Error loading data"
            );
        }
    }

    public static void clearAll(

            DefaultTableModel tableModel,

            JLabel totalSubjectsLabel,
            JLabel dailyHoursLabel,
            JLabel upcomingExamsLabel

    ) {

        subjects.clear();

        tableModel.setRowCount(0);

        totalSubjectsLabel.setText("0");

        dailyHoursLabel.setText("0");

        upcomingExamsLabel.setText("0");

        File file =
                new File(FILE_NAME);

        if (file.exists())
            file.delete();
    }

    private static int getPriorityValue(
            String priority
    ) {

        if (priority.equals("High"))
            return 3;

        if (priority.equals("Medium"))
            return 2;

        return 1;
    }
}