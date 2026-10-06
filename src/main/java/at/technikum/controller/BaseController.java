package at.technikum.controller;

import at.technikum.security.Authentication;
import at.technikum.util.Response;
import at.technikum.util.exception.UnauthorizedException;
import at.technikum.util.exception.apiExceptions.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public abstract class BaseController implements HttpHandler {
    protected final ObjectMapper ow = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            handleRequest(exchange);
        } catch (ApiException e){
            send(exchange,new Response(e.getStatus(), e.getMessage()));
        } catch (Exception e){
            send(exchange, new Response(500,"Server Error: "+e.getMessage()));
        }

    }

    protected abstract Response handleRequest(HttpExchange exchange) throws IOException;

    protected void send(HttpExchange exchange, Response response) throws IOException {

        try {

            if (response == null ) {
                exchange.sendResponseHeaders(500, -1);
                return;
            }

            if (response.body() == null) {
                exchange.sendResponseHeaders(response.status(), -1);
                return;
            }

            byte[] bytes = ow.writeValueAsBytes(response.body());

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(response.status(), bytes.length); // String.lenth not enough
            exchange.getResponseBody().write(bytes);

        } catch (Exception e) {
            throw new IllegalStateException("Could not serialize " + getClass().getSimpleName(), e);
        } finally {
            exchange.close();
        }
    }

    protected UUID getUserId(HttpExchange exchange){
        String header = exchange.getRequestHeaders().getFirst("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing or invalid Authorization header");
        }

        String token = header.substring("Bearer ".length()).trim();
        return Authentication.verify(token);
    }
}
