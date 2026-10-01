package at.technikum;

import at.technikum.model.User;
import at.technikum.security.Authentication;

public class Main {
    static void main() {
        Authentication jwt = new Authentication("this-is-just-a-test-secret-1234567890");
        String token = jwt.generate(new User("fischi", "supersecret123"));
        System.out.println(token);
        System.out.println(jwt.verify(token));
    }
}
