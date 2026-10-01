package at.technikum.repository.sql;

import at.technikum.model.Rating;
import at.technikum.repository.interfaces.IRatingRepo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RatingRepository implements IRatingRepo {

    private final DataSource ds;

    public RatingRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Rating save(Rating rating) {
        return null;
    }

    @Override
    public Optional<Rating> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Rating> findByMediaId(UUID mediaId) {
        return List.of();
    }

    @Override
    public void update(Rating rating) {

    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}
