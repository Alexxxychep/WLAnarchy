package me.alexxxychep.wlanarchy.ranks;

import me.alexxxychep.wlanarchy.Rank;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.UUID;

public class RankService {
    private final WLPlayerRankDao rankDao;

    public RankService(DataSource dataSource) {
        this.rankDao = new WLPlayerRankDao(dataSource);
    }

    public Rank getRank(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        return rankDao.getRankFromUUID(uuid);
    }

    public void setRank(@NotNull UUID uuid, @NotNull Rank rank) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        Objects.requireNonNull(rank, "Rank cannot be null");
        rankDao.saveRank(uuid, rank);
    }
}
