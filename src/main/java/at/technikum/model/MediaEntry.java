package at.technikum.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class MediaEntry extends BaseEntity implements IMediaEntry{
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

    public MediaEntry(UUID creatorId, String title, String description,List<Genre> genres ,Date releaseDate, int ageRestriction, MediaType mediaType) {
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
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
        this.ageRestriction = ageRestriction;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
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
        this.averageScore = (float)ratings.stream().filter(r-> !r.isHidden()).mapToDouble((r)->r.getStars()).average().orElse(0.0);
        return averageScore;
    }

    public int calculateFavoriteCount(){
        //TODO get check all users where this media is a favorite and sum?
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
