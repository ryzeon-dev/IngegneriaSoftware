public class Main {
    public static void main(String[] args) {
        PgDB db = new PgDB();
        var res = db.runAndFetch("select * from aircraft_instance;");
        System.out.println(res);
    }
}