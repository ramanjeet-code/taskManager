package com.taskManager.repository;
import com.taskManager.modules.Task;
import com.taskManager.modules.TaskPriority;
import com.taskManager.modules.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriorityAndIsDeletedFalse(TaskPriority priority);

    List<Task> findByStatusAndIsDeletedFalse(TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.user.id = ?1 AND t.isDeleted = false")
    List<Task> findAllTasksByUser(Long userId);

    Page<Task> findByUser_UsernameAndIsDeletedFalse(String username,Pageable pageable);

    Page<Task> findByUser_UsernameAndPriorityAndDueDateAndIsDeletedFalse(String username, TaskPriority priority, LocalDate dueDate, Pageable pageable);

    Page<Task> findByUser_UsernameAndPriorityAndIsDeletedFalse(String username, TaskPriority priority, Pageable pageable);

    Page<Task> findByUser_UsernameAndDueDateAndIsDeletedFalse(String username, LocalDate dueDate, Pageable pageable);
}
