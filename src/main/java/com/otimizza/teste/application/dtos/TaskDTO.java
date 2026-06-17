package com.otimizza.teste.application.dtos;

import java.time.OffsetDateTime;
import java.util.List;

public record TaskDTO(String id, String name, int position, OffsetDateTime createdAt, 
                      OffsetDateTime dueDate, boolean completed, List<String> tags, String columnId) {}
