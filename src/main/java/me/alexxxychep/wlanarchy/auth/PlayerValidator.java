package me.alexxxychep.wlanarchy.auth;

import com.google.inject.Singleton;

import java.util.UUID;

@Singleton
public class PlayerValidator {
    public boolean canText(UUID uuid) {
        return true;
    }
}
