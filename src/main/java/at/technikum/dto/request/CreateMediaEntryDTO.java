package at.technikum.dto.request;

import at.technikum.model.Genre;
import at.technikum.model.MediaType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CreateMediaEntryDTO(String title, String description, List<Genre> genres, LocalDate releaseDate, int ageRestriction, MediaType mediaType){}
