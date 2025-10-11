package model;

import java.util.Objects;

public class Aircraft {
    public final String  manufacturer;
    public final String model;
    public final String specification;
    public final int range;
    public final int assistantsNumber;
    public final DimensionClass dimensionClass;
    public final int seats;

    public String plate;
    private boolean busy = false;
    private String position = "";

    public Aircraft(String manufacturer, String model, String specification, int range,int assistantsNumber,DimensionClass dimensionClass,int seats){
        this.manufacturer=manufacturer;
        this.model=model;
        this.specification=specification;
        this.range=range;
        this.assistantsNumber=assistantsNumber;
        this.dimensionClass=dimensionClass;
        this.seats=seats;
    }

    public Aircraft(String plate, String manufacturer, String model, String specification, DimensionClass dimensionClass, int assistantsNumber, int range, int seats){
        this.plate = plate;
        this.manufacturer = manufacturer;

        this.model = model;
        this.specification = specification;

        this.dimensionClass = dimensionClass;
        this.assistantsNumber = assistantsNumber;

        this.range = range;
        this.seats = seats;
    }

//    public String getPosition() {
//        return position;
//      }
//
//    public void setPosition(String icao) {
//        this.position = icao;
//    }
//
//    public void setBusy(boolean busy) {
//        this.busy = busy;
//      }
//
//    public boolean getBusy() {
//        return this.busy;
//      }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Aircraft aircraft = (Aircraft) object;
        return range == aircraft.range && assistantsNumber == aircraft.assistantsNumber && seats == aircraft.seats && busy == aircraft.busy && Objects.equals(manufacturer, aircraft.manufacturer) && Objects.equals(model, aircraft.model) && Objects.equals(specification, aircraft.specification) && Objects.equals(dimensionClass, aircraft.dimensionClass) && Objects.equals(plate, aircraft.plate) && Objects.equals(position, aircraft.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, model, specification, range, assistantsNumber, dimensionClass, seats, plate, busy, position);
    }

    public boolean canGo(Airport airport) {
        return this.dimensionClass.isCompatible(airport.dimensionClass);
    }

    public String toString() {
        return "Aircraft {" + this.manufacturer + " " + this.model + " " + this.plate + "}";
    }

    public String fullData() {
        return "Plate: " + this.plate + "\n" +
                "Manufacturer: " + this.manufacturer + "\n" +
                "Model: " + this.model + "\n" +
                "Specification: " + this.specification + "\n" +
                "Dimension class: " + this.dimensionClass + "\n" +
                "Assistants number: " + this.assistantsNumber + "\n" +
                "Range: " + this.range + " Km\n" +
                "Seats: " + this.seats;
    }
}