package cli;

import dao.CredentialsDaoPg;
import model.Credentials;
import system.CredentialsManager;
import system.ManagementSystem;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import java.io.Console;

public class CLI {//extends Thread {
    private ManagementSystem managementSystem;
    private boolean running = true;
    private CredentialsManager credentialsManager;

    public CLI(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        this.credentialsManager = new CredentialsManager();
    }

    public void run() {
        while (this.running) {
            System.out.println("##### ITA Airways management system #####");
            System.out.println("Current time: " + this.managementSystem.getTime());
            System.out.println("Main menu:");
            System.out.println("1 -> Access employees data"); // admin -> tutto, impiegato -> solo il suo
            System.out.println("2 -> Access aircrafts details"); // solo admin
            System.out.println("3 -> Access routes details"); // tutti a tutto
            System.out.println("4 -> Access flight schedule"); // admin -> tutto, pilota -> solo i suoi
            System.out.println("5 -> Access personal area"); // admin -> tutto, impigati -> solo il suo
            System.out.println("6 -> Quit");

            System.out.print("\nNavigate to: ");

            Scanner stdin = new Scanner(System.in);
            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.accessEmployeesData();
                        break;

                    case 2:
                        this.accessAircraftDetails();
                        break;

                    case 3:
                        this.accessRoutesDetails();
                        break;

                    case 4:
                        this.accessFlightSchedule();
                        break;

                    case 5:
                        this.accessPersonalArea();
                        break;

                    case 6:
                        this.quit();
                        break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 6 corresponding to the navigation choice");
                continue;
            }
        }
    }

    private void accessEmployeesData() {

    }

    private void accessAircraftDetails() {
        Scanner stdin = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Access to this area requires admin privileges. Please login");

        System.out.print("Username: ");
        String username = stdin.nextLine();

        System.out.print("Password: ");
        String passwd = stdin.nextLine();

        Credentials adminCredentials = credentialsManager.getCredentialsFromUsername("admin");
        if (!username.equals(adminCredentials.username) || !passwd.equals(adminCredentials.passwd)) {
            System.out.println("Denied access: non-valid credentials");
            return;
        }
        System.out.println();

        for (var airctaft : this.managementSystem.manager.aircrafts) {
            System.out.println(airctaft.fullData());
            System.out.println();
        }
    }

    private void accessRoutesDetails() {

    }

    private void accessFlightSchedule() {

    }

    private void accessPersonalArea() {

    }

    private void quit() {
        this.managementSystem.quit();
        System.exit(0);
    }
}
