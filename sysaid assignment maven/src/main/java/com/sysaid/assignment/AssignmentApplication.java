package com.sysaid.assignment;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.service.ITaskService;
import com.sysaid.assignment.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@EnableCaching
@SpringBootApplication
public class AssignmentApplication implements CommandLineRunner {

    public AssignmentApplication(ITaskService taskService, IUserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    private final ITaskService taskService;

    private final IUserService userService;

    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        List<String> names = Arrays.asList("Roman", "Alex", "Anton", "Victor", "Anna", "Ivan", "Max", "Lena");
        names.stream()
                .map(User::new)
                .forEach(userService::addUser);

//        for (int i = 0; i < 10; i++) {
//            User user = userService.getRandomUser();
//            List<Task> tasks = taskService.getUncompletedTasks(userService.getRandomUser().getUsername(), null);
//            taskService.addTaskToWishList(user.getUsername(), tasks.get(random.nextInt(tasks.size())).getKey());
//        }
//        for (int i = 0; i < 10; i++) {
//            User user = userService.getRandomUser();
//            List<Task> tasks = taskService.getUncompletedTasks(userService.getRandomUser().getUsername(), null);
//            taskService.markTaskAsComplete(user.getUsername(), tasks.get(random.nextInt(tasks.size())).getKey());
//        }
//        for (int i = 0; i < 5; i++) {
//            User user = userService.getRandomUser();
//            List<Task> tasks = taskService.getTasksInWishList(userService.getRandomUser().getUsername());
//            if (!tasks.isEmpty()) {
//                taskService.markTaskAsComplete(user.getUsername(), tasks.get(random.nextInt(tasks.size())).getKey());
//            }
//        }

    }
}
