package at.technikum.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class MediaEntry extends BaseEntity implements IMediaEntry{
    private UUID creatorId; //FK
    private String title;
    private String description;
    List<String> genres;
    private Date releaseDate;
    private int ageRestriction;
    private List<Rating> ratings; // 1-5
    private int favoriteCount;
    private float avarageScore;
    private MediaType mediaType;

    public MediaEntry(UUID creatorId, String title, String description,List<String> genres ,Date releaseDate, int ageRestriction, MediaType mediaType) {
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseDate=releaseDate;
        this.ageRestriction = ageRestriction;
        this.ratings = new ArrayList<>();
        this.favoriteCount = 0;
        this.avarageScore = 0.0f;
        this.mediaType = mediaType;

    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    public UUID getCreatorId() {
        return creatorId;
    }

}
