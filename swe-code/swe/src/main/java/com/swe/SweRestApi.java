package com.swe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SweRestApi {
    public static void main(String[] args) {
        SpringApplication.run(SweRestApi.class, args);
        // PgDB db = new PgDB();
        // var res = db.runAndFetch("select * from aircraft_instance;");
        // System.out.println(res);
    }
}