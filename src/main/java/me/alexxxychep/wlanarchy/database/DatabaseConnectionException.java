package me.alexxxychep.wlanarchy.database;

import java.sql.SQLException;

public class DatabaseConnectionException extends DatabaseInitializationException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
    public DatabaseConnectionException(String message, SQLException cause) {
        super(message, cause);
    }
}
