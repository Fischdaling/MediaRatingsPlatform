package at.technikum.model;

import at.technikum.dto.response.UserStatistic;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;

import static at.technikum.security.PasswordHasher.HashPassword;
import static at.technikum.util.valdiation.Validation.validatePassword;
import static at.technikum.util.valdiation.Validation.validateString;

public class User extends BaseEntity{
    private String username;
    private String passwordHashed;
    private Set<MediaEntry> favorites;

    public void setPassword(String password) {
        validatePassword(password, "password");

        this.passwordHashed = HashPassword(password);
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.favorites = new HashSet<>();
    }

    //Load From DB
    public User(UUID id, LocalDateTime createAt, LocalDateTime updatedAt, String username, String passwordHashed) {
        super(id, createAt,updatedAt);
        this.username = username;
        this.passwordHashed = passwordHashed;
        this.favorites = favorites;
    }

    public void setUsername(String username) {
        validateString(username, "Username");
        this.username = username;
    }

    public void setFavorites(Set<MediaEntry> favorites) {
        this.favorites = favorites;
    }

    public Set<MediaEntry> getFavorites() {
        return favorites;
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


    @Override
    public String toString() {
        return "User{" +
                "base=" + super.toString() +
                ", username='" + username + '\'' +
                ", favoriteCount=" + (favorites != null ? favorites.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return getId() != null && getId().equals(that.getId());
    }

}
