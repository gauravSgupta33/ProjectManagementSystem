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
	 * This setter method should only be used by unit tests.
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}