package at.technikum.model;

import at.technikum.exception.EntityException;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    final UUID id;
    final LocalDateTime createAt;
    private LocalDateTime updatedAt;


    // if new creation
    public BaseEntity() {
        id = UUID.randomUUID();
        createAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    //Load ffrom db
    public BaseEntity(UUID id, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createAt = createAt;
        this.updatedAt =updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt.isBefore(this.updatedAt)) throw new EntityException("Last Updated cannot be in the past");
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAtToNow(){
        setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        final BaseEntity ent = (BaseEntity)obj;
        return this.id.equals(ent.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Id: ").append(id)
                .append(" Created At: ").append(createAt)
                .append(" Last Updated At: ").append(getUpdatedAt());
        return str.toString();
    }
}
