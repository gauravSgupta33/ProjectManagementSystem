package com.cognizant.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.User;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
	
	@PostMapping("/createUser")
    public User createUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }
	
	
	@PutMapping("/editUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") int userId,
         @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userId, userDetails));
    }
	
	@PostMapping("/removeUser")
    public boolean removeUser(@Valid @RequestBody User user) throws ResourceNotFoundException {
        userService.removeUser(user);
        return true;
    }
	

	@PostMapping("/addOrUpdateProjectDetailsForUser")
    public boolean addOrUpdate(@Valid @RequestBody User user) throws ResourceNotFoundException {
        userService.addOrUpdateProjectDetailsForUser(user);
        return true;
    }
}