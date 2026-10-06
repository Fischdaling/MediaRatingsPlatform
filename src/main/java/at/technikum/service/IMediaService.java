package at.technikum.service;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateMediaDTO;
import at.technikum.model.MediaEntry;
import at.technikum.model.Rating;

import java.util.List;
import java.util.UUID;

public interface IMediaService {
    MediaEntry getMediaEntry(UUID mediaId);
    List<MediaEntry> getMediaEntries();
    MediaEntry createMediaEntry(UUID creatorId, CreateMediaEntryDTO dto);
    MediaEntry updateMediaEntry(UUID currentUserId, UUID mediaId,UpdateMediaDTO dto);
    void deleteMediaEntry(UUID currentUserId, UUID id);
    Rating createRating(UUID currentUserId,UUID mediaId,CreateRatingDTO dto);
}
