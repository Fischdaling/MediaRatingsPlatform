package at.technikum.model;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public abstract class BaseEntity {
    private final UUID id;
    private final LocalDateTime createAt;
    private LocalDateTime updatedAt;

    // if new creation
    public BaseEntity() {
        id = UUID.randomUUID();
        createAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    //Load ffrom db
    public BaseEntity(UUID id, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createAt = createAt;
        this.updatedAt =updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAtToNow(){
        setUpdatedAt(LocalDateTime.now());
    }
}
