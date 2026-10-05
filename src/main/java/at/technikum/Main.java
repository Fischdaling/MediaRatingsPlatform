package at.technikum;

import at.technikum.controller.MediaController;
import at.technikum.controller.RatingController;
import at.technikum.controller.UserController;
import at.technikum.model.User;
import at.technikum.repository.Database;
import at.technikum.security.Authentication;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    static void main() {
        /**** MANUAL TEST JWT ****/
        Authentication jwt = new Authentication("this-is-just-a-test-secret-1234567890");
        String token = jwt.generate(new User("fischi", "supersecret123"));
        System.out.println(token);
        System.out.println(jwt.verify(token));

        /**** MANUAL TEST DBCreate schema ****/

        try(InputStream in = Database.class.getResourceAsStream("/db/schema.sql");
             Connection conn = Database.getConnection();
             Statement st = conn.createStatement()) {
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**** MANUAL TEST STATEMENTS ****/

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * from users")) {
            rs.next();
            System.out.println("Connected: " + rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*** lets Try Server ***/
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080),0);
            server.createContext("/api/media", new MediaController());
            server.createContext("/api/user",new UserController());
            server.createContext("/api/rating",new RatingController());

            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            System.out.println(server.getAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

