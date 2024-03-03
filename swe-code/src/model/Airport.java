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

    @Override
    public String toString() {
        return "model.Airport {" + this.icao + " " + this.dimensionClass + " " + this.city + "}";
    }
}
