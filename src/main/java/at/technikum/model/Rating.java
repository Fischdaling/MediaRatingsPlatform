package at.technikum.model;

import at.technikum.exception.RatingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Rating extends BaseEntity{
    private UUID ownerId;
    private UUID mediaEntryId;
    private int stars;
    private String comment;
    private List<UUID> likes; //UUID TO SEE WHO LIKED
    private boolean isHidden;

    public Rating(UUID ownerId, UUID mediaEntryId,int stars, String comment) {
        this.ownerId = ownerId;
        this.mediaEntryId = mediaEntryId;
        setStars(stars);
        this.comment = comment;
        this.likes = new ArrayList<>();
        this.isHidden = true;
    }

    private void setStars(int stars) {
        if (stars >5 || stars < 1) throw new IllegalArgumentException("Only 1-5 stars possible");
        this.stars = stars;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getMediaEntryId() {
        return mediaEntryId;
    }

    public void setMediaEntryId(UUID mediaEntryId) {
        this.mediaEntryId = mediaEntryId;
    }

    public int getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<UUID> getLikes() {
        return likes;
    }

    public void setLikes(List<UUID> likes) {
        this.likes = likes;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void show() {
        isHidden = false;
    }

    public void hide() {
        isHidden = true;
    }


    public void addLike(UUID userId) {
        if (userId == null) throw new RatingException("UserId Required");
        if (userId.equals(ownerId)) throw new RatingException("You can't like your own Rating");
        if (likes.contains(userId)) throw new RatingException("User already liked");
        this.likes.add(userId);
    }
}
