package me.alexxxychep.wlanarchy;

public enum Rank {
    PLAYER, VIP, MODERATOR, DEVELOPER;

    public static Rank getRankByName(String name) {
        for(Rank rank : values()) {
            if(rank.toString().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return Rank.PLAYER;
    }
}
