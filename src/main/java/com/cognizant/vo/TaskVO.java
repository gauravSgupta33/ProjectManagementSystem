package com.cognizant.vo;

import com.cognizant.entities.ParentTask;
import com.cognizant.entities.Task;

public class TaskVO {
	private ParentTask parentTaskVO;
	private Task task;

	public ParentTask getParentTaskVO() {
		return parentTaskVO;
	}

	public void setParentTaskVO(ParentTask parentTaskVO) {
		this.parentTaskVO = parentTaskVO;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
