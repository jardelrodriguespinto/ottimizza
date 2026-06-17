package com.otimizza.teste.interfaces.mappers;

import com.otimizza.teste.application.dtos.BoardDTO;
import com.otimizza.teste.domain.entities.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardDTO toDTO(Board board) {
        return new BoardDTO(board.id(), board.name());
    }
}
