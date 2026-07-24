package me.alexxxychep.wlanarchy.database;

import me.alexxxychep.wlanarchy.WLException;

import java.sql.SQLException;


public class DatabaseException extends WLException {
    private final MySQLError error;

    public DatabaseException(String message, SQLException cause) {
        super(message, cause);
        this.error = MySQLError.fromSQLException(cause);
        this.addContext("Error", error.toString());
    }

    public MySQLError getError() {
        return error;
    }
}
