package model;

public class  FlightRoute {
    public final int id;
    public final int distance;
    public final int duration;
    public final String departure;
    public final String stepover;
    public final String arrival;

    public FlightRoute(int id, int distance, int duration, String departure, String stepover, String arrival) {
        this.id = id;
        this.distance = distance;

        this.duration = duration;
        this.departure = departure;

        this.stepover = stepover;
        this.arrival = arrival;
    }

	public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) 
            return false;
        FlightRoute other = (FlightRoute) obj;
        return (this.id == other.id);
    }

    public int hashCode() {
        return id*(departure.hashCode()+stepover.hashCode()+arrival.hashCode());
    }

    public String toString() {
        return "Route {" + this.departure + "->" + this.arrival + "}";
    }
}
