import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Scheduler_Logic {

    public static boolean validateInput(
            String subject,
            String examDate,
            String hours
    ) {

        if (subject.isEmpty()
                || examDate.isEmpty()
                || hours.isEmpty()) {

            return false;
        }

        try {

            Integer.parseInt(hours);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate.parse(examDate, formatter);

        }

        catch (Exception e) {

            return false;
        }

        return true;
    }

    public static long getDaysLeft(String examDate) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate exam =
                LocalDate.parse(examDate, formatter);

        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(today, exam);
    }

    public static String getPriority(
            String difficulty,
            long daysLeft
    ) {

        if (difficulty.equals("Hard")
                && daysLeft <= 3) {

            return "High";
        }

        if (difficulty.equals("Medium")
                && daysLeft <= 7) {

            return "Medium";
        }

        return "Low";
    }

    public static int calculateStudyHours(
            String difficulty,
            int dailyHours
    ) {

        if (difficulty.equals("Hard")) {

            return Math.max(3, dailyHours / 2);
        }

        if (difficulty.equals("Medium")) {

            return Math.max(2, dailyHours / 3);
        }

        return 1;
    }

    public static Object[] generateSchedule(

            String subject,
            String examDate,
            String difficulty,
            int dailyHours

    ) {

        long daysLeft =
                getDaysLeft(examDate);

        String priority =
                getPriority(
                        difficulty,
                        daysLeft
                );

        int allocatedHours =
                calculateStudyHours(
                        difficulty,
                        dailyHours
                );

        return new Object[] {

                examDate,
                subject,
                allocatedHours,
                priority
        };
    }
}