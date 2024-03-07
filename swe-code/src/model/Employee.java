package model;

public class Employee {
    public final int id;
    public final String name;

    public final String lastName;
    public final EmployeeRole role;

    public final String abilitation;
    public String position = "";

    public Employee(int id, String name, String lastName, EmployeeRole role, String abilitation) {
        this.id = id;

        this.name = name;
        this.lastName = lastName;

        this.role = role;
        this.abilitation = abilitation;
    }

    public void setPosition(String icao) {
        this.position = icao;
    }

    @Override
    public String toString() {
        return this.role + " {" + this.name + " " + this.lastName + "}";
    }

    public String getFullData() {
        return "Name: " + this.name + "\n" +
                "Last name: " + this.lastName + "\n" +
                "ID: " + this.id + "\n" +
                "Role: " + this.role + "\n" +
                "Abilitation: " + this.abilitation + "\n";
    }
}
