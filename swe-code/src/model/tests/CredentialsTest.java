package model.tests;

import dao.interfaces.CredentialsDaoI;
import dao.CredentialsDaoPg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.Credentials;
import org.junit.Test;

public class CredentialsTest {
    @Test
    public void AuthenticationTest_Success() {
         CredentialsDaoI credentialsDao = new CredentialsDaoPg();
         Credentials adminCredentials = credentialsDao.getCredentialsForUname("admin");
         Credentials lomgraCredentials = credentialsDao.getCredentialsForUname("lomgra");

         assertTrue(adminCredentials.checkHash("root-access"));
         assertTrue(lomgraCredentials.checkHash("com-lom-gra"));
    }

    @Test
    public void AuthenticationTest_Fail() {
        CredentialsDaoI credentialsDao = new CredentialsDaoPg();
        Credentials adminCredentials = credentialsDao.getCredentialsForUname("admin");
        Credentials lomgraCredentials = credentialsDao.getCredentialsForUname("lomgra");

        assertFalse(adminCredentials.checkHash("wrong-passwd"));
        assertFalse(lomgraCredentials.checkHash("wrong-passwd"));
    }
}
