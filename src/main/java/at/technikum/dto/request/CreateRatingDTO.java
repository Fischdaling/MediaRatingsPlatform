package at.technikum.dto.request;

import java.util.Optional;
import java.util.UUID;

public record CreateRatingDTO(UUID mediaId,int stars , String comment) {}
