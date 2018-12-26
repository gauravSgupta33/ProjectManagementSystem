package com.cognizant.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task")
public class Task {
	@Id
	@Column(name = "Task_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int taskId;
	
	@Column(name = "Parent_ID")
	private int parentId;

	@Column(name = "StartDate", nullable = true)
	@DateTimeFormat (pattern="MM-dd-yyyy")
	private Date startDate;

	@Column(name = "EndDate", nullable = true)
	@DateTimeFormat (pattern="MM-dd-yyyy")
	private Date endDate;

	@Column(name = "Priority", nullable = true)
	private int priority;
	
	@Column(name = "Status", nullable = true)
	private int status;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Project_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Project project;
	
	@Column(name = "Task_Name", nullable = false)
	private String taskName;

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getPriority() {
		return priority;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}


	public int getTaskId() {
		return taskId;
	}

	public int getParentId() {
		return parentId;
	}

	@Column(name = "Status")
	public int getStatus() {
		return status;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}