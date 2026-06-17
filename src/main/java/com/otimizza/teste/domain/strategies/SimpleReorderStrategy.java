package com.otimizza.teste.domain.strategies;

import com.otimizza.teste.domain.entities.Task;

import java.util.Comparator;
import java.util.List;

public class SimpleReorderStrategy implements ReorderStrategy {

    @Override
    public List<Task> reorder(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparingInt(Task::position))
                .toList();
    }
}
