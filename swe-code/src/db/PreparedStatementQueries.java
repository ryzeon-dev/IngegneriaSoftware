package db;

public class PreparedStatementQueries {
    public static final String GetParkedAircrafts=
    "select ai.plate, a.manufacturer, a.model, a.specification, a.range, a.assistants_number, a.class, a.seats "+
    "from parking p "+
    "join aircraft_instance ai on ai.plate = p.aircraft "+
    "join aircraft a  on ai.aircraft = a.id "+
    "where p.airport = ?";
}
