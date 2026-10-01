package at.technikum.service;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.exception.MediaException;
import at.technikum.exception.UserException;
import at.technikum.model.*;
import at.technikum.repository.interfaces.IFavoriteRepo;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.interfaces.IUserRepo;
import at.technikum.repository.sql.MediaRepository;

import java.util.UUID;

import static at.technikum.valdiation.Validation.*;


public class MediaService implements IMediaService {
    private final IMediaRepo mediaRepo;
    private final IUserRepo userRepo;
    private final IFavoriteRepo favoriteRepo;

    public MediaService(IMediaRepo mediaRepository, IUserRepo userRepo, IFavoriteRepo favoriteRepo) {
        this.mediaRepo = mediaRepository;
        this.userRepo = userRepo;
        this.favoriteRepo = favoriteRepo;
    }

    // ------------------------------------------------Validate--------------------------------------------------//
    private void validate(CreateMediaEntryDTO dto) {
        validateString(dto.title(),"Title");
        validateNumberInRange(dto.ageRestriction(),0,18,"ageRestriction");
        if (dto.mediaType() == null)
            throw new MediaException("Media type is Required");
    }
    private void validateUser(UUID userId){
        notNull(userId, "UserId");
        userRepo.findById(userId).orElseThrow(()-> new UserException("User not Found"));
    }
    private void isCreator(UUID userId, MediaEntry media){
        validateUser(userId);
        if (!media.getCreatorId().equals(userId))throw new MediaException("Lacking Permissions");
    }

    //TODO CRUD MEDIA
    // USER can CRUD Media entries

    public MediaEntry createMediaEntry(UUID creatorId, CreateMediaEntryDTO dto){
        validate(dto);
        validateUser(creatorId);

        MediaEntry entry;
        switch (dto.mediaType()){
            case Game -> entry = new GameEntry(creatorId, dto.title(), dto.description(),dto.genres(), dto.releaseDate(), dto.ageRestriction());
            case Movie -> entry = new MovieEntry(creatorId, dto.title(), dto.description(),dto.genres(), dto.releaseDate(), dto.ageRestriction());
            case Series -> entry = new SeriesEntry(creatorId, dto.title(), dto.description(),dto.genres(), dto.releaseDate(), dto.ageRestriction());
            default -> throw new MediaException("If you see this something is terrible wrong (mediaType is neither Game,Movie,Series or Null)");
        }

        mediaRepo.save(entry);
        return entry;
    }

    public MediaEntry updateMediaEntry(UUID currentUserId, UUID id, CreateMediaEntryDTO dto){
        notNull(id, "Media Entry Id");
        MediaEntry entry = mediaRepo.findById(id).orElseThrow(()-> new MediaException("Media not found"));
        isCreator(currentUserId, entry);
        validate(dto);

        entry.setTitle(dto.title());
        entry.setDescription(dto.description());
        entry.setGenres(dto.genres());
        entry.setReleaseDate(dto.releaseDate());
        entry.setAgeRestriction(dto.ageRestriction());

        entry.setUpdatedAtToNow();
        mediaRepo.update(entry);

        return entry;
    }

    public void deleteMediaEntry(UUID currentUserId, UUID id){
        notNull(id, "Media Entry Id");

        MediaEntry entry = mediaRepo.findById(id).orElseThrow(()-> new MediaException("Media not found"));
        isCreator(currentUserId, entry);

        favoriteRepo.remove(currentUserId, id);
        //delete from repo
        mediaRepo.delete(entry.getId());
    }

}
