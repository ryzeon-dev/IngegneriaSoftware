package db;
import java.sql.*;
import java.util.Vector;

public class PgDB {
    private Connection connection;
    public PgDB() {
        try {
            String url = DBConfig.getUrl(); // sostituire con `DBConfig.getUrl()`
            String username = DBConfig.getUsername();
            String passwd = DBConfig.getPasswd();

            this.connection = DriverManager.getConnection(url, username, passwd);
        } catch (SQLException e) {
            System.err.println("Fatal error: pgsql db error: " + e.getMessage());
            System.exit(1);
        }
    }

    public Vector<Vector<String>> runAndFetch(String query) {
        Vector<Vector<String>> result = new Vector<>();

        try {
            Statement statement = this.connection.createStatement();

            try {
                var dbOutput = statement.executeQuery(query);

                while (dbOutput.next()) {
                    Vector<String> row = GetRow(dbOutput);
                    result.add(row);
                }

                return result;

            } catch (SQLException ex) {
                System.err.println("fatal error: query \"" + query + "\" generated error: " +  ex.getMessage());
                System.exit(1);
            }


        } catch (SQLException ex) {
            System.err.println("fatal error: statement creation generated error: " + ex.getMessage());
            System.exit(1);

        }
        return result;
    }
    // Il codice per il fetch del contenuto delle righe itera fino a quando non viene generato un errore
    // il che indica la fine della riga (limite dell'indice di colonna raggiunto)
    // non ho trovato un metodo che mi dica il numero di colonne da poter usare, quindi bisogna usare
    // questo escamotage orrendo
    private Vector<String> GetRow(ResultSet dbOutput) {
        Vector<String> row = new Vector<>();
        try {
            var metadata=dbOutput.getMetaData();
            int colCount=metadata.getColumnCount();
            for (int i = 1; i <= colCount; i++) {
                row.add(dbOutput.getString(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public Vector<Vector<String>> runPstmtAndFetch(String query,String param ){
        Vector<Vector<String>> result = new Vector<>();
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)){
            pstmt.setString(1,param);
            try (ResultSet dbOutput = pstmt.executeQuery()){
                while (dbOutput.next()) {
                    Vector<String> row = GetRow(dbOutput);
                    result.add(row);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("fatal error: prepared statement creation generated error: " + e.getMessage());
            System.exit(1);
        }
        return result;
    }

    public boolean run(String query) {
        try {
            Statement statement = this.connection.createStatement();

            try {
                return statement.execute(query);


            } catch (SQLException ex) {
                System.err.println("fatal error: query \"" + query + "\" generated error: " +  ex.getMessage());
                System.exit(1);
            }


        } catch (SQLException ex) {
            System.err.println("fatal error: statement creation generated error: " + ex.getMessage());
            System.exit(1);

        }
        return false;
    }

    public void close() {
        try {
            this.connection.close();

        } catch (SQLException ex) {
            System.err.println("fatal error: closing connection generated: " + ex.getMessage());
            System.exit(1);
        }
    }
}
