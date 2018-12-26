package com.cognizant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query(value="SELECT * FROM task t WHERE t.Project_ID= :projectId", nativeQuery = true)
	public List<Task> findTaskByProjectId(@Param("projectId") int projectId);

	
	@Query(value="select count(Task_ID), project_id, status, count(status) from task group by project_id, status", nativeQuery = true)
	public List<Object> getTaskCountAndStatusGroupByProject();

	@Query(value="select count(Task_ID), project_id, status, count(status) from task where project_id=:projectId group by project_id, status", nativeQuery = true)
	public List<Object> getTaskCountAndStatusByPrjectId(@Param("projectId") int projectId);

	@Query(value="select count(Task_ID), project_id, status, count(status) from task where project_id=:projectId and status=1 group by project_id, status", nativeQuery = true)
	public List<Object> getTotalTaskCountAndCompletedStatusByProject(@Param("projectId") int projectId);

	@Query(value="select count(Task_ID), project_id, status, count(status) from task where status=1 group by project_id, status", nativeQuery = true)
	public List<Object> getTaskCountAndCompletedStatusGroupByProject();
	
	@Query(value="select count(Task_ID)from task where Task_Name=:taskName and Project_ID=:projectId and Parent_ID=:parentId and Task_ID <> :taskId", nativeQuery = true)
	public int findDuplicateTaskBeforeUpdate(@Param("taskName") String taskName, @Param("projectId") int projectId, @Param("parentId") int parentId, @Param("taskId") int taskId);

	@Query(value="select count(Task_ID)from task where Task_Name=:taskName and Project_ID=:projectId and Parent_ID=:parentId", nativeQuery = true)
	public int findDuplicateTaskBeforeAdd(@Param("taskName") String taskName, @Param("projectId") int projectId, @Param("parentId") int parentId);
}