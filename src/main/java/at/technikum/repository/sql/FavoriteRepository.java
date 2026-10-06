package at.technikum.repository.sql;

import at.technikum.model.MediaEntry;
import at.technikum.repository.interfaces.IFavoriteRepo;
import at.technikum.util.exception.UserException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FavoriteRepository implements IFavoriteRepo {

    private final DataSource ds;

    public FavoriteRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean exists(UUID userId, UUID mediaId) {
        String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND media_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, userId);
            ps.setObject(2, mediaId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new UserException("Could not check favorite");
        }
    }

    @Override
    public boolean addNew(UUID userId, UUID mediaId) {
        String sql = "INSERT INTO favorites (user_id, media_id) VALUES (?, ?) "
                + "ON CONFLICT DO NOTHING";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, userId);
            ps.setObject(2, mediaId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new UserException("Could not add favorite");
        }
    }

    @Override
    public boolean update(UUID userId, UUID mediaId) {
        return false;
    }

    @Override
    public boolean remove(UUID userId, UUID mediaId) {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND media_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, userId);
            ps.setObject(2, mediaId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new UserException("Could not remove favorite");
        }
    }

    @Override
    public List<UUID> findByUserId(UUID userId) {
        String sql = "SELECT media_id FROM favorites WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<UUID> ids = new ArrayList<>();
                while (rs.next()) {
                    ids.add(rs.getObject("media_id", UUID.class));
                }
                return ids;
            }
        } catch (SQLException e) {
            throw new UserException("Could not load favorites");
        }
    }
}
