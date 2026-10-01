package at.technikum.repository.sql;

import at.technikum.model.MediaEntry;
import at.technikum.repository.interfaces.IFavoriteRepo;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

public class FavoriteRepository implements IFavoriteRepo {

    private final DataSource ds;

    public FavoriteRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean exists(UUID userId, UUID mediaId) {
        return false;
    }

    @Override
    public void addNew(UUID userId, UUID mediaId) {
    }

    @Override
    public boolean update(UUID userId, UUID mediaId) {
        return false;
    }

    @Override
    public void remove(UUID userId, UUID mediaId) {
    }

    @Override
    public List<MediaEntry> findByUserId(UUID userId) {
        return List.of();
    }
}
