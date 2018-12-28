package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import com.cognizant.entities.ParentTask;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.repository.ParentTaskRepository;

@RunWith(MockitoJUnitRunner.class)
public class ParentTaskServiceTest {

	@InjectMocks
	private ParentTaskService parentTaskSerivce;

	@Mock
	private ParentTaskRepository parentTaskRepositoryMock;

	private ParentTask parentTask;

	@Before
	public void setUp() {
		parentTaskRepositoryMock = mock(ParentTaskRepository.class);
		parentTaskSerivce = new ParentTaskService();
		parentTaskSerivce.setParentTaskRepository(parentTaskRepositoryMock);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testAddParentTaskTest() {
		Integer parentTaskId = new Integer(1);
		parentTask = new ParentTask();
		parentTask.setParent_ID(parentTaskId);
		parentTask.setParent_task("Parent Task");

		Mockito.when(parentTaskRepositoryMock.save(Mockito.any(ParentTask.class))).thenReturn(parentTask);

		ParentTask p1 = parentTaskSerivce.addParentTask(parentTask);
		assertEquals(parentTask, p1);
	}

	@Test(expected = AlreadyExistsException.class)
	public void testAddParentTaskTestDuplicate() {
		Integer parentTaskId = new Integer(1);
		parentTask = new ParentTask();
		parentTask.setParent_ID(parentTaskId);
		parentTask.setParent_task("Parent Task");

		Mockito.when(parentTaskRepositoryMock.save(Mockito.any(ParentTask.class)))
				.thenThrow(AlreadyExistsException.class);

		parentTaskSerivce.addParentTask(parentTask);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testAddParentTaskTestDuplicateConstraintViolationException() {
		Integer parentTaskId = new Integer(1);
		parentTask = new ParentTask();
		parentTask.setParent_ID(parentTaskId);
		parentTask.setParent_task("Parent Task");

		Mockito.when(parentTaskRepositoryMock.save(Mockito.any(ParentTask.class)))
				.thenThrow(ConstraintViolationException.class);

		parentTaskSerivce.addParentTask(parentTask);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testAddParentTaskTestDuplicateDataIntegrityViolationException () {
		Integer parentTaskId = new Integer(1);
		parentTask = new ParentTask();
		parentTask.setParent_ID(parentTaskId);
		parentTask.setParent_task("Parent Task");

		Mockito.when(parentTaskRepositoryMock.save(Mockito.any(ParentTask.class)))
				.thenThrow(DataIntegrityViolationException.class);

		parentTaskSerivce.addParentTask(parentTask);
	}



	@Test
	public void testGetAllParentTask() {
		Integer parentTaskId = new Integer(1);
		parentTask = new ParentTask();
		parentTask.setParent_ID(parentTaskId);
		parentTask.setParent_task("Parent Task");

		List<ParentTask> parentTaskList = new ArrayList<ParentTask>();
		parentTaskList.add(parentTask);
		Mockito.when(parentTaskRepositoryMock.findAll()).thenReturn(parentTaskList);

		List<ParentTask> retreivedParentTaskList = parentTaskSerivce.getAllParentTask();

		assertEquals(parentTaskList, retreivedParentTaskList);

	}
}