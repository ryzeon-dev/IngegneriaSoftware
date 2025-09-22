package dao;

import db.ConstantQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.Credentials;


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

    private Credentials buildFromRow(Vector<String> dbRow) {
        String username = dbRow.get(0);
        String passwd = dbRow.get(1);
        int id = Integer.parseInt(dbRow.get(2));

        return new Credentials(username, passwd, id);
    }
}
