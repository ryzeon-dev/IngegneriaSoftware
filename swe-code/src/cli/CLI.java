package cli;

import system.ManagementSystem;

import java.util.Scanner;

public class CLI {//extends Thread {
    private ManagementSystem managementSystem;
    private boolean running = true;

    CLI(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
    }

    public void run() {
        while (this.running) {
            System.out.println("##### ITA Airways management system #####");
            System.out.println("Current time: "); // TODO implementare la lettura dall'orologio simulato
            System.out.println("Main menu:");
            System.out.println("1 -> Access employees data");
            System.out.println("2 -> Access aircrafts details");
            System.out.println("3 -> Access routes details");
            System.out.println("4 -> Access flight schedule");
            System.out.println("5 -> Access personal area");

            System.out.print("\nNavigate to: ");

            Scanner stdin = new Scanner(System.in);
            int choice = stdin.nextInt();

            System.out.println("Choice: " + choice);
        }
    }
}
