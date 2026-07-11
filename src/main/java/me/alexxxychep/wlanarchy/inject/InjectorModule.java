package me.alexxxychep.wlanarchy.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.alexxxychep.wlanarchy.WLAnarchy;
import me.alexxxychep.wlanarchy.database.DatabaseCredentialsHandler;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import me.alexxxychep.wlanarchy.players.WLPlayerService;
import me.alexxxychep.wlanarchy.ranks.RankService;
import me.alexxxychep.wlanarchy.ranks.WLPlayerRankDao;
import org.bukkit.plugin.java.JavaPlugin;

public class InjectorModule extends AbstractModule {
    private final WLAnarchy plugin;

    public InjectorModule(WLAnarchy plugin) {
        this.plugin = plugin;
    }

    @Override
    public void configure() {
        bind(WLAnarchy.class).toInstance(plugin);
        bind(DatabaseService.class).in(Singleton.class);
        bind(RankService.class).in(Singleton.class);
        bind(JavaPlugin.class).toInstance(plugin);
        bind(WLPlayerService.class).in(Singleton.class);
        bind(WLPlayerRankDao.class).in(Singleton.class);
        bind(DatabaseCredentialsHandler.class).in(Singleton.class);
    }
}