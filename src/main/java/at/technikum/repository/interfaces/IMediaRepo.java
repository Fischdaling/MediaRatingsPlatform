package at.technikum.repository.interfaces;

import at.technikum.model.MediaEntry;
import at.technikum.util.MediaType;

import java.util.List;

public interface IMediaRepo {
    public MediaEntry findById(int id);

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

    public MediaEntry create(MediaEntry media);

    public void update(MediaEntry media);

    public void delete(int mediaId);

}
