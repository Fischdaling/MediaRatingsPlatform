package at.technikum;

import at.technikum.controller.MediaController;
import at.technikum.controller.RatingController;
import at.technikum.controller.UserController;
import at.technikum.model.User;
import at.technikum.repository.sql.*;
import at.technikum.security.Authentication;
import at.technikum.service.MediaService;
import at.technikum.service.RatingService;
import at.technikum.service.UserService;
import com.sun.net.httpserver.HttpServer;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.concurrent.Executors;

public class Main {
    static void main() {
        /**** MANUAL TEST JWT ****/
        Authentication jwt = new Authentication("this-is-just-a-test-secret-1234567890");
        String token = jwt.generate(new User("fischi", "supersecret123"));
        System.out.println(token);
        System.out.println(jwt.verify(token));

        /*** lets Try Server ***/



            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL("jdbc:postgresql://localhost:5432/postgres");
            ds.setUser("postgres");
            ds.setPassword("postgres");

            MediaRepository mediaRepository = new MediaRepository(ds);
            FavoriteRepository favoriteRepository = new FavoriteRepository(ds);
            RatingRepository ratingRepository = new RatingRepository(ds);
            UserRepository userRepository = new UserRepository(ds);

            MediaService mediaService = new MediaService(ratingRepository,mediaRepository,userRepository,favoriteRepository);
            RatingService ratingService = new RatingService(ratingRepository,mediaRepository,userRepository);
            UserService userService = new UserService(userRepository,mediaRepository,favoriteRepository,ratingRepository);

        try (Connection conn = ds.getConnection()) {
            System.out.println("Database connected");
        } catch (SQLException e) {
            System.err.println("Cannot connect to database: " + e.getMessage());
            System.exit(1);
        }

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080),0);
            server.createContext("/api/media", new MediaController(mediaService));
            server.createContext("/api/users",new UserController(userService));
            server.createContext("/api/ratings",new RatingController(ratingService));

            server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            server.start();
            System.out.println(server.getAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

