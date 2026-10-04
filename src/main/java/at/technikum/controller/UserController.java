package at.technikum.controller;


import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.repository.util.Response;

import java.util.UUID;

public class UserController implements IUserController {
    @Override
    public Response register(CreateUserDTO dto) {
        return null;
    }

    @Override
    public Response login(LoginDto dto) {
        return null;
    }

    @Override
    public Response getUserProfile(UUID userId) {
        return null;
    }

    @Override
    public Response getFavorites(UUID userId) {
        return null;
    }

    @Override
    public Response getRatingHistory(UUID userId) {
        return null;
    }

    @Override
    public Response getLeaderboard() {
        return null;
    }

    @Override
    public Response getRecommendations(UUID userId) {
        return null;
    }
    // define Auth Service
    // Register
    // LOGIN
    // Favorites

}
