package me.alexxxychep.wlanarchy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WLPlayer {
    private UUID uuid;

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(uuid);
    }

    public boolean isOnline() {
        return Bukkit.getServer().getPlayer(uuid) != null;
    }

    public UUID getUuid() {
        return uuid;
    }
}
