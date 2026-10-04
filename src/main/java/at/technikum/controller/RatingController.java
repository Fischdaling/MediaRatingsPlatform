package at.technikum.controller;

import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.repository.util.Response;

import java.util.UUID;

public class RatingController implements IRatingController{
    @Override
    public Response updateRating(UUID userId, UUID ratingId, UpdateRatingDTO dto) {
        return null;
    }

    @Override
    public Response deleteRating(UUID userId, UUID ratingId) {
        return null;
    }

    @Override
    public Response confirmRating(UUID userId, UUID ratingId) {
        return null;
    }

    @Override
    public Response likeRating(UUID userId, UUID ratingId) {
        return null;
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
