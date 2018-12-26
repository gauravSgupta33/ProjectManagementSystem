package com.cognizant.controller.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.Task;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.service.TaskService;
import com.cognizant.vo.TaskCounterVO;

@RestController
@CrossOrigin
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/Tasks")
    public List<Task> getAllParentTasks() {
        return taskService.getAllTasks();
    }
	
	@PostMapping("/createTask")
    public Task createTask(@Valid @RequestBody Task task) throws ResourceNotFoundException {
		return taskService.addTask(task);
    }
	
	
	@GetMapping("/getTasksByProjectId/{projectId}")
    public List<Task> getTasksByProjectId(@PathVariable("projectId") int projectId) {
        return taskService.getTask(projectId);
    }
	
	@PutMapping("/updateTaskStatus/{taskId}")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable("taskId") int taskId,
         @Valid @RequestBody Task taskDetail) throws ResourceNotFoundException {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDetail));
    }
	
	@PutMapping("/updateTask/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") int taskId,
         @Valid @RequestBody Task taskDetail) throws ResourceNotFoundException {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDetail));
    }
	
	@GetMapping("/getTaskAndCompletedCountGroupByProject")
    public List<TaskCounterVO> getTaskAndCompletedCountGroupByProject() throws ResourceNotFoundException {
        return taskService.getTaskAndCompletedCountGroupByProject();
    }
}
