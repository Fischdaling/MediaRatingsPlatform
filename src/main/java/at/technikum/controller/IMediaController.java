package at.technikum.controller;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.UpdateMediaDTO;
import at.technikum.dto.response.MediaDTO;
import at.technikum.repository.util.Response;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

public interface IMediaController {
    // /media
    Response getMedia(UUID userId);
    Response createMedia(UUID userId, CreateMediaEntryDTO dto);
    Response getMediaById(UUID userId, UUID mediaId);
    Response updateMedia(UUID userId, UpdateMediaDTO dto);
    Response deleteMedia(UUID userId, UUID mediaId);
    // /media/{id}/rateing
    Response createRating(UUID userId, UUID mediaId, CreateMediaEntryDTO dto);


}
