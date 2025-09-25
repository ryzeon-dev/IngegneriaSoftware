package model;

public class Airport {
    public final String icao;
    public final DimensionClass dimensionClass;
    public final String name;
    public final String nation;
    public final String city;

    public Airport(String icao, DimensionClass dimensionClass, String name, String nation, String city) {
        this.icao = icao;

        this.dimensionClass = dimensionClass;
        this.name = name;

        this.nation = nation;
        this.city = city;
    }

    public int hashCode() {
        return icao.hashCode();
    }

    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) 
            return false;
        Airport other = (Airport) obj;
        return (this.icao.equals(other.icao));
    }

    public String toString() {
        return "Airport {icao: " + this.icao + " ; class: " + this.dimensionClass + " ; city: " + this.city + "}";
    }
}
