package at.technikum.controller;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.UpdateMediaDTO;
import at.technikum.repository.util.Response;

import java.util.UUID;

public class MediaController implements IMediaController{
    /*
    GET    /media
    GET    /media/{id}
    POST   /media
    PUT    /media/{id}
    DELETE /media/{id}
    */

    @Override
    public Response getMedia(UUID userId) {
        return null;
    }

    @Override
    public Response createMedia(UUID userId, CreateMediaEntryDTO dto) {
        return null;
    }

    @Override
    public Response getMediaById(UUID userId, UUID mediaId) {
        return null;
    }

    @Override
    public Response updateMedia(UUID userId, UpdateMediaDTO dto) {
        return null;
    }

    @Override
    public Response deleteMedia(UUID userId, UUID mediaId) {
        return null;
    }

    @Override
    public Response createRating(UUID userId, UUID mediaId, CreateMediaEntryDTO dto) {
        return null;
    }
}
