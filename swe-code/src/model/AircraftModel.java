package model;

public class AircraftModel {
    public final int modelId;
    public final String  manufacturer;
    public final String model;
    public final String specification;
    public final int range;
    public final int assistantsNumber;
    public final DimensionClass dimensionClass;
    public final int seats;

    public AircraftModel(int modelId, String manufacturer, String model, String specification, int range, int assistantsNumber, DimensionClass dimensionClass, int seats){
        this.modelId=modelId;
        this.manufacturer=manufacturer;

        this.model=model;
        this.specification=specification;

        this.range=range;
        this.assistantsNumber=assistantsNumber;

        this.dimensionClass=dimensionClass;
        this.seats=seats;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();

        builder.append("manufaturer:");
        builder.append(manufacturer+", ");

        builder.append("model:");
        builder.append(" "+ model+", ");

        builder.append("specification:");
        builder.append(" "+ specification+", ");

        builder.append("range:");
        builder.append(" "+ range+", ");

        builder.append("assistants_number:");
        builder.append(" "+ assistantsNumber+", ");

        builder.append("dimension_class:");
        builder.append(" "+ dimensionClass.toString()+", ");

        builder.append("seats:");
        builder.append(" "+ seats);
        return "{"+builder.toString()+"}";
    }
}
