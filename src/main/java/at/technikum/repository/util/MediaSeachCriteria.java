package at.technikum.repository.util;

import at.technikum.model.Genre;
import at.technikum.model.MediaType;

//Richtype values for Nullability
public record MediaSeachCriteria(String titleContains,
                                 Genre genre,
                                 MediaType mediaType,
                                 Integer releaseYear,
                                 Integer ageRestriction,
                                 Double minimumRating,
                                 MediaSortField sortBy,
                                 boolean ascending) {}
