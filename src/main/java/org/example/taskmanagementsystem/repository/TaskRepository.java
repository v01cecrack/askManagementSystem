package org.example.taskmanagementsystem.repository;

import org.example.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t.* FROM task t, task_users c, app_user u WHERE t.id = c.task_id AND c.user_id = u.id AND u.email = :email", nativeQuery = true)
    List<Task> findByUserEmail(@Param("email") String email);
}
