package at.technikum.dto.response;

import at.technikum.model.Genre;
import at.technikum.model.MediaEntry;
import at.technikum.model.MediaType;
import at.technikum.model.Rating;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record MediaEntryDTO(
        UUID id,
        LocalDateTime createAt,
        LocalDateTime updatedAt,
        UUID creatorId,
        String title,
        String description,
        List<Genre> genres,
        Date releaseDate,
        int ageRestriction,
        List<Rating> ratings,
        int favoriteCount,
        MediaType mediaType,
        float averageScore

){
    public static MediaEntryDTO from(MediaEntry m) {
        return new MediaEntryDTO(
                m.getId(),m.getCreateAt(),m.getUpdatedAt(), m.getCreatorId(), m.getTitle(), m.getDescription(),
                m.getGenres(), m.getReleaseDate(), m.getAgeRestriction(), m.getRatings(),
                m.getFavoriteCount(), m.getMediaType(),m.getAvarageScore());
    }
}
