package timesheet;

import java.util.*;

public class Project {
    private String name;
    private int maxHours, allHours;
    private Map<String, Activity> activities;

    public Project(String name, int maxHours) {
        this.name = name;
        this.maxHours = maxHours;
        activities = new TreeMap<>();
        allHours = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }

    public int getAllHours() {
        return allHours;
    }

    public void setAllHours(int allHours) {
        this.allHours += allHours;
    }

    public void setActivities(Map<String, Activity> activities) {
        this.activities = activities;
    }

    public Map<String, Activity> getActivities() {
        return activities;
    }
}
