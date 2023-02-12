package fit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Gym {
    private String name;
    private List<String> instructors;
    private List<List<Lesson>> week;

    public Gym(String name) {
        this.name = name;
        instructors = new LinkedList<>();
        week = new ArrayList<>(0);
        for(int i = 0; i < 7; i++){
            week.add(new ArrayList<>(0));
            for(int j = 0; j < 13; j++){
                week.get(i).add(new Lesson());
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<String> instructors) {
        this.instructors = instructors;
    }

    public List<List<Lesson>> getWeek() {
        return week;
    }

    public void setWeek(List<List<Lesson>> week) {
        this.week = week;
    }
}
