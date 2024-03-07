package cli;

import system.ManagementSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {//extends Thread {
    private ManagementSystem managementSystem;
    private boolean running = true;

    public CLI(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
    }

    public void run() {
        while (this.running) {
            System.out.println("##### ITA Airways management system #####");
            System.out.println("Current time: " + this.managementSystem.getTime());
            System.out.println("Main menu:");
            System.out.println("1 -> Access employees data");
            System.out.println("2 -> Access aircrafts details");
            System.out.println("3 -> Access routes details");
            System.out.println("4 -> Access flight schedule");
            System.out.println("5 -> Access personal area");
            System.out.println("6 -> Quit");

            System.out.print("\nNavigate to: ");

            Scanner stdin = new Scanner(System.in);
            try {
                int choice = stdin.nextInt();
                System.out.println("Choice: " + choice);

                if (choice == 6) {
                    this.managementSystem.quit();
                    System.exit(0);
                }

            } catch (InputMismatchException ex) {
                System.out.println("Enter a number from 1 to 6 corresponding to the navigation choice");
                continue;
            }



        }
    }
}
