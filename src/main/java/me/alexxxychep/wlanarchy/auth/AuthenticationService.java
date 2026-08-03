package me.alexxxychep.wlanarchy.auth;

import com.google.inject.Singleton;

import java.util.UUID;

@Singleton
public class AuthenticationService {
    public boolean isAuthenticated(UUID uuid) {
        return true;
    }
}
