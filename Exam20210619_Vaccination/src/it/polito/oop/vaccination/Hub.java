package it.polito.oop.vaccination;

public class Hub {
    private int doctors, nurses, personnel;

    public Hub(int doctors, int nurses, int personnel) {
        this.doctors = doctors;
        this.nurses = nurses;
        this.personnel = personnel;
    }

    public int getDoctors() {
        return doctors;
    }

    public void setDoctors(int doctors) {
        this.doctors = doctors;
    }

    public int getNurses() {
        return nurses;
    }

    public void setNurses(int nurses) {
        this.nurses = nurses;
    }

    public int getPersonnel() {
        return personnel;
    }

    public void setPersonnel(int personnel) {
        this.personnel = personnel;
    }
}
