package com.cognizant.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cognizant.entities.ParentTask;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.repository.ParentTaskRepository;

@Service("parentTaskService")
public class ParentTaskService {

	@Autowired
	private ParentTaskRepository parentTaskRepository;
	
	@Autowired
	public void setParentTaskRepository(ParentTaskRepository parentTaskRepository) {
		this.parentTaskRepository = parentTaskRepository;
	}

	public List<ParentTask> getAllParentTask() {
		return parentTaskRepository.findAll();
	}

	public ParentTask addParentTask(ParentTask parentTask) {
		
		try {
			return parentTaskRepository.save(parentTask);
		} catch (ConstraintViolationException e) {
			throw new AlreadyExistsException("Parent Task name already exists.");
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("Parent Task name already exists.");
		}

	}
}