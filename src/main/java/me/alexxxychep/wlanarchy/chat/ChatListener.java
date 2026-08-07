package me.alexxxychep.wlanarchy.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.alexxxychep.wlanarchy.auth.AuthenticationService;
import me.alexxxychep.wlanarchy.player.NameService;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

@Singleton
public class ChatListener implements Listener {
    private final AuthenticationService authenticationService;
    private final ChatMessageFactory messageFactory;

    @Inject
    public ChatListener(AuthenticationService authenticationService, NameService nameService) {
        this.authenticationService = authenticationService;
        this.messageFactory = new ChatMessageFactory(nameService);
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (!authenticationService.isAuthenticated(uuid)) {
            event.getPlayer().sendMessage(Component.text("You aren't authenticated!").color(TextColor.color(NamedTextColor.RED)));
            event.setCancelled(true);
            return;
        }

        WLChatMessage chatMessage = messageFactory.fromComponent(event.message(), uuid);

        if (!chatMessage.isGlobal()) {
            event.viewers().clear();
            event.viewers().addAll(getLocalAudience(event.getPlayer(), 80));
        }

        event.renderer((source, sourceDisplayName, message, viewer) -> {

            return chatMessage.toMessage();
        });
    }

    public Collection<Audience> getLocalAudience(Player player, int radius) {
        Location location = player.getLocation();
        World world = location.getWorld();
        if (world == null) return Collections.emptyList();

        Collection<Player> nearbyPlayers = world.getNearbyPlayers(
                location,
                radius,
                nearbyPlayer -> nearbyPlayer != player
        );

        @SuppressWarnings("unchecked")
        Collection<Audience> audience = (Collection<Audience>) (Collection<?>) nearbyPlayers;
        return audience;
    }

    @EventHandler
    public void onAsyncChatDecorate(AsyncChatDecorateEvent event) {
        UUID uuid = event.player().getUniqueId();

    }
}
