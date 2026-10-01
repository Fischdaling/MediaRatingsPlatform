package at.technikum.service;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.model.MediaEntry;

import java.util.UUID;

public interface IMediaService {

    MediaEntry createMediaEntry(UUID creatorId, CreateMediaEntryDTO dto);
    MediaEntry updateMediaEntry(UUID currentUserId, UUID id, CreateMediaEntryDTO dto);
    void deleteMediaEntry(UUID currentUserId, UUID id);
}
