package at.technikum.dto.response;

import java.util.*;

public record UserProfile (String username, String hashedPassword, Map<UUID,String> favoriteMediaTitles){}
