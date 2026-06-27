package me.alexxxychep.wlanarchy.ranks;

import com.google.inject.Inject;
import me.alexxxychep.wlanarchy.Rank;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class RankService {
    private final WLPlayerRankDao rankDao;

    @Inject
    public RankService(DatabaseService service) {
        this.rankDao = new WLPlayerRankDao(service.getDataSource());
    }

    public Rank getRank(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        return rankDao.getRankFromUUID(uuid);
    }

    public void setRank(UUID uuid, Rank rank) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        Objects.requireNonNull(rank, "Rank cannot be null");
        rankDao.saveRank(uuid, rank);
    }
}
