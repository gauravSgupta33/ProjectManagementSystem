package com.cognizant.controller.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

import com.cognizant.entities.ParentTask;
import com.cognizant.entities.User;
import com.cognizant.repository.ParentTaskRepository;
import com.cognizant.repository.ProjectRepository;
import com.cognizant.repository.TaskRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)

public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private ProjectRepository projectRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	TaskRepository taskRepository;

	@MockBean
	private ParentTaskRepository parentTaskRepository;

	@InjectMocks
	UserController userControllerController;

	User mockUser = new User();

	private String exampleUser = "{\"id\":1,\"firstName\":\"First Name\",\"lastName\":\"Last Name\",\"empId\":223309}";

	@Test
	public void testGetAllParentTasks() throws Exception {
		mockUser.setEmpId(223309);
		mockUser.setFirstName("First Name");
		mockUser.setLastName("Last Name");
		mockUser.setId(1);

		List<User> list = new ArrayList<User>();
		list.add(mockUser);

		// Mockito.when(parentTaskRepository.findAll()).thenReturn(list);

		Mockito.when(userService.getAllUsers()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{\"id\":1,\"firstName\":\"First Name\",\"lastName\":\"Last Name\",\"empId\":223309}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testAddUser() throws Exception {
		User mockUser = new User();
		mockUser.setEmpId(223309);
		mockUser.setFirstName("First Name12");
		mockUser.setLastName("Last Name12");
		mockUser.setId(2);
		
		String exampleUser = "{\"id\":2,\"firstName\":\"First Name12\",\"lastName\":\"Last Name12\",\"empId\":223309}";

		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(mockUser);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createUser")
				.accept(MediaType.APPLICATION_JSON).content(exampleUser).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// assertEquals("http://localhost:9080/createParentTask/1",
		// response.getHeader(HttpHeaders.LOCATION));
		String expected = "{\"id\":2,\"firstName\":\"First Name12\",\"lastName\":\"Last Name12\",\"empId\":223309}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	
	@Test
	public void testEditUser() throws Exception {
		User mockUser = new User();
		mockUser.setEmpId(223309);
		mockUser.setFirstName("First Name12");
		mockUser.setLastName("Last Name12");
		mockUser.setId(2);
		
		String exampleUser = "{\"id\":2,\"firstName\":\"First Name12\",\"lastName\":\"Last Name12\",\"empId\":223309}";

		Mockito.when(userService.updateUser(Mockito.anyInt(), Mockito.any(User.class))).thenReturn(mockUser);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/editUser/2")
				.accept(MediaType.APPLICATION_JSON).content(exampleUser).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// assertEquals("http://localhost:9080/createParentTask/1",
		// response.getHeader(HttpHeaders.LOCATION));
		String expected = "{\"id\":2,\"firstName\":\"First Name12\",\"lastName\":\"Last Name12\",\"empId\":223309}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testRemoveUser() throws Exception {
		User mockUser = new User();
		mockUser.setEmpId(223309);
		mockUser.setFirstName("First Name12");
		mockUser.setLastName("Last Name12");
		mockUser.setId(2);
		
		String exampleUser = "{\"id\":2,\"firstName\":\"First Name12\",\"lastName\":\"Last Name12\",\"empId\":223309}";

		//M<ockito.when(userService.updateUser(Mockito.anyInt(), Mockito.any(User.class))).thenReturn(mockUser);
		Mockito.doNothing().when(userRepository).delete(mockUser);


		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/removeUser")
				.accept(MediaType.APPLICATION_JSON).content(exampleUser).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// assertEquals("http://localhost:9080/createParentTask/1",
		// response.getHeader(HttpHeaders.LOCATION));
		String expected = "true";
		
		assertEquals(expected, result.getResponse().getContentAsString());
		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}