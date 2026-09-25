package at.technikum.service;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.exception.MediaExceptions;
import at.technikum.exception.UserExceptions;
import at.technikum.model.MediaEntry;
import at.technikum.model.User;
import at.technikum.repository.sql.UserRepository;

import java.security.InvalidParameterException;
import java.util.UUID;

import static at.technikum.security.PasswordVerifier.VerifyHash;

public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    public User getUser(UUID userId){
        //find user in DB
        return null;
    }

    public void register(CreateUserDTO dto){
        if (dto.username() == null || dto.username().isBlank())
            throw new UserExceptions("Username is required");
        if (dto.password() == null || dto.password().isBlank())
            throw new UserExceptions("Password is required");
        User user = new User(dto.username(),dto.password());

        // TODO ADD USER TO DB
    }

    public boolean login(UUID id, LoginDto dto){
        User user = getUser(id);

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) throw new InvalidParameterException("Username or Password is empty");
        if (!user.getUsername().equals(dto.username())) throw new InvalidParameterException("Username is wrong");
        if (VerifyHash(dto.password(),user.getPassword().toCharArray()).verified) throw new InvalidParameterException("Password is wrong");

        //TODO TOKEN LOGIC
        return true;
    }

    public void addToFavorite(UUID currentUserId, UUID mediaId){
        User user = getUser(currentUserId);
        //user.addToFavorite(); //TODO findMediaById() in Repo
        //TODO update DB
    }

    public void removeFromFavorite(UUID currentUserId, UUID mediaId){
        User user = getUser(currentUserId);
        //user.removeFromFavorite(); // TODO findMediaById() in Repo
        // TODO UPDATE DB
    }


}
