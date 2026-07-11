package me.alexxxychep.wlanarchy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import me.alexxxychep.wlanarchy.inject.InjectorModule;
import me.alexxxychep.wlanarchy.listeners.PlayerJoinBlocker;
import me.alexxxychep.wlanarchy.ranks.RankService;
import org.bukkit.plugin.java.JavaPlugin;


public class WLAnarchy extends JavaPlugin {
    private Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new InjectorModule(this));
        injector.getInstance(DatabaseService.class).initializeDatabase();
        getServer().getPluginManager().registerEvents(injector.getInstance(PlayerJoinBlocker.class), this);
    }

    @Override
    public void onDisable() {
        injector.getInstance(DatabaseService.class).closePool();
        injector.getInstance(RankService.class).shutdown();
    }


}
