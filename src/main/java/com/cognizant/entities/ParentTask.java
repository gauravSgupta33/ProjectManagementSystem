package com.cognizant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "parent_task")
public class ParentTask {
	@Id
	@Column(name = "Parent_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer parent_ID;

	@Column(name = "ParentTask", nullable = false)
	private String parent_task;

	public Integer getParent_ID() {
		return parent_ID;
	}

	public String getParent_task() {
		return parent_task;
	}

	public void setParent_task(String parent_task) {
		this.parent_task = parent_task;
	}

	/**
	 * This setter method should only be used by unit tests.
	 * 
	 * @param id
	 */
	public void setParent_ID(Integer id) {
		this.parent_ID = id;
	}
}