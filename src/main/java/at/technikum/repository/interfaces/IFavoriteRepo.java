package at.technikum.repository.interfaces;

import at.technikum.model.MediaEntry;

import java.util.List;
import java.util.UUID;

public interface IFavoriteRepo {
    boolean exists(UUID userId, UUID mediaId);
    boolean addNew(UUID userId, UUID mediaId);
    boolean update(UUID userId, UUID mediaId);
    boolean remove(UUID userId, UUID mediaId);
    List<UUID> findByUserId(UUID userId);

}
