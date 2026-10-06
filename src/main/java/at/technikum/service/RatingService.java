package at.technikum.service;

import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.util.exception.MediaException;
import at.technikum.util.exception.RatingException;
import at.technikum.model.MediaEntry;
import at.technikum.model.Rating;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.interfaces.IRatingRepo;
import at.technikum.repository.interfaces.IUserRepo;


import java.util.List;
import java.util.UUID;

import static at.technikum.util.valdiation.Validation.*;

public class RatingService implements IRatingService {

    private final IRatingRepo ratingRepo;
    private final IMediaRepo mediaRepo;
    private final IUserRepo userRepo;
    //TODO CRUD Services
    // USER can rate media Entries
    // createRating() adds comment

    public RatingService(IRatingRepo ratingRepo, IMediaRepo mediaRepo, IUserRepo userRepo) {
        this.ratingRepo = ratingRepo;
        this.mediaRepo = mediaRepo;
        this.userRepo = userRepo;
    }


    // -------------------------------VAlidation-------------------------------

    private void validate(UpdateRatingDTO dto){
        validateString(dto.comment(), "Comment");
        validateNumberInRange(dto.stars(),1,5, "Stars");
    }

    private void isOwner(UUID userId, Rating rating){
        allNotNull(userId,rating);
        if (!rating.getOwnerId().equals(userId))throw new RatingException("Lacking Permissions");
    }



    public List<Rating> getRatingsFromMedia(UUID mediaId) {
        notNull(mediaId, "Media Id");
        MediaEntry media = mediaRepo.findById(mediaId).orElseThrow(()->new MediaException("Media not found"));
        return media.getRatings();
    }


    public List<Rating> getRatingHistory(UUID userId) {
        notNull(userId, "user Id");
        userRepo.findById(userId).orElseThrow(()->new MediaException("user not found"));

        return ratingRepo.findAll().stream().filter(r-> r.getOwnerId().equals(userId)).toList();
    }


    public void like(UUID currentUserId, UUID id){
        notNull(id,"rating Id");
        notNull(currentUserId, "currentUserId");

        Rating rating = ratingRepo.findById(id).orElseThrow(()-> new RatingException("Rating Id not found"));
        if (rating.getLikes().contains(currentUserId)) throw new RatingException("User already Liked");
        rating.addLike(currentUserId);
        ratingRepo.update(rating);
    }

    public void removeLike(UUID currentUserId, UUID id){
        notNull(id,"rating Id");
        notNull(currentUserId, "currentUserId");

        Rating rating = ratingRepo.findById(id).orElseThrow(()-> new RatingException("Rating Id not found"));
        if (!rating.getLikes().contains(currentUserId)) throw new RatingException("User hasn't liked yet");
        rating.removeLike(currentUserId);
        ratingRepo.update(rating);
    }


    public Rating updateRating(UUID currentUserId,UUID id, UpdateRatingDTO dto){
        notNull(id, "Rating Id");
        notNull(currentUserId, "currentUserId");

        validate(dto);
        Rating rating = ratingRepo.findById(id).orElseThrow(()-> new RatingException("Rating not Found"));
        isOwner(currentUserId,rating);

        rating.setComment(dto.comment());
        rating.setStars(dto.stars());

        rating.setUpdatedAtToNow();
        ratingRepo.update(rating);
        return rating;
    }

    public Rating deleteRating(UUID currentUserId,UUID id){
        notNull(id, "Rating Id");
        notNull(currentUserId, "currentUserId");

        Rating rating = ratingRepo.findById(id).orElseThrow(()->new RatingException("Rating Not Found"));
        isOwner(currentUserId,rating);
        ratingRepo.delete(rating.getId());
        return rating;
    }

    public void confirmComment(UUID currentUserId, UUID ratingId) {
        notNull(ratingId, "Rating Id");
        notNull(currentUserId, "currentUserId");

        Rating rating = ratingRepo.findById(ratingId).orElseThrow(()->new RatingException("Rating Not Found"));
        isOwner(currentUserId,rating);

        rating.showComments();
        ratingRepo.update(rating);
    }

    public void deleteComment(UUID currentUserId, UUID ratingId) {
        notNull(ratingId, "Rating Id");
        notNull(currentUserId, "currentUserId");

        Rating rating = ratingRepo.findById(ratingId).orElseThrow(()->new RatingException("Rating Not Found"));
        isOwner(currentUserId,rating);

        ratingRepo.delete(rating.getId());
    }


}
