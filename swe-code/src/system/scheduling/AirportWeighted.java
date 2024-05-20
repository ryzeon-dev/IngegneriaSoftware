package system.scheduling;

import model.Airport;

public class AirportWeighted {
    public final Airport airport;
    public final int routeId;
    public final int weight;
    public final int duration;
    public AirportWeighted(Airport airport,int weight,int route_id,int route_duration){
        this.airport = airport;
        this.weight = weight;
        this.duration=route_duration;
        this.routeId=route_id;
    }

    @Override
    public int hashCode() {
        return airport.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return airport.equals(obj);
    }
}
