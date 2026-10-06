package at.technikum.controller;


import at.technikum.dto.request.CreateUserDTO;
import at.technikum.dto.request.LoginDto;
import at.technikum.dto.request.UpdateRatingDTO;
import at.technikum.dto.request.UpdateUserDTO;
import at.technikum.dto.response.*;
import at.technikum.service.IUserService;
import at.technikum.util.Response;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.UUID;

public class UserController extends BaseController implements IUserController {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    private UserStatistic getUserStatistic(UUID currentUserID, UUID userId){
        return service.getUserStatistic(currentUserID,userId);
    }
    // /api/register
    @Override
    public Response register(CreateUserDTO dto) {
        return Response.created(UserProfileDTO.from(service.register(dto),null));
    }
    // /api/login
    @Override
    public Response login(LoginDto dto) {
        return Response.ok(new TokenDTO(service.login(dto)));
    }

    // /api/user/{Id}
    @Override
    public Response getUserProfile(UUID currentUserId, UUID userId) {
        return Response.ok(UserProfileDTO.from(service.getProfile(userId),getUserStatistic(currentUserId,userId)));
    }

    @Override
    public Response updateUser(UUID currentUserId, UUID userId, UpdateUserDTO dto) {
        return Response.ok(UserProfileDTO.from(service.updateProfile(currentUserId, userId, dto),getUserStatistic(currentUserId,userId)));
    }

    @Override
    public Response deleteUser(UUID currentUserId, UUID userId) {
        service.deleteUser(currentUserId, userId);
        return Response.notFound();
    }

    // /api/user/{Id}
    @Override
    public Response getFavorites(UUID currentUserId,UUID userId) {
        return Response.ok(new FavoriteDTO(service.getFavorites(userId)));
    }

    @Override
    public Response getRatingHistory(UUID currentUserId,UUID userId) {
        return Response.ok(new RatingHistoryDTO(service.getRatingHistory(currentUserId, userId)));
    }

    @Override
    public Response getLeaderboard() {
        return Response.ok(service.getLeaderboard());
    }

    @Override
    public Response getRecommendations(UUID currentUserId,UUID userId) {
        return null;
    } //TODO IMPLEMENT

    @Override
    protected Response handleRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        UUID userId = getUserId(exchange);

        // /api/register
        if (pathParts.length == 3 && pathParts[2].equals("register")){
            if (method.equals("POST")) return register(ow.readValue(exchange.getRequestBody(), CreateUserDTO.class));
            return Response.methodNotAllowed("Allow: POST");
        }

        // /api/register
        if (pathParts.length == 3 && pathParts[2].equals("login")){
            if (method.equals("POST")) return login(ow.readValue(exchange.getRequestBody(), LoginDto.class));
            return Response.methodNotAllowed("Allow: POST");
        }

        // /api/users/{id}
        if (pathParts.length == 4){
            UUID id = UUID.fromString(pathParts[3]);

            if (method.equals("PUT")) return updateUser(userId,id,ow.readValue(exchange.getRequestBody(), UpdateUserDTO.class));
            if (method.equals("DELETE")) return deleteUser(userId,id);
            return Response.methodNotAllowed("Allow: PUT, DELETE");

        }
        // /api/users/{id}/profile
        if (pathParts.length == 5 && pathParts[4].equals("profile")){
            UUID id = UUID.fromString(pathParts[3]);
            if (method.equals("GET")) return getUserProfile(userId,id);
            return Response.methodNotAllowed("Allow: GET");
        }
        // /api/users/{id}/ratings
        if (pathParts.length == 5 && pathParts[4].equals("ratings")){
            UUID id = UUID.fromString(pathParts[3]);
            if (method.equals("GET")) return getRatingHistory(userId,id);
            return Response.methodNotAllowed("Allow: GET");
        }
        // /api/users/{id}/favorites
        if (pathParts.length == 5 && pathParts[4].equals("favorites")){
            UUID id = UUID.fromString(pathParts[3]);
            if (method.equals("GET")) return getFavorites(userId,id);
            return Response.methodNotAllowed("Allow: GET");
        }

        return Response.notFound();
    }


    // define Auth Service
    // Register
    // LOGIN
    // Favorites

}
