package at.technikum.model;

import at.technikum.util.exception.RatingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static at.technikum.util.valdiation.Validation.validateNumberInRange;

public class Rating extends BaseEntity{
    private UUID ownerId;
    private UUID mediaEntryId;
    private int stars;
    private String comment;
    private List<UUID> likes; //UUID TO SEE WHO LIKED
    private boolean commentsVisible;

    // LOAD FROM DB
    public Rating(UUID id, LocalDateTime createAt, LocalDateTime updatedAt, UUID ownerId, UUID mediaEntryId, List<UUID> likes, int stars, String comment, boolean isHidden) {
        super(id, createAt, updatedAt);
        this.ownerId = ownerId;
        this.mediaEntryId = mediaEntryId;
        this.likes = likes;
        this.stars = stars;
        this.comment = comment;
        this.commentsVisible = isHidden;
    }

    public Rating(UUID ownerId, UUID mediaEntryId, int stars, String comment) {
        this.ownerId = ownerId;
        this.mediaEntryId = mediaEntryId;
        setStars(stars);
        this.comment = comment;
        this.likes = new ArrayList<>();
        this.commentsVisible = true;
    }

    public void setStars(int stars) {
        validateNumberInRange(stars, 1,5, "Star Rating");
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
    public void deleteComment(){this.comment = null;}

    public List<UUID> getLikes() {
        return likes;
    }

    public void setLikes(List<UUID> likes) {
        this.likes = likes;
    }

    public boolean isCommentsVisible() {
        return commentsVisible;
    }

    public void showComments() {
        commentsVisible = false;
    }

    public void hideComments() {
        commentsVisible = true;
    }


    public void addLike(UUID userId) {
        if (userId == null) throw new RatingException("UserId Required");
        if (userId.equals(ownerId)) throw new RatingException("You can't like your own Rating");
        if (likes.contains(userId)) throw new RatingException("User already liked");
        this.likes.add(userId);
    }

    public void removeLike(UUID userId){
        if (userId == null) throw new RatingException("UserId Required");
        if (userId.equals(ownerId)) throw new RatingException("You can't even like your Rating");
        if (!likes.contains(userId)) throw new RatingException("user hasn't liked the Rating");
        this.likes.remove(userId);

    }

    @Override
    public String toString() {
        return "Rating{" +
                "base=" + super.toString() +
                ", ownerId=" + ownerId +
                ", mediaEntryId=" + mediaEntryId +
                ", stars=" + stars +
                ", comment='" + comment + '\'' +
                ", likeCount=" + (likes != null ? likes.size() : 0) +
                ", isHidden=" + commentsVisible +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating that = (Rating) o;
        return getId() != null && getId().equals(that.getId());
    }

}
