package model;

public class Aircraft {
    public final String plate;
    public final String manufacturer;

    public final String model;
    public final String specification;

    public final DimensionClass dimensionClass;
    public final int assistantsNumber;

    public final int range;
    public final int seats;

    private boolean busy = false;
    private String position = "";

    public Aircraft(String plate, String manufacturer, String model, String specification,
                    DimensionClass dimensionClass, int assistantsNumber, int range, int seats){
        this.plate = plate;
        this.manufacturer = manufacturer;

        this.model = model;
        this.specification = specification;

        this.dimensionClass = dimensionClass;
        this.assistantsNumber = assistantsNumber;

        this.range = range;
        this.seats = seats;
    }

    public String getPosition() {
        return position;
      }
    public void setPosition(String icao) {
        this.position = icao;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
      }
    public boolean getBusy() {
        return this.busy;
      }

    @Override
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
                "Range: " + this.range + "Km\n" +
                "Seats: " + this.seats;
    }
}