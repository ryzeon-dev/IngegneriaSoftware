package cli.tests;

import static org.junit.Assert.assertTrue;

import cli.LoginResult;
import cli.PermissionLevel;
import model.EmployeeRole;
import org.junit.Test;

import cli.CLI;

public class CLITest {
    @Test
    public void AdminLoginTest_Success() {
        String uname = "admin";
        String passwd = "root-access";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == PermissionLevel.ADMIN);
        assertTrue(res.id == 0);
    }

    @Test
    public void AdminLoginTest_Fail() {
        String uname = "admin";
        String passwd = "rootaccess";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == null);
        assertTrue(res.id == -1);
    }

    @Test
    public void EmployeeLoginTest_Success() {
        String uname = "lomgra";
        String passwd = "com-lom-gra";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == PermissionLevel.EMPLOYEE);
        assertTrue(res.id == 1);
    }

    @Test
    public void EmployeeLoginTest_Fail() {
        String uname = "lomgra";
        String passwd = "comlomgra";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == null);
        assertTrue(res.id == -1);
    }

    @Test
    public void GuestLoginTest_Success() {
        String uname = "guest";
        String passwd = "guest";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == PermissionLevel.GUEST);
        assertTrue(res.id == -1);
    }

    @Test
    public void GuestLoginTest_Fail() {
        String uname = "guest";
        String passwd = "giuest";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == null);
        assertTrue(res.id == -1);
    }

    @Test
    public void NonExistingUsernameTest() {
        String uname = "does not exist";
        String passwd = "does not matter";

        CLI cli = new CLI();

        LoginResult res = cli.credentialsCheck(uname, passwd);
        assertTrue(res.permissionLevel == null);
        assertTrue(res.id == -1);
    }

    @Test
    public void ValidateEmployeeDataTest_Success() {
        CLI cli = new CLI();

        String name = "Mario";
        String lastname = "Rossi";

        EmployeeRole role = EmployeeRole.Commander;
        String abilitation = "A320";

        int res = cli.validateEmployeeData(name, lastname, role, abilitation);
        assertTrue(res == 0);
    }

    @Test
    public void ValidateEmployeeDataTest_Fail1() {
        CLI cli = new CLI();

        String name = "";
        String lastname = "Rossi";

        EmployeeRole role = EmployeeRole.FlightAssistant;
        String abilitation = "";

        int res = cli.validateEmployeeData(name, lastname, role, abilitation);
        assertTrue(res == 1);
    }

    @Test
    public void ValidateEmployeeDataTest_Fail2() {
        CLI cli = new CLI();

        String name = "Mario";
        String lastname = "";

        EmployeeRole role = EmployeeRole.FirstOfficer;
        String abilitation = "A320";

        int res = cli.validateEmployeeData(name, lastname, role, abilitation);
        assertTrue(res == 2);
    }

    @Test
    public void ValidateEmployeeDataTest_Fail3() {
        CLI cli = new CLI();

        String name = "Mario";
        String lastname = "Rossi";

        EmployeeRole role = EmployeeRole.Commander;
        String abilitation = "";

        int res = cli.validateEmployeeData(name, lastname, role, abilitation);
        assertTrue(res == 3);
    }
}