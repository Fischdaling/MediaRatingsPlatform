package at.technikum.controller;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateMediaDTO;
import at.technikum.util.Response;

import java.util.UUID;

public interface IMediaController {
    // /api/media
    Response getMedia();
    Response createMedia(UUID userId, CreateMediaEntryDTO dto);
    Response getMediaById(UUID mediaId);
    Response updateMedia(UUID userId,UUID mediaId ,UpdateMediaDTO dto);
    Response deleteMedia(UUID userId, UUID mediaId);
    // /api/media/{id}/rateing
    Response createRating(UUID userId, UUID mediaId, CreateRatingDTO dto);


}
