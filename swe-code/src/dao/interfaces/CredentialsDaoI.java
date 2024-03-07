package dao.interfaces;

import model.Credentials;

import java.util.Vector;

public interface CredentialsDaoI {
    public Vector<Credentials> getAll();
}
