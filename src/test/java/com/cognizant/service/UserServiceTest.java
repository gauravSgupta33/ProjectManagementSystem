package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.ProjectManagementSystem;
import com.cognizant.entities.User;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.model.UserTestUtil;
import com.cognizant.repository.UserRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProjectManagementSystem.class)
public class UserServiceTest {

	private UserService userService;

	private UserRepository userRepositoryMock;
	
	private static final Integer USER_ID = Integer.valueOf(5);
    private static final String FIRST_NAME = "Gaurav";
    private static final int EMPLOYEE_ID = 223306;
    private static final String FIRST_NAME_UPDATED = "GauravUpdated";
    
    private static final String LAST_NAME = "Gupta";
    private static final String LAST_NAME_UPDATED = "GuptaUpdated";

	@Before
	public void setUp() {
		userRepositoryMock = mock(UserRepository.class);
		userService = new UserService();
		userService.setUserRepository(userRepositoryMock);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void addUserTest() {
		User created = UserTestUtil.create(null, FIRST_NAME, LAST_NAME, EMPLOYEE_ID);
        User persisted = UserTestUtil.createModelObject(USER_ID, FIRST_NAME, LAST_NAME, EMPLOYEE_ID);
        
        when(userRepositoryMock.save(any(User.class))).thenReturn(persisted);
        //when(userRepositoryMock.save(created)).thenReturn(persisted);

        
        User returned = userService.addUser(created);

        ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).save(userArgument.capture());
        //verifyNoMoreInteractions(userRepositoryMock);

        assertUser(created, userArgument.getValue());
        assertEquals(persisted, returned);
	}
	
	@Test
    public void delete() throws ResourceNotFoundException {
        User deleted = UserTestUtil.createModelObject(USER_ID, FIRST_NAME, LAST_NAME, EMPLOYEE_ID);
        User persisted = UserTestUtil.createModelObject(USER_ID, FIRST_NAME, LAST_NAME, EMPLOYEE_ID);
        when(userRepositoryMock.save(any(User.class))).thenReturn(persisted);
       
        //when(userRepositoryMock.findById(USER_ID).orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + USER_ID + " Not Found"))).thenReturn(deleted);


        
        userService.removeUser(deleted);
        
       // verify(userRepositoryMock, times(1)).findById(USER_ID).orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + USER_ID + " Not Found"));
        verify(userRepositoryMock, times(1)).delete(deleted);
        //verifyNoMoreInteractions(userRepositoryMock);
        
        assertEquals(deleted, deleted);
    }
	
	@Test
    public void update() throws ResourceNotFoundException {
        User updated = UserTestUtil.create(USER_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED, EMPLOYEE_ID);
        Optional<User> user = Optional.of(UserTestUtil.createModelObject(USER_ID, FIRST_NAME, LAST_NAME, EMPLOYEE_ID));
        
        when(userRepositoryMock.findById(updated.getId())).thenReturn(user);
        User u1  = user.get();
        
        User returned = userService.updateUser(u1.getId(), u1);
        
        verify(userRepositoryMock, times(1));
    }
	
	private void assertUser(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), expected.getLastName());
    }
	
}
