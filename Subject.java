import java.time.LocalDate;

public class Subject {
    private String name;
    private LocalDate examDate;
    private int difficulty;
    private double priorityScore;

    public Subject(String name, LocalDate examDate, int difficulty) {
        this.name = name;
        this.examDate = examDate;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public double getPriorityScore() {
        return priorityScore;
    }

    public void calculatePriority() {
        long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), examDate);
        if (daysLeft <= 0) daysLeft = 1;
        this.priorityScore = (double) difficulty / daysLeft;
    }
}
