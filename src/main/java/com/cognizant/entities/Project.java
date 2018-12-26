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
@Table(name = "project")
public class Project {
	@Id
	@Column(name = "Project_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int projectId;
	
	@Column(name = "ProjectName", nullable = false)
	private String projectName;
	
	@Column(name = "StartDate", nullable = true)
	@DateTimeFormat (pattern="MM-dd-yyyy")
	private Date startDate;
	
	@Column(name = "EndDate", nullable = true)
	@DateTimeFormat (pattern="MM-dd-yyyy")
	private Date endDate;
	
	@Column(name = "Priority", nullable = false)
	private int priority;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Manager_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	public int getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	
	public Date getEndDate() {
		return endDate;
	}

	
	public int getPriority() {
		return priority;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}