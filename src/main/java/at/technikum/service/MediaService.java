package at.technikum.service;

import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.dto.request.UpdateMediaDTO;
import at.technikum.model.*;
import at.technikum.repository.interfaces.IFavoriteRepo;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.interfaces.IRatingRepo;
import at.technikum.repository.interfaces.IUserRepo;
import at.technikum.util.exception.MediaException;
import at.technikum.util.exception.RatingException;
import at.technikum.util.exception.UserException;

import java.util.List;
import java.util.UUID;

import static at.technikum.util.valdiation.Validation.*;


public class MediaService implements IMediaService {
    private final IRatingRepo ratingRepo;
    private final IMediaRepo mediaRepo;
    private final IUserRepo userRepo;
    private final IFavoriteRepo favoriteRepo;

    public MediaService(IRatingRepo ratingRepo, IMediaRepo mediaRepository, IUserRepo userRepo, IFavoriteRepo favoriteRepo) {
        this.ratingRepo = ratingRepo;
        this.mediaRepo = mediaRepository;
        this.userRepo = userRepo;
        this.favoriteRepo = favoriteRepo;
    }

    // ------------------------------------------------Validate--------------------------------------------------//
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

    @Override
    public MediaEntry getMediaEntry(UUID mediaId) {
        return mediaRepo.findById(mediaId).orElseThrow(()-> new MediaException("Media not found"));
    }

    @Override
    public List<MediaEntry> getMediaEntries() {
        return mediaRepo.findAll();
    }

    public MediaEntry createMediaEntry(UUID creatorId, CreateMediaEntryDTO dto){
        validateString(dto.title(),"Title");
        validateNumberInRange(dto.ageRestriction(),0,18,"ageRestriction");
        if (dto.mediaType() == null)
            throw new MediaException("Media type is Required");
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

    public MediaEntry updateMediaEntry(UUID currentUserId, UUID mediaId,UpdateMediaDTO dto){
        notNull(mediaId, "Media Entry Id");
        MediaEntry entry = mediaRepo.findById(mediaId).orElseThrow(()-> new MediaException("Media not found"));
        isCreator(currentUserId, entry);
        validateString(dto.title(),"Title");
        validateNumberInRange(dto.ageRestriction(),0,18,"ageRestriction");
        if (dto.mediaType() == null)
            throw new MediaException("Media type is Required");

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

    public Rating createRating(UUID currentUserId, UUID mediaId,CreateRatingDTO dto){
        notNull(currentUserId, "currentUserId");
        notNull(mediaId, "Media Entry Id");
        validateString(dto.comment(), "Comment");
        validateNumberInRange(dto.stars(),1,5, "Stars");

        MediaEntry mediaEntry = mediaRepo.findById(mediaId).orElseThrow(()->new MediaException("MediaId not found"));
        List<Rating> ratingsFromUser = ratingRepo.findByMediaId(mediaEntry.getId());
        if(ratingsFromUser.stream().anyMatch(r -> r.getOwnerId().equals(currentUserId)))
            throw new RatingException("current User already has a Rating about this Media");
        Rating rating = new Rating(currentUserId, mediaId,dto.stars(),dto.comment());
        ratingRepo.save(rating);
        return rating;
    }

}
