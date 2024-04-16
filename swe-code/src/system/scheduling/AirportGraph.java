package system.scheduling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Airport;

public class AirportGraph {
    private Map<Airport,LinkedList<AirportWeighted>> adjacencyList= new HashMap<>();
    
    public void addVertex(Airport airport){
        if(!adjacencyList.containsKey(airport)){
            adjacencyList.put(airport, new LinkedList<AirportWeighted>());
        }
    }

    /*
        Add edge to graph and also create vertex if they not exist. 
        a1 -> a2 Order matter since this is a directed graph.
    */

    public void addEdge(Airport a1,Airport a2,int weight,int routeId){
        if(!adjacencyList.containsKey(a1)){
            addVertex(a1);
        }
        if(!adjacencyList.containsKey(a2)){
            addVertex(a2);
        }
        adjacencyList.get(a1).add(new AirportWeighted(a2, weight,routeId));
    }

    public Set<Airport> getVertexList(){
        return adjacencyList.keySet();
    }

    public boolean containsVertex(Airport r){
        return adjacencyList.containsKey(r);
    }

    public LinkedList<AirportWeighted> getAdjacentVertex(Airport airport){
        return adjacencyList.get(airport);
    }

    @Override
    public String toString() {
        StringBuilder builder= new StringBuilder();
        for ( Entry<Airport, LinkedList<AirportWeighted>> vertex : adjacencyList.entrySet()) {
            builder.append(vertex.getKey().icao);
            for ( AirportWeighted airportWeighted : vertex.getValue()) {
                builder.append(" -"+airportWeighted.weight+"-> "+airportWeighted.airport.icao+"");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}

