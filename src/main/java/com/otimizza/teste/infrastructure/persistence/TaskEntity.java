package com.otimizza.teste.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    @Id
    private UUID id;
    private String name;
    private int position;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "due_date")
    private OffsetDateTime dueDate;
    private boolean completed;
    @Convert(converter = TagsConverter.class)
    private List<String> tags;
    @Column(name = "column_id")
    private UUID columnId;
}
