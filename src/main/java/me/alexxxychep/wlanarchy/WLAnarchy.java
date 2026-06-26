package me.alexxxychep.wlanarchy;

import me.alexxxychep.wlanarchy.database.DatabasePool;
import me.alexxxychep.wlanarchy.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;


public class WLAnarchy extends JavaPlugin {
    private static final DatabasePool databasePool = new DatabasePool();


    @Override
    public void onEnable() {
        databasePool.init();
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("setrank").setExecutor(new SetRankCommand());
        getCommand("getrank").setExecutor(new GetRankCommand());
    }

    @Override
    public void onDisable() {
        databasePool.closePool();
    }

    public static DatabasePool getDatabasePool() {
        return databasePool;
    }
}
