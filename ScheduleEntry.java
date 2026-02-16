import java.time.LocalDate;

public class ScheduleEntry {
    private LocalDate date;
    private String subjectName;
    private double hours;

    public ScheduleEntry(LocalDate date, String subjectName, double hours) {
        this.date = date;
        this.subjectName = subjectName;
        this.hours = hours;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public double getHours() {
        return hours;
    }
}
