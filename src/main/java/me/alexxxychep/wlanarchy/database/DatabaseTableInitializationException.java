package me.alexxxychep.wlanarchy.database;

import java.sql.SQLException;

public class DatabaseTableInitializationException extends DatabaseInitializationException {
    public DatabaseTableInitializationException(String message, SQLException cause) {
        super(message, cause);
    }

}
