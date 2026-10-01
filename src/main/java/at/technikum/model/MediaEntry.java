package at.technikum.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static at.technikum.util.valdiation.Validation.*;

public abstract class MediaEntry extends BaseEntity{
    private UUID creatorId; //FK
    private String title;
    private String description;
    private List<Genre> genres;
    private Date releaseDate;
    private int ageRestriction;
    private List<Rating> ratings; // 1-5
    private int favoriteCount;
    private float averageScore;
    private MediaType mediaType;

    // LOAD FROM DB
    public MediaEntry(UUID id, LocalDateTime createAt, LocalDateTime updatedAt, UUID creatorId, String title, String description, List<Genre> genres, Date releaseDate, int ageRestriction, List<Rating> ratings, int favoriteCount, MediaType mediaType, float averageScore) {
        super(id, createAt, updatedAt);
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.ratings = ratings;
        this.favoriteCount = favoriteCount;
        this.mediaType = mediaType;
        this.averageScore = averageScore;
    }

    public MediaEntry(UUID creatorId, String title, String description, List<Genre> genres , Date releaseDate, int ageRestriction, MediaType mediaType) {
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseDate=releaseDate;
        this.ageRestriction = ageRestriction;
        this.ratings = new ArrayList<>();
        this.favoriteCount = 0;
        this.averageScore = 0.0f;
        this.mediaType = mediaType;

    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateString(title, "title");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateString(description, "description");

        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        validateNumberInRange(ageRestriction,0,18,"ageRestriction");
        this.ageRestriction = ageRestriction;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        validatePositiveNumber(favoriteCount, "Favorite count");
        this.favoriteCount = favoriteCount;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
        calculateAverageScore();

    }

    public float getAvarageScore() {
        return averageScore;
    }

    public void setAvarageScore(float avarageScore) {
        validatePositiveNumber(avarageScore, "avarageScore");
        this.averageScore = avarageScore;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
        calculateAverageScore();
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public float calculateAverageScore(){
        this.averageScore = (float)ratings.stream().mapToDouble((r)->r.getStars()).average().orElse(0.0);
        return averageScore;
    }

    public int calculateFavoriteCount(){
        //TODO get check all users where this media is a favorite and sum?
        //TODO USE SQL LATER ON AND PPUT THIS SOMEWHERE ELSE
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaEntry that = (MediaEntry) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "base=" + super.toString() +
                ", creatorId=" + creatorId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                ", releaseDate=" + releaseDate +
                ", ageRestriction=" + ageRestriction +
                ", mediaType=" + mediaType +
                ", ratingCount=" + (ratings != null ? ratings.size() : 0) +
                ", averageScore=" + averageScore +
                ", favoriteCount=" + favoriteCount +
                '}';
    }
}
