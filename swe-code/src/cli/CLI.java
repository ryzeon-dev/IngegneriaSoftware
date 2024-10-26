package cli;

import model.Credentials;
import model.Employee;
import model.Flight;
import system.CredentialsManager;
import system.ManagementSystem;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {//extends Thread {
    private ManagementSystem managementSystem;
    private boolean running = true;
    private CredentialsManager credentialsManager;

    public CLI(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        managementSystem.runScheduling();
        this.credentialsManager = new CredentialsManager();
    }

    public void run() {
        while (this.running) {
            try {
                Runtime.getRuntime().exec("/bin/bash -c clear");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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

    private void waitUntilEnter() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nPress <Enter> key when you're done");
        stdin.nextLine();
    }

    private void accessEmployeesData() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Access to this area requires login");

        System.out.print("Username: ");
        String username = stdin.nextLine();

        System.out.print("Password: ");
        String passwd = stdin.nextLine();

        if (username.equals("admin")) {
            Credentials adminCredentials = this.credentialsManager.getCredentialsFromUsername(username);
            adminCredentials.checkHash("root-access");

            if (!adminCredentials.username.equals(username) || !adminCredentials.checkHash(passwd)) {
                System.out.println("Login failed");
                accessEmployeesData();
                return;
            }

            while (true) {
                System.out.println("\nEmployees data menu");
                System.out.println("1 -> View all employees");
                System.out.println("2 -> View all commanders");

                System.out.println("3 -> View all first officers");
                System.out.println("4 -> View all flight assistants");

                System.out.println("5 -> View a specific employee");
                System.out.println("6 -> back");

                int choice;
                while (true) {
                    try {
                        System.out.print("View: ");
                        choice = stdin.nextInt();
                        break;

                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid input: retry");
                    }
                }

                System.out.println();
                switch (choice) {
                    case 1: {
                        for (var employee : managementSystem.flightManager.getAllEmployees()) {
                            System.out.println(employee);
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 2: {
                        for (var commander : managementSystem.flightManager.getCommanders()) {
                            System.out.println(commander + "\n");
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 3: {
                        for (var firstOfficer : managementSystem.flightManager.getFirstOfficiers()) {
                            System.out.println(firstOfficer + "\n");
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 4: {
                        for (var flightAssistant : managementSystem.flightManager.getFlightAssistants()) {
                            System.out.println(flightAssistant + "\n");
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 5: {
                        int requestedId = -1;

                        while (true) {
                            try {
                                System.out.print("Employee id: ");
                                requestedId = stdin.nextInt();
                                break;

                            } catch (InputMismatchException ex) {
                                System.out.println("Invalid input: retry");
                            }
                        }

                        if (requestedId >= 0 && requestedId < managementSystem.flightManager.employeesNumber()) {
                            System.out.println(managementSystem.flightManager.getEmployeeById(requestedId).getFullData());
                            waitUntilEnter();

                        } else {
                            System.out.println("ID index out of range");
                        }
                        break;
                    }

                    case 6: {
                        return;
                    }

                    default: {
                        System.out.println("Invalid choice\n");
                    }
                }
            }
        } else {
            System.out.println();
            Credentials credentials = credentialsManager.getCredentialsFromUsername(username);

            if (credentials == null) {
                System.out.println("Username does not exist");
                return;
            }

            if (!credentials.checkHash(passwd)) {
                System.out.println("Wrong password");
                accessEmployeesData();
                return;
            }
            int id = credentials.id;
            System.out.println(managementSystem.flightManager.getEmployeeById(id).getFullData());

            waitUntilEnter();
        }
    }

    private void accessAircraftDetails() {
        Scanner stdin = new Scanner(System.in);

        System.out.println("Access to this area requires admin privileges. Please login");

        System.out.print("Username: ");
        String username = stdin.nextLine();

        System.out.print("Password: ");
        String passwd = stdin.nextLine();

        Credentials adminCredentials = credentialsManager.getCredentialsFromUsername("admin");
        if (!username.equals(adminCredentials.username) || !adminCredentials.checkHash(passwd)) {
            System.out.println("Denied access: non-valid credentials");
            return;
        }
        System.out.println();

        for (var airctaft : this.managementSystem.getAircraftDetails()) {
            System.out.println(airctaft.fullData());
            System.out.println();
        }
        waitUntilEnter();
    }

    private void accessRoutesDetails() {
        System.out.println(this.managementSystem.getRouteDetails());
        System.out.println();
        waitUntilEnter();
    }

    private void accessFlightSchedule() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Access to this area requires login");

        System.out.print("Username: ");
        String username = stdin.nextLine();

        System.out.print("Password: ");
        String passwd = stdin.nextLine();

        Credentials credentials = this.credentialsManager.getCredentialsFromUsername(username);
        if (credentials == null) {
            System.out.println("Username not found");
            return;
        }

        if (!credentials.checkHash(passwd)) {
            System.out.println("Incorrect password, retry");
            accessFlightSchedule();
            return;
        }

        if (username.equals("admin")) {
            System.out.println("root access");
            for (var flight : this.managementSystem.scheduledFlights) {
                //if (flight.departureTime >= this.managementSystem.getTime())
                System.out.println(flight.toString());
            }
        } else {

        }

        waitUntilEnter();
    }

    private void accessPersonalArea() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Access to this area requires login");

        System.out.print("Username: ");
        String username = stdin.nextLine();

        System.out.print("Password: ");
        String passwd = stdin.nextLine();

        Credentials credentials = this.credentialsManager.getCredentialsFromUsername(username);
        if (credentials == null) {
            System.out.println("Username not found");
            return;
        }

        if (!credentials.checkHash(passwd)) {
            System.out.println("Incorrect password, retry");
            accessPersonalArea();
            return;
        }

        Employee employee = this.managementSystem.flightManager.getEmployeeById(credentials.id);

        while (true) {
            System.out.println("\nPersonal area menu:");
            System.out.println("1 -> Personal data view");
            System.out.println("2 -> Personal scheduling view");
            System.out.println("3 -> Quit");
            System.out.print("Navigate to: ");
            String choice = stdin.nextLine().trim();
            System.out.println();

            if (choice.equals("1")) {
                System.out.println(employee.getFullData());
                this.waitUntilEnter();

            } else if (choice.equals("2")) {
                for (Flight flight : this.managementSystem.scheduledFlights) {
                    if (flight.commanders.contains(employee) || flight.firstOfficers.contains(employee) || flight.flightAssistants.contains(employee)) {
                        System.out.println(flight.toString());
                        System.out.println();
                    }
                }
                this.waitUntilEnter();

            } else {
                return;
            }
        }
    }

    private void quit() {
        this.managementSystem.quit();
        System.exit(0);
    }
}
