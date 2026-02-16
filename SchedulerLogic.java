import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SchedulerLogic {

    public static List<ScheduleEntry> generateSchedule(List<Subject> subjects, double dailyHours) {

        List<ScheduleEntry> schedule = new ArrayList<>();
        LocalDate today = LocalDate.now();

        if (subjects.isEmpty())
            return schedule;

        // Step 1: Calculate Priority
        double totalPriority = 0;

        for (Subject s : subjects) {
            long daysLeft = ChronoUnit.DAYS.between(today, s.getExamDate());
            if (daysLeft <= 0) continue;

            double priority = (double) s.getDifficulty() / daysLeft;
            totalPriority += priority;

            // Store temporary priority in object
            s.calculatePriority();
        }

        // Step 2: Sort subjects by priority descending
        subjects.sort((a, b) -> Double.compare(b.getPriorityScore(), a.getPriorityScore()));

        // Step 3: Allocate hours proportionally
        for (Subject s : subjects) {

            long daysLeft = ChronoUnit.DAYS.between(today, s.getExamDate());
            if (daysLeft <= 0) continue;

            double subjectShare =
                    (s.getPriorityScore() / totalPriority) * dailyHours;

            for (int i = 0; i < daysLeft; i++) {
                schedule.add(new ScheduleEntry(
                        today.plusDays(i),
                        s.getName(),
                        Math.round(subjectShare * 100.0) / 100.0
                ));
            }
        }

        return schedule;
    }
}
