package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.TaskType;
import com.sysaid.assignment.repository.ITaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TaskServiceImpl implements ITaskService {

    private int day = 1;

    @Value("${external.boredapi.baseURL}")
    private String baseUrl;
    private final ITaskRepository taskRepository;

    public TaskServiceImpl(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Integer getDay() {
        return day;
    }

    @CacheEvict("taskOfTheDay")
    public Integer incrementDay() {
        return ++day;
    }

    @Cacheable("taskOfTheDay")
    public Task getTaskOfTheDay() {
        String endpointUrl = String.format("%s/activity", baseUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<Task> responseEntity = template.getForEntity(endpointUrl, Task.class);
        log.info("Task of " + day + " day: " + responseEntity.getBody());
        taskRepository.addTask(responseEntity.getBody());
        return responseEntity.getBody();
    }

    private Task getRandomTask() {
        String endpointUrl = String.format("%s/activity", baseUrl);

        RestTemplate template = new RestTemplate();
        Task task = template.getForEntity(endpointUrl, Task.class).getBody();
        taskRepository.addTask(task);
        return task;
    }

    @Override
    public List<Task> getUncompletedTasks(String username, TaskType type) {
        return taskRepository.getTasksSortedByRank().stream()
                .filter(task -> !task.getCompletedTaskUsers().contains(username))
                .filter(task -> !task.getAddedToWishListUsers().contains(username))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> getTasksInWishList(String username) {
        return taskRepository.getTasksSortedByRank().stream()
                .filter(task -> task.getAddedToWishListUsers().contains(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getCompletedTasks(String username) {
        return taskRepository.getTasksSortedByRank().stream()
                .filter(task -> task.getCompletedTaskUsers().contains(username))
                .collect(Collectors.toList());
    }

    @Override
    public void markTaskAsComplete(String username, String key) {
        Task task = taskRepository.findByKey(key);
        task.getAddedToWishListUsers().remove(username);
        task.getCompletedTaskUsers().add(username);
        task.setRank(task.getRank() + 2);
    }

    @Override
    public void addTaskToWishList(String username, String key) {
        Task task = taskRepository.findByKey(key);
        task.getAddedToWishListUsers().add(username);
        task.setRank(task.getRank() + 1);
    }

}
