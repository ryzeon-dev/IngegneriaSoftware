package cli;

public class LoginResult {
    public final PermissionLevel permissionLevel;
    public final int id;

    LoginResult(PermissionLevel pl, int id) {
        this.permissionLevel = pl;
        this.id = id;
    }
}