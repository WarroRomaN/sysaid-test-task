package com.sysaid.assignment.controller;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.TaskType;
import com.sysaid.assignment.service.ITaskService;
import com.sysaid.assignment.service.TaskServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * the controller is a basic structure and save some time on "dirty" work.
 */

@RestController
@CrossOrigin
public class TaskController {

	private final ITaskService taskService;

	/**
	 * constructor for dependency injection
	 *
	 * @param taskService
	 */
	public TaskController(TaskServiceImpl taskService) {
		this.taskService = taskService;
	}

	/**
	 * will return uncompleted tasks for given user
	 *
	 * @param username the user which the tasks relevant for
	 * @param type     type of the task
	 * @return list uncompleted tasks for the user
	 */
	@GetMapping("/uncompleted-tasks/{username}")
	public ResponseEntity<List<Task>> getUncompletedTasks(@PathVariable("username") String username,
														  @RequestParam(name = "type", required = false) TaskType type) {
		List<Task> uncompletedTasks = taskService.getUncompletedTasks(username, type);
		return ResponseEntity.ok(uncompletedTasks);
	}

	@GetMapping("/day")
	public ResponseEntity<Integer> getDay() {
		return ResponseEntity.ok(taskService.getDay());
	}

	@GetMapping("/incrementDay")
	public ResponseEntity<Integer> getIncrementedDay() {
		return ResponseEntity.ok(taskService.incrementDay());
	}


	@GetMapping("/completed-tasks/{username}")
	public ResponseEntity<List<Task>> getCompletedTasks(@PathVariable("username") String username) {
		List<Task> uncompletedTasks = taskService.getCompletedTasks(username);
		return ResponseEntity.ok(uncompletedTasks);
	}

	@GetMapping("/inwishlist-tasks/{username}")
	public ResponseEntity<List<Task>> getTasksInWishList(@PathVariable("username") String username) {
		List<Task> uncompletedTasks = taskService.getTasksInWishList(username);
		return ResponseEntity.ok(uncompletedTasks);
	}

	@PostMapping("markascomplete/{username}")
	public void markTaskAsComplete(@PathVariable("username") String username,
								   @RequestParam(name = "key") String key) {
		taskService.markTaskAsComplete(username, key);
	}

	@PostMapping("addtasktowishlist/{username}")
	public ResponseEntity<String> addTaskToWishList(@PathVariable("username") String username,
													@RequestParam(name = "key") String key) {
		taskService.addTaskToWishList(username, key);
		return ResponseEntity.noContent().build();
	}

	/**
	 * example for simple API use
	 *
	 * @return random task of the day
	 */
	@GetMapping("/task-of-the-day")
	public ResponseEntity<Task> getTaskOfTheDay() {
		return ResponseEntity.ok(taskService.getTaskOfTheDay());
	}

}

