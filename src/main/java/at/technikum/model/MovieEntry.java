package at.technikum.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MovieEntry extends MediaEntry{


    public MovieEntry(UUID creatorId, String title, String description, List<Genre> genres, LocalDate releaseDate, int ageRestriction) {
        super(creatorId, title, description, genres, releaseDate, ageRestriction, MediaType.Movie);
    }
}
