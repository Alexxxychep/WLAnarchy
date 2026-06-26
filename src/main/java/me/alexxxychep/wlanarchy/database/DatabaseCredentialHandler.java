package me.alexxxychep.wlanarchy.database;

public class DatabaseCredentialHandler {
    public static boolean areCredentialsValid() {
        return (getUser() != null) && (getPassword() != null) && (getAddress() != null) && (getPort() != null);
    }

    public static String getUser() {
        return System.getenv("DATABASE_USER");
    }

    public static String getPassword() {
        return System.getenv("DATABASE_PASSWORD");
    }

    public static String getAddress() {
        return System.getenv("DATABASE_ADDRESS");
    }

    public static String getPort() {
        return System.getenv("DATABASE_PORT");
    }
}
