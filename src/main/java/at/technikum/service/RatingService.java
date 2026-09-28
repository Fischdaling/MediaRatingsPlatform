package at.technikum.service;

import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.exception.MediaExceptions;
import at.technikum.model.Rating;
import at.technikum.repository.sql.RatingRepository;


import java.util.UUID;

public class RatingService {

    private RatingRepository RatingRepo;
    //TODO CRUD Services
    // USER can rate media Entries
    // createRating() adds comment

    public RatingService(RatingRepository ratingRepo) {
        RatingRepo = ratingRepo;
    }

    // -------------------------------VAlidation-------------------------------
    private void validate(CreateRatingDTO dto){
        if (dto.mediaId() == null) throw new IllegalArgumentException("message");
        if (dto.stars() >5 || dto.stars() < 1) throw new IllegalArgumentException("Only 1-5 stars possible");

    }

    private void isOwner(UUID userId, Rating rating){
        if (!rating.getOwnerId().equals(userId))throw new MediaExceptions("Lacking Permissions");
    }

    public Rating createRating(UUID currentUserId, CreateRatingDTO dto){
        validate(dto);
        //TODO if !findMediaById(dto.mediaId()) then throw 404 exception
        Rating rating = new Rating(currentUserId, dto.mediaId(),dto.stars(),dto.comment());
        // TODO add to REPO
        return rating;
    }

    public Rating getRating(UUID id) {
        //TODO FIND RATING PER ID
        return null;
    }

    public void like(UUID currentUserId, UUID id){
        Rating rating = getRating(id);
        rating.addLike(currentUserId);
        rating.setUpdatedAtToNow();
    }

    public Rating updateRating(UUID currentUserId,UUID id, CreateRatingDTO dto){
        validate(dto);

        Rating rating = getRating(id);
        isOwner(currentUserId,rating);

        rating = createRating(currentUserId,dto);
        rating.setUpdatedAtToNow();
        //TODO REplace in DB
        return rating;
    }

    public Rating deleteRating(UUID currentUserId,UUID id){
        Rating rating = getRating(id);
        isOwner(currentUserId,rating);

        //TODO REMOVE IN DB
        return rating;
    }


}
