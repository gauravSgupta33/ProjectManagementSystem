package com.cognizant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cognizant.entities.ParentTask.ParentTaskBuilder;

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
	 * Gets a builder which is used to create ParentTask objects.
	 * 
	 * @param firstName
	 *            The first name of the created user.
	 * @param lastName
	 *            The last name of the created user.
	 * @return A new Builder instance.
	 */
	public static ParentTaskBuilder getBuilder(String parentTaskName) {
		return new ParentTaskBuilder(parentTaskName);
	}

	/**
	 * Gets the full name of the person.
	 * 
	 * @return The full name of the person.
	 */
	@Transient
	public String getName() {
		StringBuilder name = new StringBuilder();

		name.append(parent_task);
		return name.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * A Builder class used to create new ParentTask objects.
	 */
	public static class ParentTaskBuilder {
		ParentTask built;

		/**
		 * Creates a new Builder instance.
		 * @param parentTaskName
		 *            The parent task name of the created ParentTask object.
		 */
		ParentTaskBuilder(String parentTaskName) {
			built = new ParentTask();
			built.setParent_task(parentTaskName);
		}

		/**
		 * Builds the new ParentTask object.
		 * @return The created ParentTask object.
		 */
		public ParentTask build() {
			return built;
		}
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