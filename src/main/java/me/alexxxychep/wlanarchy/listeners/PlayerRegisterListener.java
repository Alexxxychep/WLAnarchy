package me.alexxxychep.wlanarchy.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.alexxxychep.wlanarchy.Rank;
import me.alexxxychep.wlanarchy.players.WLPlayerService;
import me.alexxxychep.wlanarchy.ranks.RankService;
import me.alexxxychep.wlanarchy.service.ServiceException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.UUID;

@Singleton
public class PlayerRegisterListener implements Listener {
    private final WLPlayerService playerService;
    private final Logger logger = LoggerFactory.getLogger(PlayerRegisterListener.class);
    private final RankService rankService;

    @Inject
    public PlayerRegisterListener(WLPlayerService playerService, RankService rankService) {
        this.playerService = playerService;
        this.rankService = rankService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        playerService.registerPlayer(uuid);
        try {
            logger.info(rankService.getRank(uuid).name());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        playerService.unregisterPlayer(uuid);
        try {
            rankService.setRank(uuid, Rank.DEVELOPER);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
