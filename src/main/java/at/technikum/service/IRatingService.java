package at.technikum.service;

import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.model.MediaEntry;
import at.technikum.model.Rating;

import java.util.UUID;

public interface IRatingService {
    void createRating(UUID currentUserId, CreateRatingDTO dto);
    void like(UUID currentUserId, UUID id);
    void removeLike(UUID currentUserId, UUID id);
    Rating updateRating(UUID currentUserId, UUID id, UpdateRatingDTO dto);
    void deleteRating(UUID currentUserId,UUID id);
}
