package at.technikum.dto.response;

import at.technikum.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public record UserProfileDTO(UUID id,
                             LocalDateTime createAt,
                             LocalDateTime updatedAt,
                             String username,
                             String hashedPassword,
                             Map<UUID,String> favoriteMediaTitles,
                             UserStatistic userStatistic){

    public static UserProfileDTO from(User u, UserStatistic userStatistic)    {
        return new UserProfileDTO(u.getId(),u.getCreateAt(),u.getUpdatedAt(),u.getUsername(),u.getPasswordHashed(),
            u.getFavorites()
            .stream()
            .map(m-> Map.entry(m.getId(),m.getTitle()))
            .collect(Collectors.toMap(m->m.getKey(), m->m.getValue())),
                userStatistic); }
}
