package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.ProjectManagementSystem;
import com.cognizant.entities.ParentTask;
import com.cognizant.model.ParentTaskTestUtil;
import com.cognizant.repository.ParentTaskRepository;;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectManagementSystem.class)
public class ParentTaskServiceTest {

	private ParentTaskService parentTaskSerivce;

	private ParentTaskRepository parentTaskRepositoryMock;

	private static final Integer PARENT_ID = Integer.valueOf(5);
    private static final String PARENT_TASK = "Planning";

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
	public void addParentTaskTest() {
		ParentTask created = ParentTaskTestUtil.create(null, PARENT_TASK);
        ParentTask persisted = ParentTaskTestUtil.createModelObject(PARENT_ID, PARENT_TASK);
        
        when(parentTaskRepositoryMock.save(any(ParentTask.class))).thenReturn(persisted);
        //when(userRepositoryMock.save(created)).thenReturn(persisted);

        
        ParentTask returned = parentTaskSerivce.addParentTask(created);

        ArgumentCaptor<ParentTask> userArgument = ArgumentCaptor.forClass(ParentTask.class);
        verify(parentTaskRepositoryMock, times(1)).save(userArgument.capture());
        //verifyNoMoreInteractions(userRepositoryMock);

        assertParentTask(created, userArgument.getValue());
        assertEquals(persisted, returned);
	}
	
	
	private void assertParentTask(ParentTask expected, ParentTask actual) {
        assertEquals(expected.getParent_ID(), actual.getParent_ID());
        assertEquals(expected.getParent_task(), actual.getParent_task());
    }
}