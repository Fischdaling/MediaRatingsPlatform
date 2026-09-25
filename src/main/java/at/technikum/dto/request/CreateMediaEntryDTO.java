package at.technikum.dto.request;

import at.technikum.model.MediaType;

import java.util.Date;
import java.util.List;

public record CreateMediaEntryDTO(String title, String description, List<String> genres, Date releaseDate, int ageRestriction, MediaType mediaType){}
