package at.technikum.controller;

import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.dto.response.RatingDTO;
import at.technikum.service.IRatingService;
import at.technikum.util.Response;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.UUID;

public class RatingController extends BaseController implements IRatingController{
    // /api/ratings/{id}
    private final IRatingService service;

    public RatingController(IRatingService service) {
        this.service = service;
    }

    @Override
    public Response updateRating(UUID userId, UUID ratingId, UpdateRatingDTO dto) {

        return Response.ok(RatingDTO.from(service.updateRating(userId, ratingId, dto)));
    }

    @Override
    public Response deleteRating(UUID userId, UUID ratingId) {
        service.deleteRating(userId, ratingId);
        return Response.noContent();
    }

    @Override
    public Response confirmRating(UUID userId, UUID ratingId) {
        service.confirmComment(userId, ratingId);
        return Response.noContent();
    }

    @Override
    public Response likeRating(UUID userId, UUID ratingId) {
        service.like(userId, ratingId);
        return Response.noContent();
    }

    @Override
    public Response unlikeRating(UUID userId, UUID ratingId) {
        service.removeLike(userId, ratingId);
        return Response.noContent();
    }
    @Override
    protected Response handleRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        UUID userId = getUserId(exchange);

        // /api/ratings/{id}
        if (pathParts.length == 4){
            UUID ratingId = UUID.fromString(pathParts[3]);

            if (method.equals("PUT")) return updateRating(userId,ratingId, ow.readValue(exchange.getRequestBody(), UpdateRatingDTO.class));
            if (method.equals("DELETE")) return deleteRating(userId,ratingId);
            return Response.methodNotAllowed("Allow: PUT, DELETE");

        }

        // /api/ratings/{id}/confirm
        if (pathParts.length == 5 && pathParts[4].equals("confirm")){
            UUID ratingId = UUID.fromString(pathParts[3]);
            if (method.equals("POST")) return confirmRating(userId,ratingId);
            return Response.methodNotAllowed("Allow: POST");

        }
        // /api/ratings/{id}/like
        if (pathParts.length == 5 && pathParts[4].equals("like")){
            UUID ratingId = UUID.fromString(pathParts[3]);
            if (method.equals("POST")) return likeRating(userId,ratingId);
            if (method.equals("DELETE")) return unlikeRating(userId,ratingId);
            return Response.methodNotAllowed("Allow: POST");

        }
        return Response.notFound();
    }




  /*
    GET    /media/{id}/ratings
    POST   /media/{id}/ratings
    PUT    /ratings/{id}
    DELETE /ratings/{id}

    DO I DO LIKE HERE?
    POST   /ratings/{id}/like
    DELETE /ratings/{id}/like
    */
}
