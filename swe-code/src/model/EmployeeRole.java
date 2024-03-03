package model;

public enum EmployeeRole {
    Commander, FirstOfficer, FlightAssistant;

    @Override
    public String toString() {
        if (this.equals(EmployeeRole.Commander)) {
            return "Commander";

        } else if (this.equals(EmployeeRole.FirstOfficer)) {
            return "First Officer";

        } else if (this.equals(EmployeeRole.FlightAssistant)) {
            return "Flight Assistant";

        } else {
            return "";
        }
    }
}
