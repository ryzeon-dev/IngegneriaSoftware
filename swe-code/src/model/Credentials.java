package model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Credentials {
    public final int id;
    public final String username;
    public final String passwd;

    public Credentials(String username, String passwd, int id) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
    }

    public String toString() {
        return "{ username: " + this.username + " ; passwd: " + this.passwd + " }";
    }

    public boolean checkHash(String passwd) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            var hashed = messageDigest.digest(passwd.getBytes(StandardCharsets.UTF_8));

            BigInteger no = new BigInteger(1, hashed);
            String hexHashed = no.toString(16);

            return this.passwd.equals(hexHashed);

        } catch (NoSuchAlgorithmException e) {

        }
        return false;
    }
}
