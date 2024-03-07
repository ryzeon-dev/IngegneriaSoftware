package model;

public class Credentials {
    public final String username;
    public final String passwd;

    public Credentials(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "{ username: " + this.username + " ; passwd: " + this.passwd + " }";
    }
}
