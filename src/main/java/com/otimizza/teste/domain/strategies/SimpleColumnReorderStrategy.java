package com.otimizza.teste.domain.strategies;

import com.otimizza.teste.domain.entities.Task;
import java.util.Comparator;
import java.util.List;

public class SimpleColumnReorderStrategy implements ReorderStrategy {
    @Override
    public void reorder(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(Task::position));
    }
}
