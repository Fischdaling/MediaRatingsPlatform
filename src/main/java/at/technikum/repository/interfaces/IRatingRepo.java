package at.technikum.repository.interfaces;

import at.technikum.model.Rating;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRatingRepo {
    /*
     Search, Filtering & Sorting
• partial title matching
• filtering by genre, media type, release year, age restriction, and minimum rating
• sorting by title, release year, and average score
• implementation in SQL, application logic, or both
     */

    Rating save(Rating rating);
    Optional<Rating> findById(UUID id);
    List<Rating> findByMediaId(UUID mediaId);
    void update(Rating rating);
    boolean delete(UUID id);
}
