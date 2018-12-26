package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.cognizant.entities.User;
import com.cognizant.model.UserTestUtil;
import com.cognizant.repository.UserRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProjectManagementSystem.class)
public class UserServiceTest {

	private UserService userService;

	private UserRepository userRepositoryMock;
	
	private static final Integer USER_ID = Integer.valueOf(5);
    private static final String FIRST_NAME = "Gaurav";
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
		User created = UserTestUtil.create(null, FIRST_NAME, LAST_NAME);
        User persisted = UserTestUtil.createModelObject(USER_ID, FIRST_NAME, LAST_NAME);
        
        when(userRepositoryMock.save(any(User.class))).thenReturn(persisted);
        //when(userRepositoryMock.save(created)).thenReturn(persisted);

        
        User returned = userService.addUser(created);

        ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).save(userArgument.capture());
        //verifyNoMoreInteractions(userRepositoryMock);

        assertUser(created, userArgument.getValue());
        assertEquals(persisted, returned);
	}
	
	
	private void assertUser(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), expected.getLastName());
    }
	
}
