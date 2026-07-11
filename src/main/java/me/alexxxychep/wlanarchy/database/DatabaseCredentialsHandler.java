package me.alexxxychep.wlanarchy.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.logging.Logger;

@Singleton
public class DatabaseCredentialsHandler {

    public String getUser() {
        return System.getenv("DATABASE_USER");
    }

    public String getPassword() {
        return System.getenv("DATABASE_PASSWORD");
    }

    public String getAddress() {
        return System.getenv("DATABASE_ADDRESS");
    }

    public String getPort() {
        return System.getenv("DATABASE_PORT");

    }

    public String getName() {
        return System.getenv("DATABASE_NAME");
    }

    public String getURL() throws FatalDatabaseInitializationException {
        if(!areCredentialsValid()) {
            throw new FatalDatabaseInitializationException("Some credentials are null, or port can't be parsed");
        }
        return String.format("jdbc:mysql://%s:%s/%s", getAddress(), getPort(), getName());
    }

    public boolean areCredentialsValid() {
        return getUser() != null && getPassword() != null && getAddress() != null && getPort() != null;
    }
}
