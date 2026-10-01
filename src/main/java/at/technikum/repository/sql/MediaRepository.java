package at.technikum.repository.sql;

import at.technikum.model.MediaEntry;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.util.MediaSeachCriteria;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MediaRepository implements IMediaRepo {

    private final DataSource ds;

    public MediaRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public MediaEntry save(MediaEntry mediaEntry) {
        return null;
    }

    @Override
    public Optional<MediaEntry> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<MediaEntry> findByPartialMatching(MediaSeachCriteria criteria) {
        return List.of();
    }

    @Override
    public List<MediaEntry> findAll() {
        return List.of();
    }

    @Override
    public MediaEntry update(MediaEntry mediaEntry) {
        return null;
    }

    @Override
    public void delete(UUID mediaId) {

    }
}
