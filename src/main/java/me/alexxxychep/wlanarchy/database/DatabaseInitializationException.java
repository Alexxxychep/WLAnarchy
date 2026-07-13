package me.alexxxychep.wlanarchy.database;

import java.sql.SQLException;

public class DatabaseInitializationException extends DatabaseException {
    public DatabaseInitializationException(String message, SQLException cause) {
        super(message, cause);
    }
}
