package com.taskManager.repository;

import com.taskManager.modules.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    List<SubTask> findByTaskIdAndIsDeletedFalse(Long taskId);
}

