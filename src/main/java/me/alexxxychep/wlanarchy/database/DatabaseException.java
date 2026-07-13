package me.alexxxychep.wlanarchy.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseException extends Exception {
    private final MySQLError error;
    private final Map<String, String> context = new HashMap<>();
    public DatabaseException(String message, SQLException cause) {
        super(message, cause);
        this.error = MySQLError.fromSQLException(cause);
    }

    public MySQLError getError() {
        return error;
    }

    public Map<String, String> getContext() {
        return context;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(getMessage()).append("\n");
        builder.append("Error: ").append(getError().name()).append(" : Error code: ").append(error.getErrorCode()).append("\n");
        if(!getContext().isEmpty()) {
            builder.append("Context: \n");
            for(Map.Entry<String, String> contextEntry : getContext().entrySet()) {
                builder.append(contextEntry.getKey()).append(" -> ").append(contextEntry.getValue()).append("\n");
            }
        }
        return builder.toString();
    }
}
