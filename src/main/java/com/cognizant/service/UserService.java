package com.cognizant.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cognizant.entities.User;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.UserRepository;

@Service("userService")
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		List<User> userList = userRepository.findUniqueUsers();
		Map<Integer, User> userMap = new LinkedHashMap<Integer, User>();
		for (User user : userList) {
			if (!userMap.containsKey(user.getEmpId())) {
				userMap.put(user.getEmpId(), user);
			}
		}
		return new ArrayList<User>(userMap.values());
	}

	public User addUser(User user) {
		int count = userRepository.duplicateEmpId(user.getEmpId());
		if (count > 0) {
			throw new AlreadyExistsException("Employee with ID " + user.getEmpId() + " Already Exists");
		}

		try {
			return userRepository.save(user);
		} catch (ConstraintViolationException e) {
			throw new AlreadyExistsException("User Detail already exists.");
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("User Detail already exists.");
		} catch(EmptyResultDataAccessException e) {
			throw new AlreadyExistsException(e.getMessage());
		}

	}

	public User updateUser(int userId, User userDetails) throws ResourceNotFoundException {
//		User user = userRepository.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + userId + " Not Found"));
//
//		int count = userRepository.duplicateUserCount(userDetails.getEmpId(), user.getId());
//		if (count > 0) {
//			throw new ResourceNotFoundException("Employee with ID " + user.getEmpId() + " Already Exists");
//		}

//		user.setFirstName(userDetails.getFirstName());
//		user.setLastName(userDetails.getLastName());
//		user.setEmpId(userDetails.getEmpId());
		try {
			final User updatedUser = userRepository.save(userDetails);
			return updatedUser;
		} catch (ConstraintViolationException e) {
			throw new AlreadyExistsException("User Detail already exists.");
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("User Detail already exists.");
		} catch(EmptyResultDataAccessException e) {
			throw new AlreadyExistsException(e.getMessage());
		}

	}

	public User addOrUpdateProjectDetailsForUser(User userDetails) throws ResourceNotFoundException {
		return null;
	}

	public boolean removeUser(User user) throws ResourceNotFoundException {
		System.out.println("User id is " + user.getId());
//		User user1 = userRepository.findById(user.getId())
//				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + user.getId() + " Not Found"));
		
		try {
			userRepository.delete(user);
			return true;
		} catch (ConstraintViolationException e) {
			throw new AlreadyExistsException("User is already assigned to Project or Task. Cannnot Delete the user details.");
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("User is already assigned to Project or Task. Cannnot Delete the user details.");
		}
	}

	public User findById(int id) throws ResourceNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " Not Found"));
	}
}
