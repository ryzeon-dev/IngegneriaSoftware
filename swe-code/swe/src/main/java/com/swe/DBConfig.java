package com.swe;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
    private static final Properties properties = new Properties();
    static {
        try (InputStream file = DBConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (file == null) {
                System.out.println("Fatal error: db.properties not found");
                System.exit(1);
            }
            properties.load(file);

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("Fatal error: cannot read db.properties");
            System.exit(1);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getLocalnetUrl() {
        return properties.getProperty("db.localneturl");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPasswd() {
        return properties.getProperty("db.passwd");
    }
}