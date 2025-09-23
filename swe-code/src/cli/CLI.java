package cli;

import dao.*;
import dao.interfaces.*;
import model.Aircraft;
import model.AircraftModel;
import model.Credentials;
import model.DimensionClass;
import model.EmployeeRole;
import system.ManagementSystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

enum PermissionLevel {
    ADMIN, EMPLOYEE, GUEST
};

public class CLI {
    EmployeeDaoI employeeDao = new EmployeeDaoPg();
    AircraftDaoI aircraftDao = new AircraftDaoPg();
    FlightRouteDaoI flightRouteDao = new FlightRouteDaoPg();
    AirportDaoI airportDaoPg = new AirportDaoPg();
    CredentialsDaoI credentialsDao = new CredentialsDaoPg();

    private ManagementSystem managementSystem;
    private boolean running = true;

    private PermissionLevel permissionLevel;
    private int id;

    public CLI(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        System.out.println("---Beginning scheduling");
        managementSystem.runScheduling();
        System.out.println("---End scheduling");
    }
    /* STD CLI */

    private void banner() {
        System.out.println("##### ITA Airways management system #####");
        System.out.println("Current time: " + this.managementSystem.getTime());
    }

    public void login() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Please enter your credentials to access the system");
        System.out.println("To login as a guest, use `guest` as username and password");

        while (true) {
            System.out.print("Username: ");
            String username = stdin.nextLine();

            System.out.print("Password: ");
            String passwd = stdin.nextLine();

            if (username.equals("guest") && passwd.equals("guest")) {
                this.permissionLevel = PermissionLevel.GUEST;
                break;
            }

            if (username == "admin") {
                Credentials adminCredentials = credentialsDao.getCredentialsForUname(username);
                if (adminCredentials.checkHash(passwd)) {
                    this.permissionLevel = PermissionLevel.ADMIN;
                    break;
                }
                System.out.println("Wrong password, retry");

            } else {
                Credentials credentials = credentialsDao.getCredentialsForUname(username);
                if (credentials == null) {
                    System.out.println("Error: username not found, retry");
                    continue;
                }

                if (credentials.checkHash(passwd)) {
                    this.permissionLevel = PermissionLevel.EMPLOYEE;
                    this.id = credentials.id;
                    break;
                }
                System.out.println("Wrong password, retry");
            }
        }

        this.menuSwitch();
    }

    private void menuSwitch() {
        switch (this.permissionLevel) {
            case PermissionLevel.ADMIN -> adminMenu();
            case PermissionLevel.EMPLOYEE -> employeeMenu();
            case PermissionLevel.GUEST -> this.guestMenu();
        }
    }

    private void adminMenu() {
        boolean backToLogin = false;

        while (true) {
            System.out.println("Main menu:");
            System.out.println("1 -> Access employees data");
            System.out.println("2 -> Access aircrafts details");
            System.out.println("3 -> Access routes details");
            System.out.println("4 -> Access flight schedule");
            System.out.println("5 -> Access airport details");
            System.out.println("6 -> Back To Login");
            System.out.println("7 -> Quit");

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
                        this.accessAirportDetails();
                        break;

                    case 6:
                        backToLogin = true;
                        break;

                    case 7:
                        this.quit();
                        break;
                }

                if (backToLogin) {
                    break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 7 corresponding to the navigation choice");
                continue;
            }
        }

        if (backToLogin) {
            this.login();
        }
    }

    private void employeeMenu() {
        boolean backToLogin = false;

        while (true) {
            System.out.println("Main menu:");
            System.out.println("1 -> Access routes details");
            System.out.println("2 -> Access flight schedule");
            System.out.println("3 -> Back to login");
            System.out.println("4 -> Quit");

            Scanner stdin = new Scanner(System.in);
            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.accessRoutesDetails();
                        break;

                    case 2:
                        this.accessFlightSchedule();
                        break;

                    case 3:
                        backToLogin = true;
                        break;

                    case 4:
                        this.quit();
                        break;
                }

                if (backToLogin) {
                    break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 4 corresponding to the navigation choice");
                continue;
            }
        }

        if (backToLogin) {
            this.login();
        }
    }

    private void guestMenu() {
        System.out.println("Being logged in as `guest`, it is only possible to view flight route details");
        System.out.println();

        this.accessRoutesDetails();
        this.login();
    }

    public void run() {
        banner();
        login();
    }

    private void waitUntilEnter() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nPress <Enter> key when you're done");
        stdin.nextLine();
    }

    private void accessEmployeesData() {
        Scanner stdin = new Scanner(System.in);
        if (this.permissionLevel == PermissionLevel.ADMIN) {
            while (true) {
                System.out.println("\nEmployees data menu");
                System.out.println("1 -> View all employees");
                System.out.println("2 -> View all commanders");

                System.out.println("3 -> View all first officers");
                System.out.println("4 -> View all flight assistants");

                System.out.println("5 -> View a specific employee");
                System.out.println("6 -> Edit specific employee");
                System.out.println("7 -> back");

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
                        for (var employee : employeeDao.getAll()) {
                            System.out.println(employee);
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 2: {
                        for (var commander : employeeDao.getAllCommanders()) {
                            System.out.println(commander + "\n");
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 3: {
                        for (var firstOfficer : employeeDao.getAllFirstOfficers()) {
                            System.out.println(firstOfficer + "\n");
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 4: {
                        for (var flightAssistant : employeeDao.getAllFlightAssistants()) {
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

                        var employee = employeeDao.getEmployeeById(String.valueOf(requestedId));
                        if (employee != null) {
                            System.out.println(employee.getFullData());
                            waitUntilEnter();

                        } else {
                            System.out.println("ID not found");
                        }
                        break;
                    }

                    case 6: {
                        this.employeeCrud();
                    }

                    case 7: {
                        return;
                    }

                    default: {
                        System.out.println("Invalid choice\n");
                    }
                }
            }
        } else {
            System.out.println(employeeDao.getEmployeeById(String.valueOf(this.id)).getFullData());
            waitUntilEnter();
        }
    }

    private void accessAircraftDetails() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Aircraft menu:");
        System.out.println("1 -> View aircrafts");
        System.out.println("2 -> Edit aircrafts");
        System.out.println("3 -> Back");
        System.out.println();

        while (true) {
            boolean exit = false;

            try {
                System.out.print("Coice: ");
                int choice = stdin.nextInt();

                switch (choice) {
                    case 1: {
                        for (var airctaft : aircraftDao.getAllInstances()) {
                            System.out.println(airctaft.fullData());
                            System.out.println();
                        }
                        waitUntilEnter();
                        break;
                    }

                    case 2: {
                        this.aircraftCrud();
                        break;
                    }

                    case 3:
                        exit = true;
                        break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
            }

            if (exit) {
                break;
            }
        }
    }

    private void accessRoutesDetails() {
        if (this.permissionLevel != PermissionLevel.ADMIN) {
            System.out.println(flightRouteDao.getAll());
            System.out.println();
            waitUntilEnter();
            return;
        }

        while (true) {
            Scanner stdin = new Scanner(System.in);
            System.out.println("Flight route menu:");
            System.out.println("1 -> View routes");
            System.out.println("2 -> Edit routes");
            System.out.println("3 -> Back");
            System.out.println();

            try {
                int choice = stdin.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.println(flightRouteDao.getAll());
                        System.out.println();
                        waitUntilEnter();
                        break;
                    }

                    case 2: {
                        this.routeCrud();
                        break;
                    }

                    case 3: {
                        return;
                    }
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
            }
        }
    }

    private void accessFlightSchedule() {
        if (this.permissionLevel == PermissionLevel.ADMIN) {
            System.out.println("root access");

            for (var flight : this.managementSystem.scheduledFlights) {
                System.out.println(flight.toString());
            }

        } else {
            for (var flight : this.managementSystem.scheduledFlights) {
                if (flight.employeeIds.contains(this.id)) {
                    System.out.println(flight.toString());
                }
            }
        }

        waitUntilEnter();
    }

    private void accessAirportDetails() {
        while (true) {
            Scanner stdin = new Scanner(System.in);
            System.out.println("Aircraft menu:");
            System.out.println("1 -> View airports");
            System.out.println("2 -> Edit airports");
            System.out.println("3 -> Back");
            System.out.println();

            try {
                int choice = stdin.nextInt();

                switch (choice) {
                    case 1: {
                        for (var airport : airportDaoPg.getAll()) {
                            System.out.println(airport.toString());
                        }

                        System.out.println();
                        waitUntilEnter();
                        break;
                    }

                    case 2: {
                        this.airportCrud();
                        break;
                    }

                    case 3:
                        return;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
            }
        }
    }

    /* EMPLOYEE CRUD */

    public void employeeCrud() {
        Scanner stdin = new Scanner(System.in);

        while (true) {
            System.out.println("Select action:");
            System.out.println("1 -> Insert");
            System.out.println("2 -> Remove");
            System.out.println("3 -> Exit");

            System.out.print("Choice: ");
            System.out.print("Navigate to: ");

            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.insertEmployee();
                        break;

                    case 2:
                        this.deleteEmployee();
                        break;

                    case 3:
                        return;

                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
                continue;
            }
        }
    }

    private void insertEmployee(){
        Scanner stdin = new Scanner(System.in);
        
        System.out.print("name: ");
        String name= stdin.nextLine();

        System.out.print("last name: ");
        String lastname= stdin.nextLine();

        EmployeeRole role = choseRole();
        
        String abilitation= "";
        if (role == EmployeeRole.Commander | role == EmployeeRole.FirstOfficer){
            System.out.print("abilitation: ");
            abilitation=stdin.nextLine();
        }

        try {
            employeeDao.create(name, lastname, role.toString(), abilitation);

        } catch (RuntimeException e) {
            System.out.println("Error: cannot insert new employee");
        }
    }

    private EmployeeRole choseRole() {
        Scanner stdin = new Scanner(System.in);
        EmployeeRole employeeRole=null;

        while (employeeRole == null ) {
            System.out.println("#Choose the role#");
            System.out.println("1 -> Commander");
            System.out.println("2 -> First Officer");
            System.out.println("3 -> Flight Assistance");

            int input =stdin.nextInt();
            switch (input) {
                case 1:
                    employeeRole = EmployeeRole.Commander;
                    break;
                case 2:
                    employeeRole= EmployeeRole.FirstOfficer;
                    break;
                case 3:
                    employeeRole= EmployeeRole.FlightAssistant;
                    break;
                default:
                    employeeRole=null;
                    break;
            }
        }

        return employeeRole;
        
    }

    private void deleteEmployee(){
        Scanner stdin = new Scanner(System.in);

        for (var employee : employeeDao.getAll()) {
            System.out.println(employee.getFullData());
        }

        System.out.println();
        System.err.print("id: ");

        int id=stdin.nextInt();
        try {
            employeeDao.delete(id);

        } catch (RuntimeException e) {
            System.out.println("Error: cannot remove employee (it is probably busy by flight schedule)");
        }
    }

    /* AIRCRAFT CRUD */

    public void aircraftCrud() {
        Scanner stdin = new Scanner(System.in);

        while (true) {
            System.out.println("Select action:");
            System.out.println("1 -> Insert");
            System.out.println("2 -> Remove");
            System.out.println("3 -> Exit");

            System.out.print("Navigate to: ");

            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.insertAircraft();
                        break;

                    case 2:
                        this.deleteAircraft();
                        break;

                    case 3:
                        return;

                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
                continue;
            }
        }
    }

    private void insertAircraft(){
        Scanner stdin = new Scanner(System.in);

        while (true) {
            System.out.println("Select action:");
            System.out.println("1 -> insert Aircraft");
            System.out.println("2 -> insert Aircraft-instance");
            System.out.println("3 -> Exit");
            System.out.print("Choice: ");
            System.out.print("Navigate to: ");

            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.insertAircraftModel();
                        break;

                    case 2:
                        this.insertAircraftInstance();
                        break;

                    case 3:
                        return;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
                continue;
            }
        }
    }

    private void insertAircraftModel(){
        Scanner stdin = new Scanner(System.in);

        System.out.println("Manufaturer: ");
        String manufaturer =stdin.nextLine();

        System.out.println("Model: ");
        String model =stdin.nextLine();

        System.out.println("Specification: ");
        String specifiation =stdin.nextLine();

        System.out.println("Range:");
        int range=stdin.nextInt();

        System.out.println("Assistants number:");
        int assistantsNumber=stdin.nextInt();

        stdin.nextLine();// Magic. Do not touch.
        System.out.println("Dimension class:");

        String dimCText= stdin.nextLine();
        DimensionClass dimC=DimensionClass.fromString(dimCText);

        System.out.println("Seats:");
        int seats=stdin.nextInt();

        Aircraft aircraft=new Aircraft(manufaturer, model, specifiation, range, assistantsNumber, dimC, seats);
        try {
            aircraftDao.createModel(aircraft);

        } catch (RuntimeException e) {
            System.out.println("Error: cannot create aircraft model");
        }
    }

    private void insertAircraftInstance(){
        Scanner stdin = new Scanner(System.in);
        Vector<AircraftModel> models = aircraftDao.getAllModels();

        for (int i = 0; i < models.size(); i++) {
            System.err.println("["+Integer.toString(i)+"] " + models.get(i) );
        }

        System.err.println("chose the model: ");
        int chosenModel=stdin.nextInt();

        stdin.nextLine();
        System.err.println("plate: ");

        String plate=stdin.nextLine();
        try {
            aircraftDao.createInstance(plate, String.valueOf(chosenModel));

        } catch (RuntimeException e) {
            System.out.println("Error: cannot insert new aircraft instance");
        }
    }

    public void deleteAircraft() {
        Scanner stdin = new Scanner(System.in);
        while (true) {
            System.out.println("Select action:");
            System.out.println("1 -> delete Aircraft");
            System.out.println("2 -> delete Aircraft-instance");
            System.out.println("3 -> Exit");
            System.out.print("Choice: ");
            System.out.print("Navigate to: ");

            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.deleteAircraftModel();
                        break;

                    case 2:
                        this.deleteAircraftInstance();
                        break;
                    case 3:
                        return;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
                continue;
            }
        }

    }

    public void deleteAircraftModel(){
        Scanner stdin = new Scanner(System.in);
        Vector<AircraftModel> models = aircraftDao.getAllModels();

        for (int i = 0; i < models.size(); i++) {
            System.err.println("["+Integer.toString(i)+"] " + models.get(i) );
        }

        System.err.println("chose the model: ");
        int chosenModel=stdin.nextInt();

        try {
            aircraftDao.deleteModel(String.valueOf(chosenModel));

        } catch (RuntimeException e) {
            System.out.println("Error: cannot delete aircraft model (it is possible that one or more aircrafts of its type are still present)");
        }
    }

    public void deleteAircraftInstance(){
        Scanner stdin = new Scanner(System.in);
        ArrayList<String> plates = new ArrayList<>();

        for (var aircraft : aircraftDao.getAllInstances()) {
            System.out.println(aircraft.toString());
            plates.add(aircraft.plate);
        }

        System.out.print("\nEnter aircraft plate: ");
        String plate = stdin.nextLine();

        if (plates.contains(plate)) {
            try {
                aircraftDao.deleteInstance(plate);

            } catch (RuntimeException e) {
                System.out.println("Error: Impossible to delete airctaft instance " + plate + " (it is probably busy by flight schedule)");

            } finally {
                System.out.println("Aircraft " + plate + " removed. Returning to menu\n");
            }
        }
    }

    /* AIRPORT CRUD */

    public void airportCrud() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("\nOptions");
        System.out.println("1 -> Insert new airport");
        System.out.println("2 -> Update airport");
        System.out.println("3 -> Remove airport");
        System.out.println("4 -> Exit");
        System.out.print("Navigate to: ");

        try {
            int choice = stdin.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    this.createAirport();
                    return;

                case 2:
                    this.updateAirport();
                    return;

                case 3:
                    this.removeAirport();
                    return;

                case 4:
                    return;
            }

        } catch (InputMismatchException ex) {
            System.out.println("Enter a number from 1 to 4 corresponding to the navigation choice");
        }
    }

    public void createAirport() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Adding new Airport");

        System.out.print("ICAO: ");
        String icao = stdin.nextLine().trim();

        System.out.print("\nDimension Class: ");
        String dimensionClass = stdin.nextLine().trim();

        System.out.print("\nName: ");
        String name = stdin.nextLine().trim();

        System.out.print("\nNation: ");
        String nation = stdin.nextLine().trim();

        System.out.print("\nCity: ");
        String city = stdin.nextLine().trim();

        try {
            airportDaoPg.create(icao, dimensionClass, name, nation, city);
            System.out.println("Insertion successful");
        } catch (RuntimeException e) {
            System.out.println("Insertion failed");
        }
    }

    public void updateAirport() {
        Scanner stdin = new Scanner(System.in);

        System.out.print("\nICAO: ");
        String icao = stdin.nextLine().trim();

        System.out.print("\nNew dimension class");
        String dimensionClass = stdin.nextLine().trim();


        try {
            airportDaoPg.update(icao, dimensionClass);
            System.out.println("Update successful");
        } catch (RuntimeException e) {
            System.out.println("Update failed");
        }
    }

    public void removeAirport() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nICAO: ");

        String icao = stdin.nextLine().trim();

        try {
            airportDaoPg.delete(icao);
            System.out.println("Removal successful");

        } catch (RuntimeException e) {
            System.out.println("Removal failed");
        }
    }

    /* ROUTE CRUD */

    public void routeCrud() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("\nOptions:");
        System.out.println("1 -> Create route");
        System.out.println("2 -> Delete route");
        System.out.println("3 -> Exit");
        System.out.print("Navigate to: ");

        while (true) {
            try {
                int choice = stdin.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        this.createRoute();
                        return;

                    case 2:
                        this.deleteRoute();
                        return;

                    case 3:
                        return;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 3 corresponding to the navigation choice");
            }
        }
    }

    public void createRoute() {
        Scanner stdin = new Scanner(System.in);

        System.out.print("Distance: ");
        int distance = stdin.nextInt();

        System.out.print("Duration: ");
        int duration = stdin.nextInt();

        System.out.print("Depature: ");
        String departure = stdin.nextLine().trim();

        System.out.print("Stepover: ");
        String stepover = stdin.nextLine().trim();

        System.out.println("Arrival: ");
        String arrival = stdin.nextLine().trim();

        try {
            flightRouteDao.create(distance, duration, departure, stepover, arrival);
            System.out.println("Route creation successful");

        } catch (RuntimeException e) {
            System.out.println("Route creation failed");
        }
    }

    public void deleteRoute() {
        Scanner stdin = new Scanner(System.in);

        System.out.print("Route id: ");
        int id = stdin.nextInt();

        try {
            flightRouteDao.delete(String.valueOf(id));
            System.out.println("Route deletion successful");

        } catch (RuntimeException e) {
            System.out.println("Route deletion failed");

        }
    }

    private void quit() {
        this.managementSystem.quit();
        System.exit(0);
    }
}