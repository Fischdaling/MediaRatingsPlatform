package at.technikum;

import at.technikum.model.User;
import at.technikum.repository.Database;
import at.technikum.security.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    static void main() {
        Authentication jwt = new Authentication("this-is-just-a-test-secret-1234567890");
        String token = jwt.generate(new User("fischi", "supersecret123"));
        System.out.println(token);
        System.out.println(jwt.verify(token));

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT version()")) {
            rs.next();
            System.out.println("Connected: " + rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    }
}
