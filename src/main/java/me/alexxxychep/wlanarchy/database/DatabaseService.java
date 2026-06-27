package me.alexxxychep.wlanarchy.database;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Singleton
public class DatabaseService {
    private HikariDataSource dataSource;
    private boolean ready = false;
    private boolean credentialsValid = false;
    private final JavaPlugin javaPlugin;
    private final ExecutorService executorService;

    private final Logger logger;

    @Inject
    public DatabaseService(JavaPlugin javaPlugin, Logger logger) {
        this.javaPlugin = javaPlugin;
        this.logger = logger;
        executorService = Executors.newCachedThreadPool();
    }

    public void init() {
        if (ready) {
            return;
        }

        if(!DatabaseCredentialHandler.areCredentialsValid()) {
            credentialsValid = false;
            return;
        }
        credentialsValid = true;
        dataSource = new HikariDataSource(getHikariConfig());
        ready = true;
        try {
            initRankTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HikariConfig getHikariConfig() {
        HikariConfig config = new HikariConfig();

        //temporary, will change the credentials to be put in as docker args
        config.setJdbcUrl("jdbc:mysql://localhost:3306/wldb");
        config.setUsername("root");
        config.setPassword("w5lllll");

        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void initRankTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS ranks ( uuid BINARY(16) PRIMARY KEY, rankname VARCHAR(255) )";

        try (
             Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            preparedStatement.executeUpdate();
        }
    }

    public boolean isCredentialsValid() {
        return credentialsValid;
    }

    public boolean isReady() {
        return ready && dataSource != null && !dataSource.isClosed();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public synchronized void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            ready = false;
        }
    }
}
