package at.technikum.model;

import java.time.LocalDateTime;
import java.util.*;

import static at.technikum.security.PasswordHasher.HashPassword;

public class User extends BaseEntity{
    private String username;
    private String passwordHashed;
    private Set<MediaEntry> favorites;

    public void setPasswordHashed(String password) {
        this.passwordHashed = HashPassword(password);
    }

    public User(String username, String password) {
        this.username = username;
        setPasswordHashed(password);
        this.favorites = new HashSet<>();
    }

    //Load From DB
    public User(UUID id, LocalDateTime createAt, LocalDateTime updatedAt, String username, String passwordHashed, Set<MediaEntry> favorites) {
        super(id, createAt,updatedAt);
        this.username = username;
        this.passwordHashed = passwordHashed;
        this.favorites = favorites;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPasswordHashed() {
        return this.passwordHashed;
    }

    public void addToFavorite(MediaEntry favorite){
        if(favorite == null) throw new IllegalArgumentException("favorite is required");
        this.favorites.add(favorite);
    }

    public void removeFromFavorite(MediaEntry favorite){
        if(favorite == null) throw new IllegalArgumentException("favorite is required");
        favorites.remove(favorite);
    }
    
    public int getFavoriteCount(){
        return this.favorites.size();
    }
}
