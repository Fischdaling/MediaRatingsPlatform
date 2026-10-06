package at.technikum.dto.request;

import java.util.Optional;
import java.util.UUID;

public record CreateRatingDTO(int stars , String comment) {}
