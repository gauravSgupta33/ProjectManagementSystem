package com.cognizant.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cognizant.entities.Project;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.ProjectRepository;

@Service("projectService")
public class ProjectService {
	
	private ProjectRepository projectRespository;

//	@Autowired
//	private UserService userService;
	
	@Autowired
	public void setProjectRepository(ProjectRepository projectRespository) {//, UserService userService) {
		this.projectRespository = projectRespository;
//		this.userService = userService;
	}

	public List<Project> getAllProjects() {
		return projectRespository.findAll();
	}

	public Project addProject(Project project) throws ResourceNotFoundException {
//		User user = userService.findById(project.getUser().getId());
//		project.setUser(user);
		try {
			return projectRespository.save(project);
		} catch (ConstraintViolationException e) {
			throw new AlreadyExistsException("Project Name already exists.");
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("Project Name already exists.");
		}

	}

	public Project updateProject(int projectId, Project projectDetails) throws ResourceNotFoundException {
		Project project = projectRespository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found for this id :: " + projectId));
		project.setStartDate(projectDetails.getStartDate());
		project.setEndDate(projectDetails.getEndDate());
		project.setProjectName(projectDetails.getProjectName());
		project.setPriority(projectDetails.getPriority());
		project.setUser(projectDetails.getUser());
		final Project updatedProject = projectRespository.save(project);
		return updatedProject;
	}

	public Project findById(int projectId) throws ResourceNotFoundException {
		Project project = projectRespository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + projectId));
		return project;
	}
}