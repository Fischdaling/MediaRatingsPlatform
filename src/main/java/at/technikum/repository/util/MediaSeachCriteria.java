package at.technikum.repository.util;

import at.technikum.model.Genre;
import at.technikum.model.MediaType;

public record MediaSeachCriteria(String titleContains,
                                 Genre genre,
                                 MediaType mediaType,
                                 int releaseYear,
                                 int ageRestriction,
                                 double minimumRating,
                                 MediaSortField sortBy,
                                 boolean ascending) {}
