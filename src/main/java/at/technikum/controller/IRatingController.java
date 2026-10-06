package at.technikum.controller;

import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.util.Response;

import java.util.UUID;

public interface IRatingController {
    // /ratings/{Id}
    Response updateRating(UUID userId, UUID ratingId, UpdateRatingDTO dto);
    // /ratings/{Id}
    Response deleteRating(UUID userId, UUID ratingId);
    // /ratings/{Id}/confirm
    Response confirmRating(UUID userId, UUID ratingId);
    // /ratings/{Id}/like
    Response likeRating(UUID userId, UUID ratingId);

    Response unlikeRating(UUID userId, UUID ratingId);
}
