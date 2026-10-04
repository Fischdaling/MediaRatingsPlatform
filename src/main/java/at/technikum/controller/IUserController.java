package at.technikum.controller;

import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.repository.util.Response;

import java.util.UUID;


public interface IUserController {
    // /users/register
    Response register(CreateUserDTO dto);
    // /users/login
    Response login(LoginDto dto);
    // users/{id}/profile
    Response getUserProfile(UUID userId);
    // users/{id}/favorites
    Response getFavorites(UUID userId);
    // users/{id}/rating (RatingHistory)
    Response getRatingHistory(UUID userId);
    // users/leaderboard
    Response getLeaderboard();
    // users/{id}/recommendations //TODO
    Response getRecommendations(UUID userId);

}
