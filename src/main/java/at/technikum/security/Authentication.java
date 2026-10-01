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
// https://www.baeldung.com/java-auth0-jwt

public class Authentication implements IAuthentication {
    private static final String ISSUER = "Rokyta 3D SWEN";
    private final byte[] secret;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public Authentication(String secret) {
        validateNumberInRange(secret.length(), 32,Integer.MAX_VALUE,"Secret Length has to be over 32 characters long");
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generate(User user){
        return JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(Instant.now()
                        .plus(Duration.ofHours(168)))
                .sign(algorithm);
    }

    public UUID verify(String token){
        try {
            DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
            return UUID.fromString(jwt.getSubject());
        }catch (JWTVerificationException e){
            throw new UnauthorizedException("INvalid or expired token");
        }
    }
}
