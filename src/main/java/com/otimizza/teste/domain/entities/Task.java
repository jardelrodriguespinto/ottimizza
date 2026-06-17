package com.otimizza.teste.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.otimizza.teste.domain.defaults.DefaultProvider;
import com.otimizza.teste.domain.defaults.NullDefaultProvider;
import com.otimizza.teste.domain.validators.Validator;
import com.otimizza.teste.domain.validators.common.NotBlankValidator;
import com.otimizza.teste.domain.validators.common.NotNullValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
public class Task implements Serializable {
    private String id;
    private String name;
    private int position;
    private OffsetDateTime createdAt;
    private OffsetDateTime dueDate;
    private boolean completed;
    private List<String> tags;
    private String columnId;

    @Builder(toBuilder = true)
    @JsonCreator
    public Task(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("position") int position,
            @JsonProperty("createdAt") OffsetDateTime createdAt,
            @JsonProperty("dueDate") OffsetDateTime dueDate,
            @JsonProperty("completed") boolean completed,
            @JsonProperty("tags") List<String> tags,
            @JsonProperty("columnId") String columnId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.completed = completed;
        this.tags = tags;
        this.columnId = columnId;
        
        initializeDefaults();
        validate();
    }

    private void initializeDefaults() {
        DefaultProvider<Task> defaults = new NullDefaultProvider<>(Task::id, (t, id) -> t.id(id), t -> UUID.randomUUID().toString());
        defaults.linkWith(new NullDefaultProvider<>(Task::createdAt, (t, c) -> t.createdAt(c), t -> OffsetDateTime.now(ZoneOffset.UTC)))
                .linkWith(new NullDefaultProvider<>(Task::tags, (t, tags) -> t.tags((List<String>) tags), t -> List.of()));
        defaults.provide(this);
        
        // Ensure tags are immutable
        this.tags = List.copyOf(this.tags);
    }

    private void validate() {
        Validator<Task> validator = new NotBlankValidator<>(Task::name, "Task name cannot be null or blank");
        validator.linkWith(new NotNullValidator<>(Task::columnId, "Column id cannot be null"));
        validator.validate(this);
    }
}
