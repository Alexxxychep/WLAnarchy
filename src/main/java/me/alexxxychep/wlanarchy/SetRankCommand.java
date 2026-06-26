package me.alexxxychep.wlanarchy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SetRankCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player player)) {
            return true;
        }
        if(strings.length == 0) {
            return true;
        }
        Rank rank = Rank.getRankByName(strings[0]);
        System.out.println(strings[0]);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return Arrays.stream(Rank.values())
                .map(rank -> rank.toString().toLowerCase())
                .toList();
    }
}
