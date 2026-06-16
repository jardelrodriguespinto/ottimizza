package com.otimizza.teste.domain.strategies;

import com.otimizza.teste.domain.entities.Task;
import java.util.List;

public interface ReorderStrategy {
    void reorder(List<Task> tasks);
}
