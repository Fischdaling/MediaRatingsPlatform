package at.technikum.repository.interfaces;

import at.technikum.model.MediaEntry;

import java.util.List;
import java.util.UUID;

public interface IFavoriteRepo {
    boolean exists(UUID userId, UUID mediaId);
    void addNew(UUID userId, UUID mediaId);
    boolean update(UUID userId, UUID mediaId);
    void remove(UUID userId, UUID mediaId);
    List<MediaEntry> findByUserId(UUID userId);

}
