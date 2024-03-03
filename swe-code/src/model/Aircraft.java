package model;

public class Aircraft {
    private  String plate;
    private  String manufacturer ;
    private  String model;
    private  String specification;
    private  DimensionClass dimensionClass;
    private  int assistantsNumber;
    private  int range;
    private  int seats;
    private boolean busy = false;
    private String position = "";

    public Aircraft(){
        this.plate="";
        this.manufacturer="";
        this.model="";
        this.specification="";
        this.range=0;
        this.assistantsNumber=0;
        this.dimensionClass=null;
        this.seats=0;
    }

    //Getters and setters
    public String getPosition() {
        return position;
      }
  
    public void setPosition(String icao) {
        this.position = icao;
    }


    public int getAssistantsNumber() {
      return assistantsNumber;
    }

    public void setAssistantsNumber(int assistantsNumber) {
      this.assistantsNumber = assistantsNumber;
    }
    
    public DimensionClass getDimensionClass() {
      return dimensionClass;
    }

    public void setDimensionClass(DimensionClass dimensionClass) {
      this.dimensionClass = dimensionClass;
    }

    public String getManufacturer() {
      return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
      this.manufacturer = manufacturer;
    }

    public String getModel() {
      return model;
    }

    public void setModel(String model) {
      this.model = model;
    }

    public String getPlate() {
      return plate;
    }

    public void setPlate(String plate) {
      this.plate = plate;
    }

    public int getRange() {
      return range;
    }
    public void setRange(int range) {
      this.range = range;
    }

    public int getSeats() {
      return seats;
    }

    public void setSeats(int seats) {
      this.seats = seats;
    }

    public String getSpecification() {
      return specification;
    }

    public void setSpecification(String specification) {
      this.specification = specification;
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

}