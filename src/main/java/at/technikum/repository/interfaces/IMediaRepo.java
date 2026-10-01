package at.technikum.repository.interfaces;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.model.MediaEntry;
import at.technikum.model.MediaType;
import at.technikum.repository.util.MediaSeachCriteria;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMediaRepo {
     MediaEntry save(MediaEntry mediaEntry);

     Optional<MediaEntry> findById(UUID id);

     List<MediaEntry> findByPartialMatching(MediaSeachCriteria criteria);

     List<MediaEntry> findAll();

     MediaEntry update(MediaEntry mediaEntry);

     void delete(UUID mediaId);

}
