package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import com.cognizant.entities.Project;
import com.cognizant.entities.Task;
import com.cognizant.entities.User;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.ProjectRepository;
import com.cognizant.repository.TaskRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.vo.TaskCounterVO;

public class TaskServiceTest {
	@Mock
	UserService userService;

	@Mock
	ProjectService projectService;

	@InjectMocks
	TaskService taskService;

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TaskRepository taskRepository;

	private Project project;

	private Integer projectId = 1;
	private User user = null;
	private Integer userId = 1;
	private Task task = null;
	private Integer taskId = 1;

	@Before
	public void setUp() {
		projectService = mock(ProjectService.class);
		userService = mock(UserService.class);// new UserService();
		taskRepository = mock(TaskRepository.class);
		taskService = new TaskService();
		taskService.setTaskRepository(projectService, userService, taskRepository);

		task = new Task();
		project = new Project();
		project.setProjectId(projectId);
		project.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		project.setPriority(10);
		project.setProjectName("First Project");
		task.setProject(project);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		project.setUser(user);
		task.setUser(user);
		task.setParentId(12);
		task.setPriority(3);
		task.setStatus(0);
		task.setTaskName("New Task");
		task.setTaskId(taskId);
		task.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		task.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		project.setEndDate(new java.sql.Date(System.currentTimeMillis()));
	}

	@Test
	public void testAddTask() {
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
		Task retrivedTask = taskService.addTask(task);
		assertEquals(task, retrivedTask);
	}

	@Test
	public void testUpdateTask() {
		Optional<Task> optionalTask = Optional.of(task);
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(optionalTask);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
		Task retrivedTask = taskService.updateTask(task.getTaskId(), task);
		assertEquals(task, retrivedTask);
	}

	@Test(expected = AlreadyExistsException.class)
	public void testUpdateTaskWithDuplicateTest() {
		Optional<Task> optionalTask = Optional.of(task);
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(optionalTask);
		Mockito.when(taskRepository.findDuplicateTaskBeforeUpdate(Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
		taskService.updateTask(task.getTaskId(), task);
	}

	@Test(expected = AlreadyExistsException.class)
	public void addTaskWithDuplicateTest() {
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.findDuplicateTaskBeforeAdd(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(1);
		taskService.addTask(task);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateTaskIdNotExists() {
		Optional<Task> optionalTask = Optional.of(task);
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.findById(Mockito.any(Integer.class))).thenThrow(ResourceNotFoundException.class);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
		Task retrivedTask = taskService.updateTask(task.getTaskId(), task);
		// assertEquals(task, retrivedTask);
	}

	@Test(expected = AlreadyExistsException.class)
	public void addTaskWithConstraintViolationException() {
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenThrow(ConstraintViolationException.class);
		taskService.addTask(task);
	}

	@Test(expected = AlreadyExistsException.class)
	public void addTaskWithDataIntegrityViolationException() {
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenThrow(DataIntegrityViolationException.class);
		taskService.addTask(task);
	}

	@Test
	public void testGetAllTasks() {
		List<Task> taskList = new ArrayList<Task>();
		Mockito.when(taskRepository.findAll()).thenReturn(taskList);
		List<Task> retrivedTaskList = taskService.getAllTasks();
		assertEquals(retrivedTaskList, taskList);
	}

	@Test
	public void testGetTaskById() {
		List<Task> taskList = new ArrayList<Task>();
		Mockito.when(taskRepository.findTaskByProjectId(Mockito.anyInt())).thenReturn(taskList);
		List<Task> retrivedTaskList = taskService.getTask(task.getProject().getProjectId());
		assertEquals(retrivedTaskList, taskList);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void addTaskWithStartDateValidation() {
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Task tempTask = new Task();
		tempTask.setProject(project);
		tempTask.setUser(user);
		tempTask.setParentId(12);
		tempTask.setPriority(3);
		tempTask.setStatus(0);
		tempTask.setTaskName("New  tempTask");
		tempTask.setTaskId(taskId);
		tempTask.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		tempTask.setEndDate(new java.sql.Date(System.currentTimeMillis()));

		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(tempTask);
		taskService.addTask(tempTask);
	}
	
	@Test()
	public void addTaskWithStartDateGreaterAndEqualToEndDate() {
		Task tempTask = new Task();
		tempTask.setProject(project);
		tempTask.setUser(user);
		tempTask.setParentId(12);
		tempTask.setPriority(3);
		tempTask.setStatus(0);
		tempTask.setTaskName("New  tempTask");
		tempTask.setTaskId(taskId);
		tempTask.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		tempTask.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		
		Calendar c1 = GregorianCalendar.getInstance();
		c1.add(Calendar.MONTH, -2);
		
		Project tempProject = new Project();
		tempProject.setProjectId(projectId);
		tempProject.setStartDate(new java.sql.Date(c1.getTimeInMillis()));
		tempProject.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		tempProject.setPriority(10);
		tempProject.setProjectName("First Project");
		tempTask.setProject(tempProject);

		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(tempProject);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(tempTask);
		Task retrivedTask = taskService.addTask(tempTask);
		assertEquals(tempTask, retrivedTask);
	}
	
	@Test()
	public void addTaskWithDateIsBetweenProjectStartAndEndDate() {
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Task tempTask = new Task();
		tempTask.setUser(user);
		tempTask.setParentId(12);
		tempTask.setPriority(3);
		tempTask.setStatus(0);
		tempTask.setTaskName("New  tempTask");
		tempTask.setTaskId(taskId);
		tempTask.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		tempTask.setEndDate(new java.sql.Date(c.getTimeInMillis()));

		
		Calendar c1 = GregorianCalendar.getInstance();
		c1.add(Calendar.MONTH, -2);
		
		Project tempProject = new Project();
		tempProject.setProjectId(projectId);
		tempProject.setStartDate(new java.sql.Date(c1.getTimeInMillis()));
		tempProject.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		tempProject.setPriority(10);
		tempProject.setProjectName("First Project");
		tempTask.setProject(tempProject);
		
		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(tempProject);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(tempTask);
		Task retrivedTask = taskService.addTask(tempTask);
		assertEquals(tempTask, retrivedTask);
	}



	@Test(expected = ResourceNotFoundException.class)
	public void addTaskWithEndDateValidation() {
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.MONTH, 1);
		Task tempTask = new Task();
		tempTask.setProject(project);
		tempTask.setUser(user);
		tempTask.setParentId(12);
		tempTask.setPriority(3);
		tempTask.setStatus(0);
		tempTask.setTaskName("New  tempTask");
		tempTask.setTaskId(taskId);
		tempTask.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		tempTask.setEndDate(new java.sql.Date(c.getTimeInMillis()));

		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(tempTask);
		taskService.addTask(tempTask);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void tesUpdateTaskWithStartDateValidation() {
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Task tempTask = new Task();
		tempTask.setProject(project);
		tempTask.setUser(user);
		tempTask.setParentId(12);
		tempTask.setPriority(3);
		tempTask.setStatus(0);
		tempTask.setTaskName("New  tempTask");
		tempTask.setTaskId(taskId);
		tempTask.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		tempTask.setEndDate(new java.sql.Date(System.currentTimeMillis()));

		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(tempTask);
		taskService.updateTask(task.getTaskId(), tempTask);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateTaskWithEndDateValidation() {
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.MONTH, 1);
		Task tempTask = new Task();
		tempTask.setProject(project);
		tempTask.setUser(user);
		tempTask.setParentId(12);
		tempTask.setPriority(3);
		tempTask.setStatus(0);
		tempTask.setTaskName("New  tempTask");
		tempTask.setTaskId(taskId);
		tempTask.setStartDate(new java.sql.Date(System.currentTimeMillis()));
		tempTask.setEndDate(new java.sql.Date(c.getTimeInMillis()));

		Mockito.when(userService.findById(Mockito.any(Integer.class))).thenReturn(user);
		Mockito.when(projectService.findById(Mockito.any(Integer.class))).thenReturn(project);
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(tempTask);
		taskService.updateTask(tempTask.getTaskId(), tempTask);
	}

	@Test
	public void testGetTotalTaskAndCompletedCountByProject() {
		List<TaskCounterVO> taskList = new ArrayList<TaskCounterVO>();
		TaskCounterVO taskCounterVO = new TaskCounterVO();
		taskCounterVO.setCompletedCount(10);
		taskCounterVO.setProjectId(10);
		taskCounterVO.setTaskCount(20);
		taskList.add(taskCounterVO);

		Object[] obj = new Object[4];
		obj[0] = 20;
		obj[1] = 10;
		obj[2] = 1;
		obj[3] = 10;
		List<Object> objList = new ArrayList<Object>();
		objList.add(obj);
		Mockito.when(taskRepository.getTotalTaskCountAndCompletedStatusByProject(Mockito.anyInt())).thenReturn(objList);
		List<TaskCounterVO> retrivedTaskList = taskService
				.getTotalTaskAndCompletedCountByProject(task.getProject().getProjectId());
		assertEquals(retrivedTaskList.get(0).getProjectId(), taskList.get(0).getProjectId());
		assertEquals(retrivedTaskList.get(0).getTaskCount(), taskList.get(0).getTaskCount());
		assertEquals(retrivedTaskList.get(0).getCompletedCount(), taskList.get(0).getCompletedCount());
	}

	public void testGetTaskAndCompletedCountGroupByProject() {
		List<TaskCounterVO> taskList = new ArrayList<TaskCounterVO>();
		TaskCounterVO taskCounterVO = new TaskCounterVO();
		taskCounterVO.setCompletedCount(10);
		taskCounterVO.setProjectId(10);
		taskCounterVO.setTaskCount(20);
		taskList.add(taskCounterVO);

		Object[] obj = new Object[4];
		obj[0] = 20;
		obj[1] = 10;
		obj[2] = 1;
		obj[3] = 10;
		List<Object> objList = new ArrayList<Object>();
		objList.add(obj);
		Mockito.when(taskRepository.getTaskCountAndStatusGroupByProject()).thenReturn(objList);
		List<TaskCounterVO> retrivedTaskList = taskService.getTaskAndCompletedCountGroupByProject();
		assertEquals(retrivedTaskList.get(0).getProjectId(), taskList.get(0).getProjectId());
		assertEquals(retrivedTaskList.get(0).getTaskCount(), taskList.get(0).getTaskCount());
		assertEquals(retrivedTaskList.get(0).getCompletedCount(), taskList.get(0).getCompletedCount());
	}

}
