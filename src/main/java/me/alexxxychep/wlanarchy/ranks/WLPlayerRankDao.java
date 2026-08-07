package me.alexxxychep.wlanarchy.ranks;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.alexxxychep.wlanarchy.database.DatabaseExecutionException;
import me.alexxxychep.wlanarchy.database.DatabaseService;
import me.alexxxychep.wlanarchy.utils.UuidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Singleton
public class WLPlayerRankDao {
    private final DatabaseService databaseService;

    @Inject
    public WLPlayerRankDao(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Rank getRankFromUUID(UUID uuid) throws DatabaseExecutionException {
        String query = "SELECT rankname FROM ranks WHERE uuid = ?";
        try (
                Connection connection = databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            byte[] uuidBytes = UuidUtils.convertToBytes(uuid);
            preparedStatement.setBytes(1, uuidBytes);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String rankName = resultSet.getString("rankname");
                    if (rankName != null) {
                        return Rank.getRankByName(rankName);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseExecutionException("Error fetching rank from rank table! ", e, query);
        }
        return Rank.PLAYER;
    }

    public void saveRank(UUID uuid, Rank rank) throws DatabaseExecutionException {
        if (rank.equals(Rank.PLAYER)) {
            deleteRank(uuid);
            return;
        }

        String query = """
                INSERT INTO ranks (uuid, rankname)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE rankname = VALUES(rankname);""";

        try (
                Connection connection = databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setBytes(1, UuidUtils.convertToBytes(uuid));
            preparedStatement.setString(2, rank.toString().toLowerCase());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseExecutionException("Error saving rank to rank table! ", e, query);
        }
    }

    //js use saveRank(uuid), this method is pretty dangerous
    private void deleteRank(UUID uuid) throws DatabaseExecutionException {
        String query = "DELETE FROM ranks WHERE uuid = ?";
        try (
                Connection connection = databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setBytes(1, UuidUtils.convertToBytes(uuid));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseExecutionException("Error deleting rank from rank table! ", e, query);
        }
    }
}
