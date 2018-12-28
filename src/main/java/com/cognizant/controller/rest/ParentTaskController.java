package com.cognizant.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.ParentTask;
import com.cognizant.service.ParentTaskService;

@RestController
@CrossOrigin
public class ParentTaskController {
	
	private ParentTaskService parentTaskService;
	
	@Autowired
	public void setParentTaskService(ParentTaskService parentTaskService) {
		this.parentTaskService = parentTaskService;
		
	}
	
	@GetMapping("/parentTasks")
    public List<ParentTask> getAllParentTasks() {
        return parentTaskService.getAllParentTask();
    }
	
	@PostMapping("/createParentTask")
    public ParentTask createParentTask(@Valid @RequestBody ParentTask parentTask) {
        return parentTaskService.addParentTask(parentTask);
    }
}