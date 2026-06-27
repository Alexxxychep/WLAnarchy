package me.alexxxychep.wlanarchy;

import com.google.inject.Inject;
import me.alexxxychep.wlanarchy.ranks.RankService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetRankCommand implements CommandExecutor {
    private final RankService rankService;

    @Inject
    public GetRankCommand(RankService rankService) {
        this.rankService = rankService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player player)) {
            return true;
        }
        return true;
    }
}
