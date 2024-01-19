package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.TaskType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskService {
    Task getTaskOfTheDay();

    List<Task> getUncompletedTasks(String username, TaskType type);

    List<Task> getTasksInWishList(String username);

    List<Task> getCompletedTasks(String username);

    void markTaskAsComplete(String username, String taskKey);

    void addTaskToWishList(String username, String taskKey);

    Integer getDay();
    Integer incrementDay();
}
