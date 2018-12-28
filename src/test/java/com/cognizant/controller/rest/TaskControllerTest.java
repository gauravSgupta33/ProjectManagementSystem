package com.cognizant.controller.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.entities.Project;
import com.cognizant.entities.Task;
import com.cognizant.entities.User;
import com.cognizant.repository.ParentTaskRepository;
import com.cognizant.repository.ProjectRepository;
import com.cognizant.repository.TaskRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.ParentTaskService;
import com.cognizant.service.ProjectService;
import com.cognizant.service.TaskService;
import com.cognizant.service.UserService;
import com.cognizant.vo.TaskCounterVO;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TaskController.class, secure = false)
public class TaskControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private ProjectService projectService;

	@MockBean
	TaskService taskService;

	@MockBean
	private ParentTaskService parentTaskService;

	@MockBean
	private ProjectRepository projectRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	TaskRepository taskRepository;

	@MockBean
	private ParentTaskRepository parentTaskRepository;

	@InjectMocks
	private TaskController taskController;

	private Task mockTask;
	private Project mockProject;
	private User mockUser;

	@Before
	public void setUp() {
		Calendar c = GregorianCalendar.getInstance();
		c.set(2018, 11, 28);

		mockTask = new Task();
		mockProject = new Project();
		mockProject.setProjectId(1);
		mockProject.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setPriority(10);
		mockProject.setProjectName("First Project");
		mockTask.setProject(mockProject);
		mockUser = new User();
		mockUser.setId(1);
		mockUser.setFirstName("Gaurav");
		mockUser.setLastName("Gupta");
		mockUser.setEmpId(123456);
		mockProject.setUser(mockUser);
		mockTask.setUser(mockUser);
		mockTask.setParentId(12);
		mockTask.setPriority(3);
		mockTask.setStatus(0);
		mockTask.setTaskName("New Task");
		mockTask.setTaskId(1);
		mockTask.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		mockTask.setEndDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setEndDate(new java.sql.Date(c.getTimeInMillis()));

	}

	@Test
	public void testGetAllTasks() throws Exception {
		List<Task> list = new ArrayList<Task>();
		list.add(mockTask);

		// Mockito.when(parentTaskRepository.findAll()).thenReturn(list);

		Mockito.when(taskService.getAllTasks()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/Tasks").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{\"taskId\":1,\"parentId\":12,\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":3,\"status\":0,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456},\"project\":{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}},\"taskName\":\"New Task\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void testAddTask() throws Exception {
		// Mockito.when(taskRepository.findAll()).thenReturn(list);
		String expected = "{\"taskId\":1,\"parentId\":12,\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":3,\"status\":0,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456},\"project\":{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}},\"taskName\":\"New Task\"}";

		Mockito.when(taskService.addTask(Mockito.any(Task.class))).thenReturn(mockTask);
		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createTask").accept(MediaType.APPLICATION_JSON)
				.content(expected).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testUpdateTask() throws Exception {
		// Mockito.when(taskRepository.findAll()).thenReturn(list);
		String expected = "{\"taskId\":1,\"parentId\":12,\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":12,\"status\":0,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456},\"project\":{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}},\"taskName\":\"New Task12\"}";

		mockTask.setPriority(12);
		mockTask.setTaskName("New Task12");
		Mockito.when(taskService.updateTask(Mockito.anyInt(), Mockito.any(Task.class))).thenReturn(mockTask);
		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateTask/1").accept(MediaType.APPLICATION_JSON)
				.content(expected).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testUpdateTaskStatus() throws Exception {
		// Mockito.when(taskRepository.findAll()).thenReturn(list);
		String expected = "{\"taskId\":1,\"parentId\":12,\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":12,\"status\":1,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456},\"project\":{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}},\"taskName\":\"New Task12\"}";

		mockTask.setPriority(12);
		mockTask.setTaskName("New Task12");
		mockTask.setStatus(1);
		Mockito.when(taskService.updateTask(Mockito.anyInt(), Mockito.any(Task.class))).thenReturn(mockTask);
		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateTaskStatus/1")
				.accept(MediaType.APPLICATION_JSON).content(expected).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetTaskByProjectId() throws Exception {
		mockTask.setPriority(12);
		mockTask.setTaskName("New Task12");
		mockTask.setStatus(1);

		List<Task> list = new ArrayList<Task>();
		list.add(mockTask);
		Mockito.when(taskService.getTask(Mockito.anyInt())).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getTasksByProjectId/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{\"taskId\":1,\"parentId\":12,\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":12,\"status\":1,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456},\"project\":{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}},\"taskName\":\"New Task12\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	
	@Test
	public void testGetTaskAndCompletedCountGroupByProject() throws Exception {
		
		List<TaskCounterVO> list = new ArrayList<TaskCounterVO>();
		
		TaskCounterVO vo = new TaskCounterVO();
		vo.setCompletedCount(10);
		vo.setProjectId(1);
		vo.setTaskCount(20);
		list.add(vo);

		Mockito.when(taskService.getTaskAndCompletedCountGroupByProject()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getTaskAndCompletedCountGroupByProject")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{\"taskCount\":20,\"projectId\":1,\"completedCount\":10}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}


}
