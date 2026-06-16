package com.otimizza.teste.application.dtos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record TaskDTO(UUID id, String name, int position, OffsetDateTime createdAt, 
                      OffsetDateTime dueDate, boolean completed, List<String> tags, UUID columnId) {}
