package me.alexxxychep.wlanarchy.ranks;

import me.alexxxychep.wlanarchy.Rank;
import me.alexxxychep.wlanarchy.utils.UuidUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WLPlayerRankDao {
    private final DataSource dataSource;

    public WLPlayerRankDao(DataSource dataSource) {
        this.dataSource = dataSource;
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

    public void deleteRank(UUID uuid) {
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


}
