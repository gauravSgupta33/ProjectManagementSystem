package com.cognizant.controller.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

//import org.json.JSONArray;
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
import com.cognizant.entities.User;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.ParentTaskRepository;
import com.cognizant.repository.ProjectRepository;
import com.cognizant.repository.TaskRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.ProjectService;
import com.cognizant.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectController.class, secure = false)
public class ProjectControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private ProjectService projectService;

	@MockBean
	private ProjectRepository projectRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	TaskRepository taskRepository;

	@MockBean
	private ParentTaskRepository parentTaskRepository;

	@InjectMocks
	ProjectController projectController;

	Project mockProject = new Project();

	@Test
	public void testGetAllProjects() throws Exception {
		Calendar c = GregorianCalendar.getInstance();
		c.set(2018, 11, 28);
		mockProject = new Project();
		mockProject.setProjectId(1);
		mockProject.setEndDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setPriority(10);
		mockProject.setProjectName("First Project");
		User user = new User();
		user.setId(1);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		mockProject.setUser(user);
		List<Project> list = new ArrayList<Project>();
		list.add(mockProject);

		Mockito.when(projectService.getAllProjects()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/projects").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateProject() throws Exception {
		Calendar c = GregorianCalendar.getInstance();
		c.set(2018, 11, 28);
		mockProject = new Project();
		mockProject.setProjectId(1);
		mockProject.setEndDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setPriority(10);
		mockProject.setProjectName("First Project");
		User user = new User();
		user.setId(1);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		mockProject.setUser(user);
		List<Project> list = new ArrayList<Project>();
		list.add(mockProject);

		String expected = "{\"projectId\":1,\"projectName\":\"First Project\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":10,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}}";

		Mockito.when(projectService.addProject(Mockito.any(Project.class))).thenReturn(mockProject);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createProject").accept(MediaType.APPLICATION_JSON)
				.content(expected).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testUpdateProject() throws Exception {
		Calendar c = GregorianCalendar.getInstance();
		c.set(2018, 11, 28);
		Project mockProject = new Project();
		mockProject.setProjectId(1);
		mockProject.setEndDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setPriority(15);
		mockProject.setProjectName("First Project12");
		User user = new User();
		user.setId(1);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		mockProject.setUser(user);
		List<Project> list = new ArrayList<Project>();
		list.add(mockProject);

		String expected = "{\"projectId\":1,\"projectName\":\"First Project12\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":15,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}}";

		Mockito.when(projectService.updateProject(Mockito.anyInt(), Mockito.any(Project.class)))
				.thenReturn(mockProject);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateProject/1")
				.accept(MediaType.APPLICATION_JSON).content(expected).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testUpdateProjectWithResouceNotFoundException() throws Exception {
		Calendar c = GregorianCalendar.getInstance();
		c.set(2018, 11, 28);
		Project mockProject = new Project();
		mockProject.setProjectId(1);
		mockProject.setEndDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setStartDate(new java.sql.Date(c.getTimeInMillis()));
		mockProject.setPriority(15);
		mockProject.setProjectName("First Project12");
		User user = new User();
		user.setId(1);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		mockProject.setUser(user);
		List<Project> list = new ArrayList<Project>();
		list.add(mockProject);

		String expected = "{\"projectId\":1,\"projectName\":\"First Project12\",\"startDate\":\"2018-12-28\",\"endDate\":\"2018-12-28\",\"priority\":15,\"user\":{\"id\":1,\"firstName\":\"Gaurav\",\"lastName\":\"Gupta\",\"empId\":123456}}";

		Mockito.when(projectService.updateProject(Mockito.anyInt(), Mockito.any(Project.class)))
				.thenThrow(ResourceNotFoundException.class);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateProject/1")
				.accept(MediaType.APPLICATION_JSON).content(expected).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResolvedException().getClass().toString());

		Thread.sleep(1000);
		// int errorCode = ;
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		assertEquals("class com.cognizant.exception.ResourceNotFoundException",
				result.getResolvedException().getClass().toString());
	}

}
