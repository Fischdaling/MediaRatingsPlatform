package at.technikum.controller;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.dto.request.UpdateUserDTO;
import at.technikum.dto.response.TokenDTO;
import at.technikum.util.Response;

import java.util.UUID;


public interface IUserController {
    // /users/register
    Response register(CreateUserDTO dto);
    // /users/login
    Response login(LoginDto dto);
    // users/{id}
    Response updateUser(UUID currentUserId, UUID userId, UpdateUserDTO dto);
    Response deleteUser(UUID currentUserId, UUID userId);
    // users/{id}/profile
    Response getUserProfile(UUID currentUserId, UUID userId);
    // users/{id}/favorites
    Response getFavorites(UUID currentUserId, UUID userId);
    // users/{id}/rating (RatingHistory)
    Response getRatingHistory(UUID currentUserId, UUID userId);
    // users/leaderboard
    Response getLeaderboard();
    // users/{id}/recommendations //TODO
    Response getRecommendations(UUID currentUserId, UUID userId);

}
