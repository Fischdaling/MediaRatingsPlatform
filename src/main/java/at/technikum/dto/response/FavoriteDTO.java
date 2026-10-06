package at.technikum.dto.response;

import at.technikum.model.MediaEntry;

import java.util.Set;

public record FavoriteDTO(Set<MediaEntry> favorites) {
}
