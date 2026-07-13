package me.alexxxychep.wlanarchy.database;

import java.sql.SQLException;

public class DatabaseExecutionException extends DatabaseException {
    private final String query;

    public DatabaseExecutionException(String message, SQLException cause, String query) {
        super(message, cause);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
