package system.scheduling;

import model.Airport;

public class AirportWeighted {
    public final Airport airport;
    public final int weight;
    public AirportWeighted(Airport airport,int weight){
        this.airport = airport;
        this.weight = weight;
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
