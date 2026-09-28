package at.technikum.dto.response;

import java.util.UUID;

public record LeaderboardEntry(int rank,
                               UUID userId,
                               String username,
                               int ratingCount) {}
