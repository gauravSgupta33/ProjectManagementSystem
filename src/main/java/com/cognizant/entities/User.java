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
@Table(name = "user")
public class User {

	@Id
	@Column(name = "User_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id = 0;

	@Column(name = "First_Name", nullable = false)
	private String firstName;

	@Column(name = "Last_Name", nullable = false, unique = true)
	private String lastName;

	@Column(name = "Employee_ID")
	private int empId;

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	/**
	 * Gets a builder which is used to create User objects.
	 * 
	 * @param firstName
	 *            The first name of the created user.
	 * @param lastName
	 *            The last name of the created user.
	 * @return A new Builder instance.
	 */
	public static UserBuilder getBuilder(String firstName, String lastName) {
		return new UserBuilder(firstName, lastName);
	}

	/**
	 * Gets the full name of the person.
	 * 
	 * @return The full name of the person.
	 */
	@Transient
	public String getName() {
		StringBuilder name = new StringBuilder();

		name.append(firstName);
		name.append(" ");
		name.append(lastName);

		return name.toString();
	}

	public void update(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * A Builder class used to create new User objects.
	 */
	public static class UserBuilder {
		User built;

		/**
		 * Creates a new Builder instance.
		 * 
		 * @param firstName
		 *            The first name of the created User object.
		 * @param lastName
		 *            The last name of the created User object.
		 */
		UserBuilder(String firstName, String lastName) {
			built = new User();
			built.firstName = firstName;
			built.lastName = lastName;
		}

		/**
		 * Builds the new User object.
		 * @return The created User object.
		 */
		public User build() {
			return built;
		}
	}

	/**
	 * This setter method should only be used by unit tests.
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}