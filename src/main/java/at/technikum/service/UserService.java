package at.technikum.service;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.dto.request.UpdateUserDTO;
import at.technikum.dto.response.LeaderboardEntry;
import at.technikum.dto.response.UserStatistic;
import at.technikum.model.Genre;
import at.technikum.model.Rating;
import at.technikum.repository.interfaces.IRatingRepo;
import at.technikum.security.Authentication;
import at.technikum.util.exception.FavoriteException;
import at.technikum.util.exception.MediaException;
import at.technikum.util.exception.UserException;
import at.technikum.model.MediaEntry;
import at.technikum.model.User;
import at.technikum.repository.interfaces.IFavoriteRepo;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.interfaces.IUserRepo;

import java.security.InvalidParameterException;
import java.util.*;

import static at.technikum.security.PasswordVerifier.VerifyHash;
import static at.technikum.util.valdiation.Validation.*;

public class UserService implements IUserService{

    private final IUserRepo userRepo;
    private final IMediaRepo mediaRepo;
    private final IFavoriteRepo favoriteRepo;
    private final IRatingRepo ratingRepo;

    public UserService(IUserRepo userRepo, IMediaRepo mediaRepo, IFavoriteRepo favoriteRepo, IRatingRepo ratingRepo) {
        this.userRepo = userRepo;
        this.mediaRepo = mediaRepo;
        this.favoriteRepo = favoriteRepo;
        this.ratingRepo = ratingRepo;
    }

    public User getProfile(UUID userId) {
        notNull(userId,"UserId");

        return userRepo.findById(userId).orElseThrow(()-> new UserException("User not found"));
    }




    public User updateProfile(UUID currentUserId, UUID userId, UpdateUserDTO dto) {
        notNull(userId, "user Id");
        notNull(currentUserId, "currentUserId");
        if (!currentUserId.equals(userId)) throw new UserException("Insufficient Permissions");
        User user = userRepo.findById(userId).orElseThrow(()-> new UserException("User not found"));
        validateString(dto.username(), "Username");
        validatePassword(dto.password(), "Password");

        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFavorites(dto.favorites());

        userRepo.update(user);
        return user;
    }

    @Override
    public void deleteUser(UUID currentUserId, UUID userId) {
        notNull(userId, "user Id");
        notNull(currentUserId, "currentUserId");
        if (!currentUserId.equals(userId)) throw new UserException("Insufficient Permissions");
        userRepo.remove(userRepo.findById(userId).orElseThrow(()-> new UserException("User not found")).getId());
    }

    public User register(CreateUserDTO dto){
        validateString(dto.username(), "Username");

        if(userRepo.findByUsername(dto.username()).isPresent()) throw new UserException("Username is already taken");

        validatePassword(dto.password(),"Password");
        User user =new User(dto.username(),dto.password());
        userRepo.save(user);
        return user;
    }

    public String login(LoginDto dto){

        validateString(dto.username(), "Username");

        User user = userRepo.findByUsername(dto.username()).orElseThrow(()-> new UserException("Username or Password wrong"));

        if(!VerifyHash(dto.password(),user.getPasswordHashed().toCharArray()).verified)
            throw new InvalidParameterException("Username or Password wrong");

        return Authentication.generate(user);
    }

    public Set<MediaEntry> getFavorites(UUID userId){
        notNull(userId,"User Id");
        User user = userRepo.findById(userId).orElseThrow(()->new UserException("user not Found"));
        return user.getFavorites();
    }

    public void addToFavorite(UUID currentUserId, UUID mediaId){
        notNull(currentUserId, "currentUserId");
        notNull(mediaId, "media Id");
        userRepo.findById(currentUserId).orElseThrow(()->new UserException("user not Found"));
        mediaRepo.findById(mediaId).orElseThrow(()-> new MediaException("MediaId not found"));
        if (favoriteRepo.exists(currentUserId, mediaId)) throw new FavoriteException("Media is already a favorite");

        favoriteRepo.addNew(currentUserId,mediaId);
    }

    public void removeFromFavorite(UUID currentUserId, UUID mediaId){
        notNull(currentUserId, "currentUserId");
        notNull(mediaId, "media Id");
        userRepo.findById(currentUserId).orElseThrow(()->new UserException("user not Found"));
        mediaRepo.findById(mediaId).orElseThrow(()->new MediaException("Media not found"));
        favoriteRepo.remove(currentUserId, mediaId);
    }
    public UserStatistic getUserStatistic(UUID currentUserId,UUID userId){
        notNull(currentUserId, "currentUserId");
        notNull(userId, "user Id");
        if (!currentUserId.equals(userId)) throw new UserException("Lacking Permissions");

        User user = userRepo.findById(userId).orElseThrow(()->new UserException("user not Found"));

        List<Rating> ratingsFromUser = ratingRepo.findAll().stream()
                .filter(rating -> rating.getOwnerId() == userId)
                .toList();

        int totalRatings= ratingsFromUser.size();

        int allStars=ratingsFromUser.stream().mapToInt(r->r.getStars()).sum();

        return new UserStatistic(totalRatings,(double)allStars/totalRatings,user.getFavoriteCount());
    }

    @Override
    public List<Rating> getRatingHistory(UUID currentUserId, UUID userId) {
        notNull(currentUserId, "currentUserId");
        notNull(userId, "user Id");
        if (!currentUserId.equals(userId)) throw new UserException("Lacking Permissions");

        return ratingRepo.findAll().stream()
                .filter(rating -> rating.getOwnerId() == userId)
                .toList();
    }

    @Override
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> entries = new ArrayList<>();

        int i = 0;
        for (User u : userRepo.findAll()){
            entries.add(new LeaderboardEntry(i+1,u.getId(),u.getUsername(),ratingRepo.findAll().stream()
                    .filter(rating -> rating.getOwnerId() == u.getId())
                    .toList().size()));
            i++;
        }

        return entries;
    }


}
