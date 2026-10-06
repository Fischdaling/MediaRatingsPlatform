package at.technikum.model;

import at.technikum.util.exception.EntityException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public String toString() {
        String str = "Id: " + id +
                " Created At: " + createAt +
                " Last Updated At: " + getUpdatedAt();
        return str;
    }

    public String toJsonString() {
        ObjectMapper ow = new ObjectMapper();
        try {
            return ow.writeValueAsString(this);
        } catch (Exception e) {
            throw new IllegalStateException("Could not serialize " + getClass().getSimpleName(), e);
        }
    }
}
