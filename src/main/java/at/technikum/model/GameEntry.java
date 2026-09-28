package at.technikum.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GameEntry extends MediaEntry{


    public GameEntry(UUID creatorId, String title, String description, List<Genre> genres, Date releaseDate, int ageRestriction) {
        super(creatorId, title, description, genres, releaseDate, ageRestriction, MediaType.Game);
    }
}
