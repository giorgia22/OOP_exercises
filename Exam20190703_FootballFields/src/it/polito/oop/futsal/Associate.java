package it.polito.oop.futsal;

public class Associate {
    private String name, surname, cell;
    private int reserv;

    public Associate(String name, String surname, String cell) {
        this.name = name;
        this.surname = surname;
        this.cell = cell;
        reserv = 0;
    }

    public int getReserv() {
        return reserv;
    }

    public void setReserv(int reserv) {
        this.reserv = reserv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
}
