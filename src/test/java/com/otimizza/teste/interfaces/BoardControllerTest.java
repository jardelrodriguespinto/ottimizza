package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.BoardRequest;
import com.otimizza.teste.application.usecases.BoardUseCase;
import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.infrastructure.security.JwtUtil;
import com.otimizza.teste.infrastructure.security.SecurityConfig;
import com.otimizza.teste.interfaces.mappers.BoardMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
@Import({SecurityConfig.class, BoardMapper.class})
@ActiveProfiles("test")
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardUseCase boardUseCase;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("GET /api/v1/board sem autenticação retorna 401")
    void listBoardsSemAutenticacaoRetorna401() throws Exception {
        mockMvc.perform(get("/api/v1/board"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/board autenticado retorna 200 com lista de boards paginada")
    void listBoardsAutenticadoRetorna200() throws Exception {
        when(boardUseCase.listAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Board("uuid-1", "Meu Board")), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Meu Board"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/v1/board com nome válido retorna board criado")
    void createBoardComNomeValidoRetornaBoard() throws Exception {
        when(boardUseCase.create(any(BoardRequest.class))).thenReturn(new Board("uuid-1", "Sprint Board"));

        mockMvc.perform(post("/api/v1/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Sprint Board\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("uuid-1"))
                .andExpect(jsonPath("$.name").value("Sprint Board"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/v1/board com nome em branco retorna 400")
    void createBoardComNomeEmBrancoRetorna400() throws Exception {
        mockMvc.perform(post("/api/v1/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/v1/board/{id} com board inexistente retorna 404")
    void deleteBoardInexistenteRetorna404() throws Exception {
        doThrow(new EntityNotFoundException("Board not found"))
                .when(boardUseCase).delete(anyString());

        mockMvc.perform(delete("/api/v1/board/id-invalido"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Board not found"));
    }
}
