package model;

public class Flight {
    private int id;
    private String departureTime;

    private int passengersNumber;
    private int route;

    private String aircraftPlate;
    private int commander;
    private int firstOfficial;
    
    public Flight(int id, String departureTime, int passengersNumber, int route,
                  String aircraftPlate, int commander, int firstOfficial){
        this.id = id;

        this.departureTime = departureTime;
        this.passengersNumber = passengersNumber;

        this.route = route;
        this.aircraftPlate = aircraftPlate;

        this.commander = commander;
        this.firstOfficial = firstOfficial;
    }
    
    //Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }
    public void setPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
    }

    public int getRoute() {
        return route;
    }
    public void setRoute(int route) {
        this.route = route;
    }

    public String getAircraftPlate() {
        return aircraftPlate;
    }
    public void setAircraftPlate(String aircraftPlate) {
        this.aircraftPlate = aircraftPlate;
    }

    public int getCommander() {
        return commander;
    }
    public void setCommander(int commander) {
        this.commander = commander;
    }

    public int getFirstOfficial() {
        return firstOfficial;
    }
    public void setFirstOfficial(int firstOfficial) {
        this.firstOfficial = firstOfficial;
    }
}
