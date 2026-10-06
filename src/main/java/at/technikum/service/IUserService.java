package at.technikum.service;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.dto.request.UpdateUserDTO;
import at.technikum.dto.response.LeaderboardEntry;
import at.technikum.dto.response.UserStatistic;
import at.technikum.model.MediaEntry;
import at.technikum.model.Rating;
import at.technikum.model.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IUserService {
    User getProfile(UUID userId);
    User updateProfile(UUID currentUserId, UUID userId, UpdateUserDTO dto);
    void deleteUser(UUID currentUserId, UUID userId);
    User register(CreateUserDTO dto);
    String login(LoginDto dto);
    Set<MediaEntry> getFavorites(UUID userId);
    void addToFavorite(UUID currentUserId, UUID mediaId);
    void removeFromFavorite(UUID currentUserId, UUID mediaId);
    UserStatistic getUserStatistic(UUID currentUserId,UUID userId);
    List<Rating> getRatingHistory(UUID currentUserId,UUID userId);
    List<LeaderboardEntry> getLeaderboard();

    }
