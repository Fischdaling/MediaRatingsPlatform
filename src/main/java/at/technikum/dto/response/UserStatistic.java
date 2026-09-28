package at.technikum.dto.response;

import at.technikum.model.Genre;

import java.util.UUID;

public record UserStatistic(UUID userId,
                            int totalRatings,
                            double averageRating,
                            Genre favoriteGenre,
                            int favoritesCount) {}
