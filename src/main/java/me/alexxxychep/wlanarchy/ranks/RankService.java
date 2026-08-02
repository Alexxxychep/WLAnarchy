package me.alexxxychep.wlanarchy.ranks;

import com.google.inject.Inject;
import me.alexxxychep.wlanarchy.database.DatabaseExecutionException;
import me.alexxxychep.wlanarchy.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;

public class RankService {
    private static final Logger log = LoggerFactory.getLogger(RankService.class);
    private final WLPlayerRankDao rankDao;

    @Inject
    public RankService(WLPlayerRankDao rankDao) {
        this.rankDao = rankDao;
    }

    public Rank getRank(UUID uuid) throws ServiceException {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        try {
            return rankDao.getRankFromUUID(uuid);
        } catch(DatabaseExecutionException e) {
            throw getGettingRankException(uuid, e);
        }
    }

    public void setRank(UUID uuid, Rank rank) throws ServiceException {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        Objects.requireNonNull(rank, "Rank cannot be null");
        try {
            rankDao.saveRank(uuid, rank);
        } catch(DatabaseExecutionException e) {
            throw getSettingRankException(uuid, rank, e);
        }
    }

    private ServiceException getSettingRankException(UUID uuid, Rank rank, Throwable cause) {
        ServiceException serviceException = new ServiceException("Error setting rank!", cause);
        serviceException.addContext("uuid", uuid.toString());
        serviceException.addContext("rank", rank.toString());
        return serviceException;
    }


    private ServiceException getGettingRankException(UUID uuid, Throwable cause) {
        ServiceException serviceException = new ServiceException("Error getting rank!", cause);
        serviceException.addContext("uuid", uuid.toString());
        return serviceException;
    }
}
