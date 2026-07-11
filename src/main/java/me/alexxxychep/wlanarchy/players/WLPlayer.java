package me.alexxxychep.wlanarchy.players;

import me.alexxxychep.wlanarchy.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WLPlayer {
    private UUID uuid;

    private Rank rank;

    public WLPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public Rank getRank() {
        return rank;
    }

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
