package at.technikum.dto.response;

import at.technikum.model.Genre;

import java.util.UUID;

public record UserStatistic(int totalRatings,
                            double averageRating,
                            int favoritesCount) {}
