package dao;

import db.PgDB;
import db.PreparedStatementQueries;
import model.Credentials;


import java.sql.SQLException;
import java.util.Vector;

public class CredentialsDaoPg implements dao.interfaces.CredentialsDaoI {
    @Override
    public Vector<Credentials> getAll() {
        PgDB db = new PgDB();

        var result = db.runAndFetch(PreparedStatementQueries.getCredentials);
        Vector<Credentials> credentials = new Vector<>();

        for (var row : result) {
            credentials.add(this.buildFromRow(row));
        }

        db.close();
        return credentials;
    }

    @Override
    public Credentials getCredentialsForUname(String uname) {
        PgDB db = new PgDB();
        try {
            var statement = db.makePreparedStatement(PreparedStatementQueries.getCredentialsForUname);
            statement.setString(1, uname);

            System.out.println(statement.toString());

            var result = statement.executeQuery();
            if (!result.next()) {
                db.close();
                return null;
            }

            String username = result.getString("username");
            String passwd = result.getString("passwd");
            int employeeId = result.getInt("employee_id");
            db.close();

            return new Credentials(username, passwd, employeeId);

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            db.close();
            return null;
        }
    }

    private Credentials buildFromRow(Vector<String> dbRow) {
        String username = dbRow.get(0);
        String passwd = dbRow.get(1);
        int id = Integer.parseInt(dbRow.get(2));

        return new Credentials(username, passwd, id);
    }
}
