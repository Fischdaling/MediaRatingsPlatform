package at.technikum.controller;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateMediaDTO;
import at.technikum.dto.response.MediaEntryDTO;
import at.technikum.model.MediaEntry;
import at.technikum.util.Response;
import at.technikum.service.MediaService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MediaController extends BaseController implements IMediaController{
    /*
    GET    /media
    GET    /media/{id}
    POST   /media
    PUT    /media/{id}
    DELETE /media/{id}
    */
    private final MediaService service;

    public MediaController(MediaService service) {
        this.service = service;
    }

    @Override
    public Response getMedia() {
        return Response.ok(service.getMediaEntries().stream().map(mediaEntry -> MediaEntryDTO.from(mediaEntry)).toList());

    }

    @Override
    public Response createMedia(UUID userId, CreateMediaEntryDTO dto) {
        return Response.created(MediaEntryDTO.from(service.createMediaEntry(userId, dto)));
    }

    @Override
    public Response getMediaById(UUID mediaId) {
        MediaEntry entry = service.getMediaEntry(mediaId);
        if (entry == null) return new Response(404,null);

        return Response.ok(MediaEntryDTO.from(entry));
    }

    @Override
    public Response updateMedia(UUID userId, UUID mediaId ,UpdateMediaDTO dto) {

        return Response.ok(MediaEntryDTO.from(service.updateMediaEntry(userId,mediaId,dto)));
    }

    @Override
    public Response deleteMedia(UUID userId, UUID mediaId) {
        service.deleteMediaEntry(userId, mediaId);
        return Response.noContent();
    }

    @Override
    public Response createRating(UUID userId,UUID mediaId , CreateRatingDTO dto) {
        return Response.created(service.createRating(userId, mediaId, dto));
    }


    @Override
    protected Response handleRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        UUID userId = getUserId(exchange);

        // /api/media/
        if (pathParts.length == 3){
            if (method.equals("GET")) return getMedia();
            if (method.equals("POST"))return createMedia(userId, ow.readValue(exchange.getRequestBody(), CreateMediaEntryDTO.class));
            Response.methodNotAllowed("Allow: GET, POST");

        }

        // /api/media/{id}
        if (pathParts.length == 4){
            UUID mediaId = UUID.fromString(pathParts[3]);

            if (method.equals("GET")) return getMediaById(mediaId);

            if (method.equals("PUT")) return updateMedia(userId,mediaId,ow.readValue(exchange.getRequestBody(), UpdateMediaDTO.class));

            if (method.equals("DELETE")) return deleteMedia(userId, mediaId);
            Response.methodNotAllowed("Allow: GET, PUT, DELETE");

        }

        // /api/media/{id}/ratings
        if (pathParts.length == 5 && pathParts[4].equals("ratings")){
            UUID mediaId = UUID.fromString(pathParts[3]);
            if (method.equals("POST"))return createRating(userId, mediaId,ow.readValue(exchange.getRequestBody(), CreateRatingDTO.class));
            Response.methodNotAllowed("Allow: POST");
        }
        return Response.notFound();
    }

}
