package at.technikum.dto.response;

import at.technikum.model.MediaEntry;
import at.technikum.model.Rating;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RatingDTO (
        UUID id,
        LocalDateTime createAt,
        LocalDateTime updatedAt,
        UUID ownerId,
        UUID mediaEntryId,
        List<UUID> likes,
        int stars,
        String comment,
        boolean isHidden) {

    public static RatingDTO from(Rating r) {
        return new RatingDTO(
                r.getId(), r.getCreateAt(), r.getUpdatedAt(),r.getOwnerId(), r.getMediaEntryId(),
                r.getLikes(), r.getStars(), r.getComment(), r.isCommentsVisible());
    }
}
