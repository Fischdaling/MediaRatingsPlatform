package at.technikum.repository.sql;

import at.technikum.model.User;
import at.technikum.repository.interfaces.IUserRepo;
import at.technikum.util.exception.RepositoryExeption;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements IUserRepo {
    private static final String SELECT_USER =
            "SELECT id, username, password_hash, created_at, updated_at FROM users";

    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (id, username, password_hash, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPasswordHashed());
            ps.setObject(4, user.getCreateAt());
            ps.setObject(5, user.getUpdatedAt());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RepositoryExeption("Could not save user"+ e.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER + " ORDER BY username");
             ResultSet rs = ps.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RepositoryExeption("Could not load users"+ e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String Username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(UUID userId) {

    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getObject("id", UUID.class),
                rs.getObject("created_at", LocalDateTime.class),
                rs.getObject("updated_at", LocalDateTime.class),
                rs.getString("username"),
                rs.getString("password_hash"));
    }
}
