package me.alexxxychep.wlanarchy.ranks;

import com.google.inject.Inject;
import me.alexxxychep.wlanarchy.Rank;
import me.alexxxychep.wlanarchy.database.DatabaseExecutionException;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RankService {
    private final WLPlayerRankDao rankDao;

    @Inject
    public RankService(WLPlayerRankDao rankDao) {
        this.rankDao = rankDao;
    }

    public CompletableFuture<Rank> getRankAsync(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        return rankDao.getRankFromUUIDAsync(uuid);
    }

    public CompletableFuture<Void> setRankAsync(UUID uuid, Rank rank) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        Objects.requireNonNull(rank, "Rank cannot be null");
        return rankDao.saveRankAsync(uuid, rank);
    }

    public Rank getRank(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        try {
            return rankDao.getRankFromUUID(uuid);
        } catch(DatabaseExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRank(UUID uuid, Rank rank) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        Objects.requireNonNull(rank, "Rank cannot be null");
        try {
            rankDao.saveRank(uuid, rank);
        } catch(DatabaseExecutionException e) {

        }
    }

    public void shutdown() {
        rankDao.shutdown();
    }
}
