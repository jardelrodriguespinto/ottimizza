package com.otimizza.teste.interfaces.mappers;

import com.otimizza.teste.application.dtos.ColumnDTO;
import com.otimizza.teste.domain.entities.Column;
import org.springframework.stereotype.Component;

@Component
public class ColumnMapper {
    public ColumnDTO toDTO(Column column) {
        return new ColumnDTO(column.id(), column.name(), column.position(), column.boardId());
    }
}
