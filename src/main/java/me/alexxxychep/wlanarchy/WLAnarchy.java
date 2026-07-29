package me.alexxxychep.wlanarchy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.alexxxychep.wlanarchy.database.DatabaseInitializationException;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import me.alexxxychep.wlanarchy.inject.InjectorModule;
import me.alexxxychep.wlanarchy.listeners.PlayerJoinBlocker;
import me.alexxxychep.wlanarchy.listeners.PlayerRegisterListener;
import me.alexxxychep.wlanarchy.players.WLPlayer;
import me.alexxxychep.wlanarchy.ranks.RankService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public class WLAnarchy extends JavaPlugin {
    private Injector injector;

    @Override
    public void onEnable() {

        injector = Guice.createInjector(new InjectorModule(this));
        enableDatabase();
        
        registerEvents();
    }

    @Override
    public void onDisable() {
        injector.getInstance(DatabaseService.class).closePool();
    }

    public void enableDatabase() {
        try {
            injector.getInstance(DatabaseService.class).initializeDatabase();
        } catch(DatabaseInitializationException e) {
            injector.getInstance(PlayerJoinBlocker.class).block("Датабаза не запустилась!");
            injector.getInstance(Logger.class).severe("Fatal error while initializing database! " + e.getMessage());
        }
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(injector.getInstance(PlayerJoinBlocker.class), this);
        getServer().getPluginManager().registerEvents(injector.getInstance(PlayerRegisterListener.class), this);
    }


}
