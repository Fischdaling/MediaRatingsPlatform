package at.technikum.security;

import at.technikum.model.User;
import at.technikum.util.exception.UnauthorizedException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static at.technikum.util.valdiation.Validation.validateNumberInRange;

public interface IAuthentication {
    // https://www.baeldung.com/java-auth0-jwt

     String generate(User user);

    public UUID verify(String token);
}
