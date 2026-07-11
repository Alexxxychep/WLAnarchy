package me.alexxxychep.wlanarchy.database;

import java.sql.SQLException;

public enum MySQLError {
    INCORRECT_CREDENTIALS(1045, "28000", Classification.MUST_EVICT),
    DENIED_PERMISSION(1044, "42000", Classification.DO_NOT_EVICT),
    UNKNOWN_DATABASE(1049, "42000", Classification.DO_NOT_EVICT),
    NO_DATABASE_SELECTED(1046, "3D000", Classification.DO_NOT_EVICT),
    TOO_MANY_CONNECTIONS(1040, "08004", Classification.MUST_EVICT),
    BAD_HANDSHAKE(1042, "08S01", Classification.MUST_EVICT),
    HOSTNAME_ERROR(1043, "08S01", Classification.MUST_EVICT),
    SERVER_SHUTDOWN_IN_PROGRESS(1053, "08S01", Classification.MUST_EVICT),
    SYNTAX_ERROR(1064, "42000", Classification.DO_NOT_EVICT),
    UNKNOWN_TABLE(1051, "42S02", Classification.DO_NOT_EVICT),
    UNKNOWN_COLUMN(1054, "42S22", Classification.DO_NOT_EVICT),
    DUPLICATE_ENTRY_FOR_KEY(1062, "23000", Classification.DO_NOT_EVICT),
    GENERAL_ERROR(0, "HY000", Classification.CONTINUE_EVICT);

    private final int errorCode;
    private final String sqlState;
    private final Classification classification;

    MySQLError(int errorCode, String sqlState, Classification classification) {
        this.errorCode = errorCode;
        this.sqlState = sqlState;
        this.classification = classification;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getSqlState() {
        return sqlState;
    }

    public Classification getClassification() {
        return classification;
    }

    public static MySQLError fromSQLException(SQLException e) {
        int errorCode = e.getErrorCode();
        String sqlState = e.getSQLState();

        for (MySQLError error : values()) {
            if (error.errorCode == errorCode && error != GENERAL_ERROR) {
                return error;
            }
        }

        if (sqlState != null) {
            for (MySQLError error : values()) {
                if (sqlState.equals(error.sqlState) && error != GENERAL_ERROR) {
                    return error;
                }
            }

            if (sqlState.startsWith("08")) {
                return BAD_HANDSHAKE;
            }
        }

        return GENERAL_ERROR;
    }

    public enum Classification {
        MUST_EVICT,
        DO_NOT_EVICT,
        CONTINUE_EVICT
    }
}