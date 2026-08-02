package me.alexxxychep.wlanarchy.chat;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class ChatListener implements Listener {
    ChatMessageFactory messageFactory = new ChatMessageFactory();

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        WLChatMessage chatMessage = messageFactory.fromComponent(event.message());
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            
            return message;
        });
    }

    @EventHandler
    public void onAsyncChatDecorate(AsyncChatDecorateEvent event) {
        UUID uuid = event.player().getUniqueId();

    }
}
