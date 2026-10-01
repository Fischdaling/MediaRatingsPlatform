package at.technikum.repository.sql;

import at.technikum.model.User;
import at.technikum.repository.interfaces.IUserRepo;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements IUserRepo {
    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public User save(User user) {
        return null;
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
}
