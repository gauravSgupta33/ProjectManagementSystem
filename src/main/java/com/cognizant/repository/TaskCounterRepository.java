package com.cognizant.repository;

//@Repository
//public interface TaskCounterRepository extends JpaRepository<Integer, Task>{
//	@Query(value="select count(t.Task_ID), t.project_id from task t group by t.project_id", nativeQuery = true)
//	public List<Object> getTaskCount();
//
//	@Query(value="select count(t.Task_ID), t.project_id, t.status from task where t.project_id=:projectId group by t.project_id, t.status", nativeQuery = true)
//	public List<Object> getTaskCountByPrjectId(@Param("projectId") int projectId);
//
//	@Query(value="select count(Task_ID), project_id, status from task where project_id=:projectId and status=1 group by project_id, status", nativeQuery = true)
//	public List<Object> getTotalTaskAndCompletedCountByProject(@Param("projectId") int projectId);
//
//	@Query(value="select count(Task_ID), project_id, status from task where project_id=:projectId and status=1 group by project_id, status", nativeQuery = true)
//	public List<Object> getTaskAndCompletedCountGroupByProject();
//}
