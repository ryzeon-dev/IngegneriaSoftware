package model;

public class Credentials {
    public final int id;
    public final String username;
    public final String passwd;

    public Credentials(String username, String passwd, int id) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "{ username: " + this.username + " ; passwd: " + this.passwd + " }";
    }
}
