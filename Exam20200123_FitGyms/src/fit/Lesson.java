package fit;

import java.util.HashSet;
import java.util.Set;

public class Lesson {
    private int maxParticipants;
    private String instructor, specialty;
    private boolean provide;
    private Set<Integer> participants;
    private Set<String> allowedInstructors;

    public Lesson() {
        provide = false;
        participants = new HashSet<>();
        allowedInstructors = new HashSet<>();
        instructor = "";
    }

    public Set<String> getAllowedInstructors() {
        return allowedInstructors;
    }

    public void setAllowedInstructors(Set<String> allowedInstructors) {
        this.allowedInstructors = allowedInstructors;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public boolean isProvide() {
        return provide;
    }

    public void setProvide(boolean provide) {
        this.provide = provide;
    }

    public Set<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Integer> participants) {
        this.participants = participants;
    }
}
