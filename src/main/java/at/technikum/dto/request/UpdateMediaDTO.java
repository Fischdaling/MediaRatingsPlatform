package at.technikum.dto.request;

import at.technikum.model.Genre;
import at.technikum.model.MediaType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record UpdateMediaDTO(UUID mediaId, String title, String description, List<Genre> genres, Date releaseDate, int ageRestriction, MediaType mediaType) {
}
