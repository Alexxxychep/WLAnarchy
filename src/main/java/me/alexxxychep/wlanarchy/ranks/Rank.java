package me.alexxxychep.wlanarchy.ranks;

import java.util.HashMap;
import java.util.Map;

public enum Rank {
    PLAYER, VIP, MODERATOR, DEVELOPER;

    private static final Map<String, Rank> RANK_BY_NAME = new HashMap<>();

    static {
        for(Rank rank : values()) {
            RANK_BY_NAME.put(rank.name().toLowerCase(), rank);
        }
    }

    public static Rank getRankByName(String name) {
        if (name == null) {
            return Rank.PLAYER;
        }
        return RANK_BY_NAME.getOrDefault(name.toLowerCase(), Rank.PLAYER);
    }
}
