package me.alexxxychep.wlanarchy.listeners;

import me.alexxxychep.wlanarchy.WLAnarchy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;


public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        if(!WLAnarchy.getDatabasePool().isReady()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Сервер не подключенг к датабазе").color(NamedTextColor.RED));
        }
    }
}
