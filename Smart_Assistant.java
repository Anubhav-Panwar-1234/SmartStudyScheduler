import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Smart_Assistant {

    public static String getNotification(

            String subject,
            String examDate,
            String difficulty

    ) {

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy"
                    );

            LocalDate exam =
                    LocalDate.parse(
                            examDate,
                            formatter
                    );

            LocalDate today =
                    LocalDate.now();

            long daysLeft =
                    ChronoUnit.DAYS.between(
                            today,
                            exam
                    );

            if (daysLeft == 0) {

                return "🚨 " + subject
                        + " exam is TODAY!";
            }

            if (daysLeft == 1) {

                return "⚠ " + subject
                        + " exam is TOMORROW!";
            }

            if (daysLeft <= 3) {

                return "🔥 Urgent: Focus on "
                        + subject;
            }

            if (daysLeft <= 7) {

                return "📘 Revision time for "
                        + subject;
            }

            if (difficulty.equals("Hard")) {

                return "💡 Start preparing early for "
                        + subject;
            }

            return "✅ Schedule Updated";

        }

        catch (Exception e) {

            return "Invalid exam date";
        }
    }

    public static String chatbotReply(

            String message,
            int totalSubjects,
            String dailyHours

    ) {

        message = message.toLowerCase();

        if (message.contains("hello"))
            return "Hello! Ready to study?";

        if (message.contains("total"))
            return "Total subjects: " + totalSubjects;

        if (message.contains("hours"))
            return "Daily study hours: " + dailyHours;

        if (message.contains("motivation"))
            return getMotivation();

        if (message.contains("help"))
            return """
                    Commands:
                    - total subjects
                    - daily hours
                    - motivation
                    """;

        return "I did not understand.";
    }

    public static String getMotivation() {

        String[] quotes = {

                "Success comes from consistency.",
                "Study now, shine later.",
                "Small progress is still progress.",
                "Discipline beats motivation.",
                "Your future self will thank you."

        };

        Random random = new Random();

        return quotes[
                random.nextInt(quotes.length)
        ];
    }

    public static void showPopup(String message) {

        JOptionPane.showMessageDialog(

                null,
                message,

                "Smart Assistant",

                JOptionPane.INFORMATION_MESSAGE
        );
    }
}