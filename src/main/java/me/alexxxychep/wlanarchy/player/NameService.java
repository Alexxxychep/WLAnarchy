package me.alexxxychep.wlanarchy.player;

import com.google.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class NameService {
    public Map<UUID, String> customNames = new HashMap<>();


    public Component getDisplayName(UUID uuid) {
        return Component.text(getRawDisplayName(uuid));
    }

    public String getRawDisplayName(UUID uuid) {
        if(customNames.containsKey(uuid)) {
            return customNames.get(uuid);
        }
        return Bukkit.getPlayer(uuid).getName();
    }

    public void setCustomName(UUID uuid, String name) {
        customNames.put(uuid, name);
    }
}
