# Smart Study Scheduler

A modern Java Swing based desktop application that helps students generate an intelligent study timetable according to exam dates, subject difficulty, and daily study hours.

---

## Features

- Smart priority-based scheduling
- Dynamic study hour allocation
- Modern dark mode dashboard UI
- Calendar based date picker
- AI chatbot assistant
- Popup exam reminders
- Edit/Delete subjects
- Completed task tracking
- Persistent data storage
- Automatic schedule rebuilding
- Scrollable modern timetable
- Priority sorting system

---

## Project Objective

The main objective of this project is:

> To help students manage exam preparation efficiently by generating a smart and balanced study schedule.

The system analyzes:

- Exam urgency
- Subject difficulty
- Daily study capacity

and then generates an optimized timetable.

---

## Technologies Used

- Java
- Java Swing
- File Handling
- OOP Concepts

---

## Project Structure

```text
SmartStudyScheduler/
│
├── Main.java
├── GUI_Module.java
├── Integration_Module.java
├── Scheduler_Logic.java
├── Smart_Assistant.java
├── icon.png
├── schedule_data.txt
```

---

## Core Working

### 1. User Inputs

The user enters:

- Subject Name
- Exam Date
- Difficulty Level
- Daily Study Hours

---

### 2. Priority Calculation

Priority is calculated using:

```text
Priority Score =
Difficulty Weight + Urgency Factor
```

### Difficulty Weights

| Difficulty | Weight |
|------------|--------|
| Easy       | 1      |
| Medium     | 2      |
| Hard       | 3      |

Closer exam dates receive higher priority.

---

### 3. Smart Schedule Generation

The scheduler:

- sorts subjects by priority
- distributes study hours intelligently
- ensures daily hour limit is not exceeded
- generates a balanced study plan

---

### 4. AI Assistant

The built-in chatbot supports:

- motivation quotes
- total subjects
- daily study hours
- basic assistance commands

---

### 5. Reminder System

The application automatically shows notifications for:

- upcoming exams
- high priority subjects
- urgent preparation alerts

---

### 6. Persistent Storage

All schedules are automatically saved using file handling.

When the application reopens:

- previous schedules load automatically
- completed tasks remain saved

---

## GUI Preview

### Dashboard Includes

- Total Subjects
- Daily Study Hours
- Upcoming Exams
- Timetable View
- AI Assistant
- Task Completion Tracking

---

## How to Run

### Compile

```bash
javac *.java
```

### Run

```bash
java Main
```

---

## Future Improvements

- PDF export
- Weekly planner
- Performance analytics
- Cloud sync
- Login system
- Mobile version

---

## Author

Anubhav Panwar

---

## License

This project is created for educational and academic purposes.