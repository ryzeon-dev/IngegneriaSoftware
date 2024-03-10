package system;

import dao.CredentialsDaoPg;
import model.Credentials;

import java.util.Vector;

public class CredentialsManager {
    private CredentialsDaoPg credentialsDao = new CredentialsDaoPg();
    private Vector<Credentials> credentials;

    public CredentialsManager() {
        credentials = credentialsDao.getAll();
    }

    public Credentials getCredentialsFromUsername(String username) {
        for (var pair : this.credentials) {
            if (pair.username.equals(username)) {
                return pair;
            }
        }

        return null;
    }
}
