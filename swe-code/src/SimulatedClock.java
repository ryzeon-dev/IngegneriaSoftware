import java.util.concurrent.Semaphore;

public class SimulatedClock extends Thread {
    private Semaphore mutex = new Semaphore(0);
    private long counter = 0;
    public boolean run = true;
    public int timeCoefficient = 60; // impostato a 60, ogni secondo corrisponde ad un minuto

    SimulatedClock() {

    }

    public void run() {
        try {
            while (this.run) {
                sleep(1000);
            }

            this.mutex.acquire();
            this.counter += 1;
            this.mutex.release();

        } catch (InterruptedException ex) {

        }

    }

    public String getTime() {
        try {
            this.mutex.acquire();
            long current = this.counter;
            this.mutex.release();

            String hour = String.valueOf((current - (current % this.timeCoefficient)) / this.timeCoefficient);
            String minutes = String.valueOf(current % this.timeCoefficient);

            return hour + ":" + minutes;

        } catch (InterruptedException ex) {
            return "";
        }
    }
}

