package timesheet;

public class Activity {
    private String name;
    private int hours;
    private boolean completed;

    public Activity(String name) {
        this.name = name;
        this.completed = false;
        hours = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours += hours;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
