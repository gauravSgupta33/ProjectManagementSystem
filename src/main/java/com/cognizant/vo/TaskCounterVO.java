package com.cognizant.vo;

public class TaskCounterVO {
	private int taskCount;
	private int projectId;
	private int completedCount;

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	@Override
	public String toString() {
		return "TaskCounterVO [taskCount=" + taskCount + ", projectId=" + projectId + ", completedCount="
				+ completedCount + "]";
	}
}