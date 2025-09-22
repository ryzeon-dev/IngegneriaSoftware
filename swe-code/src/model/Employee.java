package model;
public class Employee {
    public int id;
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

    public Employee(String name, String lastName, EmployeeRole role, String abilitation) {
        this.name = name;
        this.lastName = lastName;

        this.role = role;
        this.abilitation = abilitation;
    }


    public void setPosition(String icao) {
        this.position = icao;
    }

    public String toString() {
        return this.role + " {" + this.name + " " + this.lastName + "}";
    }

    public String getFullData() {

        return "{ID: " + this.id + ", " +
                "Name: " + this.name + ", " +
                "Last name: " + this.lastName + ", " +
                "Role: " + this.role  +
                (
                    abilitation == null ? "" : ", Abilitation: " + this.abilitation
                )+"}";
    }


}
