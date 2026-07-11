package me.alexxxychep.wlanarchy.database;

public class FatalDatabaseInitializationException extends Exception {
    public FatalDatabaseInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
    public FatalDatabaseInitializationException(String message) {
        super(message);
    }

}
