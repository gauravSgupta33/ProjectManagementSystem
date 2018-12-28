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
import com.cognizant.repository.ParentTaskRepository;
import com.cognizant.repository.ProjectRepository;
import com.cognizant.repository.TaskRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.ParentTaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ParentTaskController.class, secure = false)
public class ParentTaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

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
	ParentTaskController parentTaskController;

	ParentTask mockParentTask = new ParentTask();
	
	private String exampleParentTask = "{\"parent_ID\":1,\"parent_task\":\"ParentTask\"}";

	@Test
	public void testGetAllParentTasks() throws Exception {

		mockParentTask.setParent_ID(1);
		mockParentTask.setParent_task("ParentTask");

		List<ParentTask> list = new ArrayList<ParentTask>();
		list.add(mockParentTask);

		Mockito.when(parentTaskRepository.findAll()).thenReturn(list);

		Mockito.when(parentTaskService.getAllParentTask()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parentTasks").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{\"parent_ID\":1,\"parent_task\":\"ParentTask\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testAddParentTask() throws Exception {

		mockParentTask.setParent_ID(1);
		mockParentTask.setParent_task("ParentTask");

		List<ParentTask> list = new ArrayList<ParentTask>();
		list.add(mockParentTask);

		Mockito.when(parentTaskRepository.findAll()).thenReturn(list);

		Mockito.when(parentTaskService.addParentTask(Mockito.any(ParentTask.class))).thenReturn(mockParentTask);

		//RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/createParentTask").accept(MediaType.APPLICATION_JSON);
		
		// Send course as body to /students/Student1/courses
				RequestBuilder requestBuilder = MockMvcRequestBuilders
						.post("/createParentTask")
						.accept(MediaType.APPLICATION_JSON).content(exampleParentTask)
						.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		//String expected = "[{\"parent_ID\":1,\"parent_task\":\"ParentTask\"}]";
		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
		

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

//		assertEquals("http://localhost:9080/createParentTask/1",
//				response.getHeader(HttpHeaders.LOCATION));
		String expected = "{\"parent_ID\":1,\"parent_task\":\"ParentTask\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
}


}
