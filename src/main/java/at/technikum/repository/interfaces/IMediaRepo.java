package at.technikum.repository.interfaces;

import at.technikum.model.MediaEntry;
import at.technikum.model.MediaType;
import at.technikum.repository.util.MediaSeachCriteria;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMediaRepo {
    public MediaEntry save(MediaEntry mediaEntry);

    public Optional<MediaEntry> findById(UUID id);

    public List<MediaEntry> findByPartialMatching(MediaSeachCriteria criteria);

    public List<MediaEntry> findAll();

    public void delete(UUID mediaId);

}
