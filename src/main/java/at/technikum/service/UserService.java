package at.technikum.service;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.exception.UserExceptions;
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
        // TODO if findUserByName(dto.username()) then throw Username is already taken exceptoion
        if (dto.username() == null || dto.username().isBlank())
            throw new UserExceptions("Username is required");
        if (dto.password() == null || dto.password().isBlank())
            throw new UserExceptions("Password is required");
        User user = new User(dto.username(),dto.password());

        // TODO ADD USER TO DB
    }

    public boolean login(LoginDto dto){
        User user = findUserByName(dto.username());

        if (dto.username().isEmpty() ||
                dto.password().isEmpty() ||
                !user.getUsername().equals(dto.username()) ||
                !VerifyHash(dto.password(),user.getPasswordHashed().toCharArray()).verified)
            throw new InvalidParameterException("Username or Password wrong");

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
