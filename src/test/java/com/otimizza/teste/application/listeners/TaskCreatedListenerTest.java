package com.otimizza.teste.application.listeners;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.infrastructure.messaging.TaskMessageProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskCreatedListenerTest {

    @Mock
    private TaskMessageProducer messageProducer;

    @InjectMocks
    private TaskCreatedListener taskCreatedListener;

    @Test
    void shouldHandleExceptionWhenSendingMessage() {
        Task task = Task.builder().id("1").name("Test Task").columnId("col-1").build();
        TaskCreatedEvent event = new TaskCreatedEvent(task);

        // Configure mock to throw exception
        doThrow(new RuntimeException("RabbitMQ connection error"))
                .when(messageProducer).sendTaskCreatedMessage(any(TaskCreatedEvent.class));

        // Verify that the listener catches the exception and does not throw it
        assertDoesNotThrow(() -> taskCreatedListener.handleTaskCreated(event));

        // Verify that the producer was indeed called
        verify(messageProducer, times(1)).sendTaskCreatedMessage(event);
    }
}
