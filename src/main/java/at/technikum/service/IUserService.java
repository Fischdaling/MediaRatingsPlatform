package at.technikum.service;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.dto.request.UpdateUserDTO;
import at.technikum.dto.response.UserProfile;
import at.technikum.model.MediaEntry;
import at.technikum.model.User;

import java.util.Set;
import java.util.UUID;

public interface IUserService {
    UserProfile getProfile(UUID userId);
    UserProfile updateProfile(UUID userId, UpdateUserDTO dto);
    void register(CreateUserDTO dto);
    String login(LoginDto dto);
    Set<MediaEntry> getFavorites(UUID userId);
    void addToFavorite(UUID currentUserId, UUID mediaId);
    void removeFromFavorite(UUID currentUserId, UUID mediaId);
}
