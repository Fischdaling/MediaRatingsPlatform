package at.technikum.repository.interfaces;

import at.technikum.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepo {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String Username);
    void update(User user);
    void remove(UUID userId);
}
