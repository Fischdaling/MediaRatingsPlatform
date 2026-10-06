package at.technikum.service;

import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.model.MediaEntry;
import at.technikum.model.Rating;

import java.util.List;
import java.util.UUID;

public interface IRatingService {
    List<Rating> getRatingsFromMedia(UUID mediaId);
    List<Rating> getRatingHistory(UUID userId);
    void like(UUID currentUserId, UUID id);
    void removeLike(UUID currentUserId, UUID id);
    Rating updateRating(UUID currentUserId, UUID id, UpdateRatingDTO dto);
    Rating deleteRating(UUID currentUserId,UUID id);
    void confirmComment(UUID currentUserId, UUID ratingId);
    void deleteComment(UUID currentUserId, UUID ratingId);
}
