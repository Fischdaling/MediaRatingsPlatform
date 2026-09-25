package at.technikum.model;


import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    private UUID id = UUID.randomUUID();
    private LocalDateTime createAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
