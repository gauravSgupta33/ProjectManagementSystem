package com.cognizant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cognizant.entities.Project;
import com.cognizant.entities.Task;
import com.cognizant.entities.User;
import com.cognizant.exception.AlreadyExistsException;
import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.repository.TaskRepository;
import com.cognizant.vo.TaskCounterVO;

@Service("taskService")
public class TaskService {

	UserService userService;

	ProjectService projectService;

	TaskRepository taskRepository;
	
	@Autowired
	public void setTaskRepository(ProjectService projectService, UserService userService, TaskRepository taskRepository) {
		this.projectService = projectService;
		this.userService = userService;
		this.taskRepository = taskRepository;
	}

	public Task addTask(Task task) throws ResourceNotFoundException {
		User user = userService.findById(task.getUser().getId());
		Project project = projectService.findById(task.getProject().getProjectId());

		if (task.getProject().getStartDate() != null) {
			if (task.getStartDate().before(task.getProject().getStartDate())
					|| task.getEndDate().before(task.getProject().getStartDate())) {
				throw new ResourceNotFoundException("Task start or end date should between Project's start date [ "
						+ project.getStartDate() + " ] and End date [ " + project.getEndDate() + "] ");
			}
		}

		if (task.getProject().getEndDate() != null) {
			if (task.getStartDate().after(task.getProject().getEndDate())
					|| task.getEndDate().after(task.getProject().getEndDate())) {
				throw new ResourceNotFoundException("Task start or end date should between Project's start date [ "
						+ project.getStartDate() + " ] and End date [ " + project.getEndDate() + "] ");
			}
		}

		task.setUser(user);
		task.setProject(project);
		
		if (taskRepository.findDuplicateTaskBeforeAdd(task.getTaskName(), task.getProject().getProjectId(),
				task.getParentId()) > 0) {
			throw new AlreadyExistsException("Task name [ " + task.getTaskName() + " ] under Project [ "
					+ task.getProject().getProjectId() + " ] is already present. Cannot add task.");
		}
		try {
			return taskRepository.save(task);
		} catch (ConstraintViolationException e) {
			throw new AlreadyExistsException("Parent Task name already exists.");
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("Parent Task name already exists.");
		}

	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public List<Task> getTask(int projectId) {
		return taskRepository.findTaskByProjectId(projectId);
	}

	public Task updateTask(int taskId, Task taskDetails) throws ResourceNotFoundException {
		if (taskDetails.getProject().getStartDate() != null) {
			if (taskDetails.getStartDate().before(taskDetails.getProject().getStartDate())
					|| taskDetails.getEndDate().before(taskDetails.getProject().getStartDate())) {
				throw new ResourceNotFoundException("Task start or end date cannot be before start date of Project");
			}
		}

		if (taskDetails.getProject().getEndDate() != null) {
			if (taskDetails.getStartDate().after(taskDetails.getProject().getEndDate())
					|| taskDetails.getEndDate().after(taskDetails.getProject().getEndDate())) {
				throw new ResourceNotFoundException("Task start or end date cannot be after End date of Project");
			}
		}
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " Not Found"));

		if (taskRepository.findDuplicateTaskBeforeUpdate(taskDetails.getTaskName(), taskDetails.getProject().getProjectId(),
				taskDetails.getParentId(), taskDetails.getTaskId()) > 0) {
			throw new AlreadyExistsException("Task name [ " + taskDetails.getTaskName() + " ] under Project [ "
					+ taskDetails.getProject().getProjectId() + " ] is already present. Cannot update task.");
		}
		task.setStatus(taskDetails.getStatus());
		task.setEndDate(taskDetails.getEndDate());
		task.setParentId(taskDetails.getParentId());
		task.setPriority(taskDetails.getPriority());
		task.setProject(taskDetails.getProject());
		task.setStartDate(taskDetails.getStartDate());
		task.setTaskName(taskDetails.getTaskName());
		task.setUser(taskDetails.getUser());
		final Task updatedTask = taskRepository.save(task);
		return updatedTask;
	}

	public List<TaskCounterVO> getTotalTaskAndCompletedCountByProject(int projectId) {
		List<Object> objectList = taskRepository.getTotalTaskCountAndCompletedStatusByProject(projectId);
		return createMapFromObjectList(objectList);
	}

	public List<TaskCounterVO> getTaskAndCompletedCountGroupByProject() {
		List<Object> objectList = taskRepository.getTaskCountAndStatusGroupByProject();
		return createMapFromObjectList(objectList);
	}

	private List<TaskCounterVO> createMapFromObjectList(List<Object> objectList) {
		Map<Integer, TaskCounterVO> map = new HashMap<Integer, TaskCounterVO>();
		for (Object obj : objectList) {
			Object[] val = (Object[]) obj;
			int taskCount = Integer.valueOf(val[0] + "");
			int projectId = Integer.valueOf(val[1] + "");
			int status = Integer.valueOf(val[2] + "");
			int statusCount = Integer.valueOf(val[3] + "");
			TaskCounterVO tCounterVO = null;
			if (map.containsKey(projectId)) {
				tCounterVO = map.get(projectId);
				if (status == 1) {
					tCounterVO.setCompletedCount(tCounterVO.getCompletedCount() + statusCount);
					tCounterVO.setTaskCount(tCounterVO.getCompletedCount() + taskCount);
				} else {
					tCounterVO.setTaskCount(tCounterVO.getCompletedCount() + taskCount);
				}
			} else {
				tCounterVO = new TaskCounterVO();
				tCounterVO.setTaskCount(taskCount);
				tCounterVO.setProjectId(projectId);
				if (status == 1) {
					tCounterVO.setCompletedCount(statusCount);
				}
			}
			map.put(projectId, tCounterVO);
		}
		return new ArrayList<TaskCounterVO>(map.values());

	}

}
