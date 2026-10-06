package at.technikum.util;

public record Response (int status, Object body){
    public static Response ok(Object body)      { return new Response(200, body); }
    public static Response created(Object body) { return new Response(201, body); }
    public static Response noContent()          { return new Response(204, null); }
    public static Response notFound()           { return new Response(404,"Not Found"); }
    public static Response methodNotAllowed(Object body)   { return new Response(405, body); }
}
