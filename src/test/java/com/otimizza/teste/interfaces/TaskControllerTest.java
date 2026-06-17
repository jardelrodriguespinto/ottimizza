package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.TaskRequest;
import com.otimizza.teste.application.usecases.TaskUseCase;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.infrastructure.security.JwtUtil;
import com.otimizza.teste.infrastructure.security.SecurityConfig;
import com.otimizza.teste.interfaces.mappers.TaskMapper;
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

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import({SecurityConfig.class, TaskMapper.class})
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskUseCase taskUseCase;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/task/from/{columnId} retorna lista de tarefas paginada")
    void listByColumnRetorna200() throws Exception {
        when(taskUseCase.listByColumn(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Task("task-1", "Tarefa 1", 0, OffsetDateTime.now(), null, false, List.of(), "col-1")), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/task/from/col-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Tarefa 1"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/v1/task cria nova tarefa")
    void createTaskRetorna200() throws Exception {
        Task task = new Task("task-1", "Tarefa 1", 0, OffsetDateTime.now(), null, false, List.of("tag1"), "col-1");
        when(taskUseCase.create(any(TaskRequest.class))).thenReturn(task);

        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tarefa 1\",\"position\":0,\"columnId\":\"col-1\",\"tags\":[\"tag1\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("task-1"))
                .andExpect(jsonPath("$.tags[0]").value("tag1"));
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /api/v1/task/{id} atualiza tarefa")
    void updateTaskRetorna200() throws Exception {
        Task task = new Task("task-1", "Tarefa 1 Atualizada", 0, OffsetDateTime.now(), null, true, List.of(), "col-1");
        when(taskUseCase.update(anyString(), any(TaskRequest.class))).thenReturn(task);

        mockMvc.perform(put("/api/v1/task/task-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tarefa 1 Atualizada\",\"position\":0,\"columnId\":\"col-1\",\"completed\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tarefa 1 Atualizada"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/v1/task/{id} deleta tarefa")
    void deleteTaskRetorna200() throws Exception {
        mockMvc.perform(delete("/api/v1/task/task-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/v1/task/{id} inexistente retorna 404")
    void deleteTaskInexistenteRetorna404() throws Exception {
        doThrow(new EntityNotFoundException("Task not found"))
                .when(taskUseCase).delete(anyString());

        mockMvc.perform(delete("/api/v1/task/task-1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Task not found"));
    }
}
