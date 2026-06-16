package com.otimizza.teste.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
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
    private OffsetDateTime createdAt;
    private OffsetDateTime dueDate;
    private boolean completed;
    private String tags; // Stored as comma-separated or JSON
    private UUID columnId;
}
