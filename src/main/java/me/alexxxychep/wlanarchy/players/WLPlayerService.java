package me.alexxxychep.wlanarchy.players;

import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class WLPlayerService {
    private final Map<UUID, WLPlayer> onlinePlayers = new HashMap<>();

    public void registerPlayer(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null!");
        if(onlinePlayers.containsKey(uuid)) {
            return;
        }
        WLPlayer player = new WLPlayer(uuid);
        onlinePlayers.put(uuid, player);
    }

    public void unregisterPlayer(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null!");
        if(!onlinePlayers.containsKey(uuid)) {
            return;
        }
        onlinePlayers.remove(uuid);
    }


    public boolean isPlayerOnline(UUID uuid) {
        return onlinePlayers.containsKey(uuid);
    }

    public boolean isPlayerOnline(WLPlayer player) {
        return onlinePlayers.containsKey(player.getUuid());
    }

    public WLPlayer getOnlinePlayer(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null!");
        return onlinePlayers.get(uuid);
    }
}
