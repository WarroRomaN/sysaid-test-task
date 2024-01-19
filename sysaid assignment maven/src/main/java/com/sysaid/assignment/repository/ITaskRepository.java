package com.sysaid.assignment.repository;

import com.sysaid.assignment.domain.Task;

import java.util.Collection;

public interface ITaskRepository {

    Collection<Task> getTasksSortedByRank();

    void addTask(Task task);

    Task findByKey(String key);

}
