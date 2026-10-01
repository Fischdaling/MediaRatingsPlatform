package at.technikum.service;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.dto.request.UpdateUserDTO;
import at.technikum.dto.response.UserProfile;
import at.technikum.exception.FavoriteException;
import at.technikum.exception.MediaException;
import at.technikum.exception.UserException;
import at.technikum.model.MediaEntry;
import at.technikum.model.User;
import at.technikum.repository.interfaces.IFavoriteRepo;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.interfaces.IUserRepo;
import at.technikum.repository.sql.FavoriteRepository;
import at.technikum.repository.sql.MediaRepository;
import at.technikum.repository.sql.UserRepository;

import javax.lang.model.type.NoType;
import javax.print.attribute.standard.Media;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

import static at.technikum.security.PasswordVerifier.VerifyHash;
import static at.technikum.valdiation.Validation.*;

public class UserService implements IUserService{

    private IUserRepo userRepo;
    private IMediaRepo mediaRepo;
    private IFavoriteRepo favoriteRepo;

    public UserService(IUserRepo userRepo, IMediaRepo mediaRepo, IFavoriteRepo favoriteRepo) {
        this.userRepo = userRepo;
        this.mediaRepo = mediaRepo;
        this.favoriteRepo = favoriteRepo;
    }

    public UserProfile getProfile(UUID userId) {
        notNull(userId,"UserId");

        User user = userRepo.findById(userId).orElseThrow(()-> new UserException("User not found"));
        return new UserProfile(user.getUsername(),
                user.getPasswordHashed(),
                user.getFavorites()
                        .stream()
                        .map(m-> Map.entry(m.getId(),m.getTitle()))
                        .collect(Collectors.toMap(m->m.getKey(), m->m.getValue())));
    }

    public UserProfile updateProfile(UUID userId, UpdateUserDTO dto) {
        notNull(userId, "user Id");
        User user = userRepo.findById(userId).orElseThrow(()-> new UserException("User not found"));
        validateString(dto.username(), "Username");
        validatePassword(dto.password(), "Password");

        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFavorites(dto.favorites());

        userRepo.update(user);
        return new UserProfile(user.getUsername(),
                user.getPasswordHashed(),
                user.getFavorites()
                        .stream()
                        .map(m-> Map.entry(m.getId(),m.getTitle()))
                        .collect(Collectors.toMap(m->m.getKey(), m->m.getValue())));
    }

    public void register(CreateUserDTO dto){
        validateString(dto.username(), "Username");

        if(userRepo.findByUsername(dto.username()).isPresent()) throw new UserException("Username is already taken");

        validatePassword(dto.password(),"Password");

        userRepo.save(new User(dto.username(),dto.password()));
    }

    public boolean login(LoginDto dto){

        validateString(dto.username(), "Username");

        User user = userRepo.findByUsername(dto.username()).orElseThrow(()-> new UserException("Username or Password wrong"));

        if(!VerifyHash(dto.password(),user.getPasswordHashed().toCharArray()).verified)
            throw new InvalidParameterException("Username or Password wrong");

        //TODO TOKEN LOGIC
        return true;
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

}
