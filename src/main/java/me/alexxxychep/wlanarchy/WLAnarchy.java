package me.alexxxychep.wlanarchy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import me.alexxxychep.wlanarchy.inject.InjectorModule;
import me.alexxxychep.wlanarchy.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;


public class WLAnarchy extends JavaPlugin {
    private Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new InjectorModule(this));
        injector.getInstance(DatabaseService.class).init();
        getServer().getPluginManager().registerEvents(injector.getInstance(PlayerJoinListener.class), this);
        getCommand("setrank").setExecutor(injector.getInstance(SetRankCommand.class));
        getCommand("getrank").setExecutor(injector.getInstance(GetRankCommand.class));
    }

    @Override
    public void onDisable() {
        injector.getInstance(DatabaseService.class).closePool();
    }

}
