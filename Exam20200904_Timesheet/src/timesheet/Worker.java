package timesheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker {
    private String name, surname;
    private int profileCode;
    private int[] hours;
    private Map<String, List<String>> projects;

    public Worker(String name, String surname, int profileCode) {
        this.name = name;
        this.surname = surname;
        this.profileCode = profileCode;
        projects = new HashMap<>();
        hours = new int[365];
    }

    public int[] getHours() {
        return hours;
    }

    public void setHours(int[] hours) {
        this.hours = hours;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(int profileCode) {
        this.profileCode = profileCode;
    }

    public Map<String, List<String>> getProjects() {
        return projects;
    }

    public void setProjects(Map<String, List<String>> projects) {
        this.projects = projects;
    }
}
