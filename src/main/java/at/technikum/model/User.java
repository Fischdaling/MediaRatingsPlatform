package at.technikum.model;

import at.technikum.exception.UserExceptions;

import java.util.ArrayList;
import java.util.List;

import static at.technikum.security.PasswordHasher.HashPassword;

public class User extends BaseEntity{
    private String Username;
    private String PasswordHashed;
    private List<MediaEntry> Favorites;

    public void setPasswordHashed(String password) {
        PasswordHashed = HashPassword(password);
    }

    public User(String username, String password) {
        Username = username;
        setPasswordHashed(password);
        this.Favorites = new ArrayList<>();
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return PasswordHashed;
    }

    public void addToFavorite(MediaEntry favorite){
        if(favorite == null) throw new IllegalArgumentException("favorite is required");
        if (Favorites.contains(favorite)) throw new UserExceptions("Favorite already in List");
        Favorites.add(favorite);
    }

    public void removeFromFavorite(MediaEntry favorite){
        if(favorite == null) throw new IllegalArgumentException("favorite is required");
        Favorites.remove(favorite);
    }
}
