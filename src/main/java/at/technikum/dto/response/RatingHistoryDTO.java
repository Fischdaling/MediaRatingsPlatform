package at.technikum.dto.response;

import at.technikum.model.Rating;

import java.util.List;

public record RatingHistoryDTO(List<Rating> ratings) {
}
