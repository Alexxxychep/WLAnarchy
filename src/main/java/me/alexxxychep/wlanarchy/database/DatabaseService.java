package me.alexxxychep.wlanarchy.database;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DatabaseService {
    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_RETRY_DELAY_MS = 1000;
    private static final long MAX_RETRY_DELAY_MS = 30000;
    private static final int CONNECTION_TEST_TIMEOUT_SECONDS = 5;

    private final Logger logger;
    private final DatabaseCredentialsHandler databaseCredentialsHandler;

    private HikariDataSource dataSource;
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private final AtomicBoolean shuttingDown = new AtomicBoolean(false);

    @Inject
    public DatabaseService(Logger logger, DatabaseCredentialsHandler databaseCredentialsHandler) {
        this.logger = logger;
        this.databaseCredentialsHandler = databaseCredentialsHandler;
    }

    public void initializeDatabase() throws DatabaseInitializationException {
        if(initialized.get()) {
            logger.warning("Attempted to initialize an already initialized database");
            return;
        }
        connectWithRetry();
        logger.info("Database initialized successfully");
    }

    private void connectWithRetry() throws DatabaseInitializationException {
        long retryDelay = INITIAL_RETRY_DELAY_MS;
        int attempt = 0;

        while(attempt < MAX_RETRIES && !shuttingDown.get()) {
            attempt++;
            logger.info("Database connection attempt " + attempt + " of " + MAX_RETRIES);

            try {
                dataSource = createHikariDataSource();

                if(testConnection()) {
                    return;
                }
            } catch(SQLException e) {
                logger.log(Level.WARNING, "Connection attempt " + attempt + " failed: " + e.getMessage());
                MySQLError error = MySQLError.fromSQLException(e);
                if(error.equals(MySQLError.INCORRECT_CREDENTIALS)) {
                    logger.severe("MySQL Database Credentials are incorrect!");
                    throw new DatabaseInitializationException("MySQL Database Credentials are incorrect!",e);
                }
                if(error.equals(MySQLError.UNKNOWN_DATABASE)) {
                    logger.severe("MySQL Database does not exist!");
                    throw new DatabaseInitializationException("MySQL Database does not exist!", e);
                }
                if(error.equals(MySQLError.NO_DATABASE_SELECTED)) {
                    logger.severe("No MySQL database selected in connection URL!");
                    throw new DatabaseInitializationException("No MySQL database selected!", e);
                }
                if(error.equals(MySQLError.DENIED_PERMISSION)) {
                    logger.severe("MySQL user lacks required permissions!");
                    throw new DatabaseInitializationException("MySQL user lacks required permissions!", e);
                }

                if(error.getClassification() == MySQLError.Classification.MUST_EVICT) {
                    logger.severe("Fatal MySQL error - won't resolve with retries: " + error.name());
                    throw new DatabaseInitializationException("Fatal MySQL error: " + error.name(), e);
                }

                if(attempt < MAX_RETRIES && !shuttingDown.get()) {
                    long jitter = (long) (retryDelay * 0.2 * (Math.random() - 0.5));
                    long actualDelay = Math.max(100, retryDelay + jitter);

                    logger.info("Retrying in " + actualDelay + "ms");
                    try {
                        TimeUnit.MILLISECONDS.sleep(actualDelay);
                    } catch(InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new DatabaseInitializationException("Database connection interrupted", e);
                    }
                    retryDelay = Math.min(retryDelay * 2, MAX_RETRY_DELAY_MS);
                } else {
                    throw new DatabaseInitializationException("Database connection failed after " + attempt + " attempts: " + e.getMessage(), e);
                }
            }
        }
    }

    public void initTables() throws DatabaseExecutionException {
        initRankTable();
    }

    public boolean testConnection() throws SQLException {
        if(dataSource == null) {
            logger.warning("Cannot test connection: DataSource is null");
            return false;
        }

        try(Connection testConnection = dataSource.getConnection()) {
            if(testConnection.isValid(CONNECTION_TEST_TIMEOUT_SECONDS)) {
                logger.info("Database connection test successful");
                return true;
            } else {
                logger.warning("Connection validation failed");
                return false;
            }
        }
    }

    public HikariDataSource createHikariDataSource() throws DatabaseInitializationException {
        if(!databaseCredentialsHandler.areCredentialsValid()) {
            throw new DatabaseInitializationException("Not all database credentials are valid");
        }

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(databaseCredentialsHandler.getURL());
        config.setUsername(databaseCredentialsHandler.getUser());
        config.setPassword(databaseCredentialsHandler.getPassword());

        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void initRankTable() throws DatabaseExecutionException {
        String query = "CREATE TABLE IF NOT EXISTS ranks ( uuid BINARY(16) PRIMARY KEY, rankname VARCHAR(255) )";

        try(
                Connection conn = getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            throw new DatabaseExecutionException(e.getMessage(), e, query);
        }
    }


    public boolean isReady() {
        return dataSource != null && dataSource.isClosed();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public synchronized void closePool() {
        if(dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
