package com.sysaid.assignment.repository;

import com.sysaid.assignment.domain.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class TaskRepositoryImpl implements ITaskRepository {

    private final Map<String, Task> tasks = new HashMap<>();

    @Override
    public Collection<Task> getTasksSortedByRank() {
        return tasks.values().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getRank(), o1.getRank()))
                .collect(Collectors.toList());
    }

    @Override
    public void addTask(Task task) {
        log.info("Added task: " + task);
        tasks.put(task.getKey(), task);
    }

    @Override
    public Task findByKey(String key) {
        return tasks.get(key);
    }


}
