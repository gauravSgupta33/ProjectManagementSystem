package com.cognizant.controller.rest;

import java.util.List;

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

import com.cognizant.entities.Project;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.service.ProjectService;

@RestController
@CrossOrigin
public class ProjectController {
	private ProjectService projectService;
	
	@Autowired
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping("/createProject")
	public Project createProject(@Valid @RequestBody Project project) throws ResourceNotFoundException {
		return projectService.addProject(project);
	}

	@GetMapping("/projects")
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}

	@PutMapping("/updateProject/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable(value = "id") int projectId,
			@Valid @RequestBody Project projectDetails) throws ResourceNotFoundException {
		return ResponseEntity.ok(projectService.updateProject(projectId, projectDetails));
	}
}
