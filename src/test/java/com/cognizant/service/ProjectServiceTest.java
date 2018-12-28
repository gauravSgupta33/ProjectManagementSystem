package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import com.cognizant.entities.Project;
import com.cognizant.entities.User;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.ProjectRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
	@Mock
	private ProjectRepository projectRepository;

	@InjectMocks
	private ProjectService projectService;

	private Project project;

	private Integer projectId = 1;
	private User user = null;
	private Integer userId = 1;

	@Before
	public void setUp() {
		projectRepository = mock(ProjectRepository.class);
		projectService = new ProjectService();
		projectService.setProjectRepository(projectRepository);
	}

	@Test
	public void testGetAllProjects() {
		project = new Project();
		project.setProjectId(projectId);
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		List<Project> projectList = new ArrayList<Project>();
		projectList.add(project);

		Mockito.when(projectRepository.findAll()).thenReturn(projectList);

		List<Project> retreivedProjectList = projectService.getAllProjects();

		assertEquals(projectList, retreivedProjectList);
	}

	@Test
	public void testAddProject() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

		Project retreivedProject = projectService.addProject(project);

		assertEquals(project, retreivedProject);
	}

	@Test(expected = AlreadyExistsException.class)
	public void testAddProjectConstraintViolationException() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenThrow(ConstraintViolationException.class);

		projectService.addProject(project);
	}

	@Test(expected = AlreadyExistsException.class)
	public void testAddProjectDataIntegrityViolationException() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Mockito.when(projectRepository.save(Mockito.any(Project.class)))
				.thenThrow(DataIntegrityViolationException.class);

		projectService.addProject(project);
	}

	@Test
	public void testFindById() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Optional<Project> p1 = Optional.of(project);
		Mockito.when(projectRepository.findById(Mockito.any(Integer.class))).thenReturn(p1);
		Project returnedProject = projectService.findById(project.getProjectId());
		assertEquals(p1.get(), returnedProject);

	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindByIdResourceNotFoundException() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Mockito.when(projectRepository.findById(Mockito.any(Integer.class))).thenThrow(ResourceNotFoundException.class);
		projectService.findById(project.getProjectId());
	}

	@Test
	public void testUpdateProject() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Optional<Project> p1 = Optional.of(project);
		Mockito.when(projectRepository.findById(Mockito.any(Integer.class))).thenReturn(p1);

		Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
		Project returnedProject = projectService.updateProject(project.getProjectId(), project);
		assertEquals(project, returnedProject);

	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateProjectResourceNotFoundException() {
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);

		Mockito.when(projectRepository.findById(Mockito.any(Integer.class))).thenThrow(ResourceNotFoundException.class);
		projectService.updateProject(project.getProjectId(), project);
	}
}
