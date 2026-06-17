package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.ColumnRequest;
import com.otimizza.teste.application.usecases.ColumnUseCase;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.infrastructure.security.JwtUtil;
import com.otimizza.teste.infrastructure.security.SecurityConfig;
import com.otimizza.teste.interfaces.mappers.ColumnMapper;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ColumnController.class)
@Import({SecurityConfig.class, ColumnMapper.class})
@ActiveProfiles("test")
class ColumnControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColumnUseCase columnUseCase;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/column/from/{boardId} retorna lista de colunas paginada")
    void listByBoardRetorna200() throws Exception {
        when(columnUseCase.listByBoard(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Column("col-1", "A Fazer", 0, "board-1")), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/column/from/board-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("A Fazer"))
                .andExpect(jsonPath("$.content[0].position").value(0));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/v1/column cria nova coluna")
    void createColumnRetorna200() throws Exception {
        when(columnUseCase.create(any(ColumnRequest.class)))
                .thenReturn(new Column("col-1", "A Fazer", 0, "board-1"));

        mockMvc.perform(post("/api/v1/column")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"A Fazer\",\"position\":0,\"boardId\":\"board-1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("col-1"))
                .andExpect(jsonPath("$.name").value("A Fazer"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/v1/column com body inválido retorna 400")
    void createColumnInvalidaRetorna400() throws Exception {
        mockMvc.perform(post("/api/v1/column")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"position\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /api/v1/column/{id} atualiza coluna")
    void updateColumnRetorna200() throws Exception {
        when(columnUseCase.update(anyString(), any(ColumnRequest.class)))
                .thenReturn(new Column("col-1", "A Fazer Atualizado", 1, "board-1"));

        mockMvc.perform(put("/api/v1/column/col-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"A Fazer Atualizado\",\"position\":1,\"boardId\":\"board-1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("A Fazer Atualizado"))
                .andExpect(jsonPath("$.position").value(1));
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/v1/column/{id} deleta coluna")
    void deleteColumnRetorna200() throws Exception {
        mockMvc.perform(delete("/api/v1/column/col-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/v1/column/{id} inexistente retorna 404")
    void deleteColumnInexistenteRetorna404() throws Exception {
        doThrow(new EntityNotFoundException("Column not found"))
                .when(columnUseCase).delete(anyString());

        mockMvc.perform(delete("/api/v1/column/col-1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Column not found"));
    }
}
