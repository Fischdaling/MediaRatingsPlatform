package at.technikum.service;

import at.technikum.service.UserService;
import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.exception.MediaExceptions;
import at.technikum.model.*;
import at.technikum.repository.sql.MediaRepository;

import java.util.UUID;


public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    // ------------------------------------------------Validate--------------------------------------------------//
    private void validate(CreateMediaEntryDTO dto) {
        if (dto.title() == null || dto.title().isBlank())
            throw new MediaExceptions("Title is required");
        if (dto.ageRestriction() < 0 || dto.ageRestriction() > 18)
            throw new MediaExceptions("Invalid age restriction");
        if (dto.mediaType() == null)
            throw new MediaExceptions("Media type is Required");
    }
    private void validateUser(UUID userId){
        // find in REPO and THROW IF NOT THERE
    }
    private void isCreator(UUID userId, MediaEntry media){
        validateUser(userId);
        if (!media.getCreatorId().equals(userId))throw new MediaExceptions("Lacking Permissions");
    }

    //TODO CRUD MEDIA
    // USER can CRUD Media entries
    public MediaEntry getMedia(UUID id) {
        //find in REPO THROW if ID NOT THERRE OR NOT FOUND
        return null;
    }

    public MediaEntry createMediaEntry(UUID creatorId, CreateMediaEntryDTO dto){
        validate(dto);
        validateUser(creatorId);

        MediaEntry entry;
        switch (dto.mediaType()){
            case Game -> entry = new GameEntry(creatorId, dto.title(), dto.description(),dto.genres(), dto.releaseDate(), dto.ageRestriction());
            case Movie -> entry = new MovieEntry(creatorId, dto.title(), dto.description(),dto.genres(), dto.releaseDate(), dto.ageRestriction());
            case Series -> entry = new SeriesEntry(creatorId, dto.title(), dto.description(),dto.genres(), dto.releaseDate(), dto.ageRestriction());
            default -> throw new MediaExceptions("If you see this something is terrible wrong (mediaType is neither Game,Movie,Series or Null)");
        }

        return entry; //TODO WRITE IN DB
    }

    public MediaEntry updateMediaEntry(UUID currentUserId, UUID id, CreateMediaEntryDTO dto){

        MediaEntry entry = getMedia(id);

        isCreator(currentUserId, entry);

        entry = createMediaEntry(currentUserId,dto);

        //TODO WRITE INTO DB

        return entry;
    }

    public MediaEntry deleteMediaEntry(UUID currentUserId, UUID id){
        MediaEntry entry = getMedia(id);
        isCreator(currentUserId, entry);
        // TODO remove out of UsersFavorite list
        //TODO findUserById in Repo

        //delete from repo
        return entry;
    }

}
