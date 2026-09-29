package at.technikum.repository.interfaces;

import at.technikum.model.MediaEntry;

import java.util.List;
import java.util.UUID;

public interface IFavoriteRepo {
    boolean addNew(UUID userId, UUID mediaId);
    boolean remove(UUID userId, UUID mediaId);
    List<MediaEntry> findByUserId(UUID userId);

}
