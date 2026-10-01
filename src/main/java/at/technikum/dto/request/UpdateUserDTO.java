package at.technikum.dto.request;

import at.technikum.model.MediaEntry;

import java.util.Set;

public record UpdateUserDTO(String username, String password, Set<MediaEntry> favorites){}
