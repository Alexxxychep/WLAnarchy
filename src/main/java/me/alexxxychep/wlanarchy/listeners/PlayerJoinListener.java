package me.alexxxychep.wlanarchy.listeners;

import com.google.inject.Inject;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.logging.Logger;


public class PlayerJoinListener implements Listener {
    private final DatabaseService databaseService;
    private final Logger logger;

    @Inject
    public PlayerJoinListener(Logger logger, DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.logger = logger;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        logger.info(String.valueOf(databaseService.isReady()));
        logger.info(databaseService.toString());
        if(!databaseService.isReady()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Сервер не подключенг к датабазе").color(NamedTextColor.RED));
        }
    }
}
