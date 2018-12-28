package com.cognizant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value="select distinct(Employee_ID), User_ID, First_Name, Last_Name from user", nativeQuery = true)
	public List<User> findUniqueUsers();
	
	@Query(value="select count(Employee_ID) from user where Employee_ID=:empId and User_ID <> :userId", nativeQuery = true)
	public int duplicateUserCount(@Param("empId") int empId, @Param("userId") int userId);
	
	@Query(value="select count(Employee_ID) from user where Employee_ID=:empId", nativeQuery = true)
	public int duplicateEmpId(@Param("empId") int empId);
}
