package model;

public enum EmployeeRole {
    Commander, FirstOfficer, FlightAssistant;
    public EmployeeRole fromString(String s){
        switch (s) {
            case "commander":
                return Commander;
            case "firstofficer":
                return FirstOfficer;
            case "hostess/steward":
                return FlightAssistant;
            default:
                throw new RuntimeException();
        }
    }
    public String toString() {
        if (this.equals(EmployeeRole.Commander)) {
            return "commander";

        } else if (this.equals(EmployeeRole.FirstOfficer)) {
            return "firstofficer";

        } else if (this.equals(EmployeeRole.FlightAssistant)) {
            return "hostess/steward";

        } else {
            return "";
        }
    }
}
