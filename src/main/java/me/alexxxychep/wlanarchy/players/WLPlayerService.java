package me.alexxxychep.wlanarchy.players;

import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class WLPlayerService {
    private final Map<UUID, WLPlayer> onlinePlayers = new HashMap<>();

    public void registerPlayer(WLPlayer player) {
        Objects.requireNonNull(player, "Player cannot be null!");
        onlinePlayers.put(player.getUuid(), player);
    }

    public void unregisterPlayer(WLPlayer player) {
        if(player == null || !onlinePlayers.containsKey(player.getUuid())) {
            return;
        }
        onlinePlayers.remove(player.getUuid());
    }

    public boolean isPlayerOnline(WLPlayer player) {
        return onlinePlayers.containsKey(player.getUuid());
    }

    public WLPlayer getOnlinePlayer(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null!");
        return onlinePlayers.get(uuid);
    }
}
