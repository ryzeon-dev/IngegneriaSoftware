package db;
import java.sql.*;
import java.util.Vector;

public class PgDB {
    private Connection connection;
    public PgDB() {
        try {
            String url = DBConfig.getUrl();
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
                    Vector<String> row = getRow(dbOutput);
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

    private Vector<String> getRow(ResultSet dbOutput) {
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

    public PreparedStatement makePreparedStatement(String query) {
        try {
            return this.connection.prepareStatement(query);

        } catch (SQLException e) {
            System.out.println("fatal error: cannot create prepared statment for query \"" + query + "\" because of: " + e.toString());
            return null;
        }
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

    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {

        }
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
