package me.alexxxychep.wlanarchy.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.alexxxychep.wlanarchy.players.WLPlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import java.util.UUID;

@Singleton
public class PlayerRegisterListener implements Listener {
    private final WLPlayerService playerService;

    @Inject
    public PlayerRegisterListener(WLPlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        playerService.registerPlayer(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        playerService.unregisterPlayer(uuid);
    }
}
