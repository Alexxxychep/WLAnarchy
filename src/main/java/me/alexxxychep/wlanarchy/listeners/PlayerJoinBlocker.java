package me.alexxxychep.wlanarchy.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.atomic.AtomicBoolean;


public class PlayerJoinBlocker implements Listener {
    private final AtomicBoolean blocked = new AtomicBoolean(false);
    private volatile String kickMessage = "В данный момент сервер недоступен";

    public void block(String reason) {
        kickMessage = "В данный момент сервер недоступен: " + reason;
        blocked.set(true);
    }

    public void unblock() {
        blocked.set(false);
    }

    @EventHandler
    public void onPlayerAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        if(!blocked.get()) {
            return;
        }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text(kickMessage));
    }

}
