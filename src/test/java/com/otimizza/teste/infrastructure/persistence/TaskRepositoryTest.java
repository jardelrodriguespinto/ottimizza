package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.repositories.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaTaskRepositoryImpl.class)
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Deve salvar e buscar tarefas por columnId")
    void deveSalvarEBuscarTarefas() {
        String columnId = UUID.randomUUID().toString();
        Task t2 = new Task(UUID.randomUUID().toString(), "Task 2", 1, OffsetDateTime.now(), null, false, List.of(), columnId);
        Task t1 = new Task(UUID.randomUUID().toString(), "Task 1", 0, OffsetDateTime.now(), null, false, List.of(), columnId);

        taskRepository.save(t2);
        taskRepository.save(t1);

        List<Task> tasks = taskRepository.findByColumnId(columnId);

        assertThat(tasks.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve salvar tarefa com tags")
    void deveSalvarTarefaComTags() {
        String id = UUID.randomUUID().toString();
        Task t = new Task(id, "Tags Task", 0, OffsetDateTime.now(), null, false, List.of("java", "spring"), UUID.randomUUID().toString());
        
        taskRepository.save(t);
        
        Task found = taskRepository.findById(id).orElseThrow();
        assertThat(found.tags()).containsExactly("java", "spring");
    }
}
