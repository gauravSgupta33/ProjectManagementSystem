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
import org.springframework.dao.EmptyResultDataAccessException;

import com.cognizant.entities.User;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private static UserService userService = new UserService();

	private User user;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserService();
		userService.setUserRepository(userRepository);

	}

	@Test
	public void addUser() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		List<User> userList = new ArrayList<User>();
		userList.add(user);

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		User retrunedUser = userService.addUser(user);
		assertEquals(user, retrunedUser);

	}

	@Test(expected = AlreadyExistsException.class)
	public void testAddUserWithDuplicateID() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.duplicateEmpId(Mockito.any(Integer.class))).thenReturn(1);

		userService.addUser(user);
	}

	@Test
	public void updateUser() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);

		List<User> userList = new ArrayList<User>();
		userList.add(user);

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		userService.addUser(user);

		User user1 = new User();
		user1.setId(userId);
		user1.setFirstName("Gaurav");
		user1.setLastName("Gupta");
		user1.setEmpId(123656);

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);

		User updatedUser = userService.updateUser(user1.getId(), user1);

		assertEquals(user1, updatedUser);

	}

	@Test(expected = AlreadyExistsException.class)
	public void testUpdateUserConstraintViolationException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(ConstraintViolationException.class);

		userService.updateUser(user.getId(), user);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testUpdateUserEmptyResultDataAccessException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(EmptyResultDataAccessException.class);

		userService.updateUser(user.getId(), user);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testUpdateUserDataIntegrityViolationException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(DataIntegrityViolationException.class);

		userService.updateUser(user.getId(), user);
	}
	
	
	@Test(expected = AlreadyExistsException.class)
	public void testAddUserConstraintViolationException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(ConstraintViolationException.class);

		userService.addUser(user);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testAddUserEmptyResultDataAccessException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(EmptyResultDataAccessException.class);

		userService.addUser(user);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testAddUserDataIntegrityViolationException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(DataIntegrityViolationException.class);

		userService.addUser(user);
	}




	@Test
	public void testGetAllUsers() {

		Integer userId = new Integer(1);

		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);

		List<User> userList = new ArrayList<User>();
		userList.add(user);
		Mockito.when(userRepository.findUniqueUsers()).thenReturn(userList);

		List<User> retrivedUser = userService.getAllUsers();

		assertEquals(userList, retrivedUser);

	}
	
	
	
	@Test
	public void removeUser() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);

		Mockito.doNothing().when(userRepository).delete(user);
		userService.removeUser(user);
		
	}

	
	@Test(expected = AlreadyExistsException.class)
	public void testRemoveUserConstraintViolationException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		//Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(ConstraintViolationException.class);
		Mockito.doThrow(ConstraintViolationException.class).when(userRepository).delete(user);
		userService.removeUser(user);
	}
	
	@Test(expected = AlreadyExistsException.class)
	public void testRemoveUserDataIntegrityViolationException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		//Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(DataIntegrityViolationException.class);

		Mockito.doThrow(DataIntegrityViolationException.class).when(userRepository).delete(user);
		userService.removeUser(user);
	}


	@Test
	public void testFindById() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		//Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(DataIntegrityViolationException.class);

		Optional<User> u1 = Optional.of(user);
		Mockito.when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(u1);
		User returnedUser = userService.findById(user.getId());
		assertEquals(u1.get(), returnedUser);
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindByIdResourceNotFoundException() {
		Integer userId = new Integer(1);
		user = new User();
		user.setId(userId);
		user.setFirstName("Gaurav");
		user.setLastName("Gupta");
		user.setEmpId(123456);
		Mockito.when(userRepository.findById(Mockito.any(Integer.class))).thenThrow(ResourceNotFoundException.class);
		userService.findById(user.getId());
	}


}