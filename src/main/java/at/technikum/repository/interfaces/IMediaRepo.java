package at.technikum.repository.interfaces;

import at.technikum.model.MediaEntry;
import at.technikum.model.MediaType;

import java.util.Date;
import java.util.List;

public interface IMediaRepo {
    public MediaEntry findById(int id);

    public MediaEntry findByPartialMatching();

    public List<MediaEntry> findAll();

    public List<MediaEntry> search(
            String title,
            String genre,
            MediaType type,
            Integer releaseYear,
            Integer ageRestriction,
            Double minimumRating,
            String sortBy
    );

    public List<MediaEntry> filterByGenre(String genre);
    public List<MediaEntry> mediaType(MediaType mediaType);
    public List<MediaEntry> releaseYear(Date releaseYear);
    public List<MediaEntry> ageRestriction(int ageRestriction);
    public List<MediaEntry> minimumRating(int starRating);

    //TODO SORTING

    public MediaEntry create(MediaEntry media);

    public void update(MediaEntry media);

    public void delete(int mediaId);

}
