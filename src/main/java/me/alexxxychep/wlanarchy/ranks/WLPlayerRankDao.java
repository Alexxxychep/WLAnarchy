package me.alexxxychep.wlanarchy.ranks;

import me.alexxxychep.wlanarchy.Rank;
import me.alexxxychep.wlanarchy.utils.UuidUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WLPlayerRankDao {
    private final DataSource dataSource;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public WLPlayerRankDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CompletableFuture<Rank> getRankFromUUIDAsync(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> getRankFromUUID(uuid), executorService);
    }

    public CompletableFuture<Void> saveRankAsync(UUID uuid, Rank rank) {
        return CompletableFuture.runAsync(() -> saveRank(uuid, rank), executorService);
    }

    public Rank getRankFromUUID(UUID uuid) {
        String query = "SELECT rankname FROM ranks WHERE uuid = ?";
        try (
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            byte[] uuidBytes = UuidUtils.convertToBytes(uuid);
            preparedStatement.setBytes(1, uuidBytes);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String rankName = resultSet.getString("rankname");
                    if(rankName != null) {
                        return Rank.getRankByName(rankName);
                    }
                    return Rank.PLAYER;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Rank.PLAYER;
    }

    public void saveRank(UUID uuid, Rank rank) {
        if(rank.equals(Rank.PLAYER)) {
            deleteRank(uuid);
            return;
        }

        String query = """
                INSERT INTO ranks (uuid, rankname)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE rankname = VALUES(rankname);""";

        try (
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setBytes(1, UuidUtils.convertToBytes(uuid));
            preparedStatement.setString(2, rank.toString().toLowerCase());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //js use saveRank(uuid), this method is pretty dangerous
    private void deleteRank(UUID uuid) {
        String query = "DELETE FROM ranks WHERE uuid = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setBytes(1, UuidUtils.convertToBytes(uuid));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
