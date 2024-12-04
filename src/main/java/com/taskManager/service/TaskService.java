package com.taskManager.service;

import com.taskManager.exception.SubTaskNotFoundException;
import com.taskManager.exception.TaskNotFoundException;
import com.taskManager.modules.*;
import com.taskManager.repository.SubTaskRepository;
import com.taskManager.repository.TaskRepository;
import com.taskManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private UserRepository userRepository;

    /** Create a new task
     *
     * @param taskDTO
     * @param username
     * @return
     */
    public Task createTask(TaskDTO taskDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(TaskStatus.TODO);
        task.setUser(user);

        return taskRepository.save(task);
    }

    /** Create a new sub-task
     *
     * @param subTaskDTO
     * @return
     */
    public SubTask createSubTask(SubTaskDTO subTaskDTO) {
        Task task = taskRepository.findById(subTaskDTO.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        SubTask subTask = new SubTask();
        subTask.setTitle(subTaskDTO.getTitle());
        subTask.setTask(task);

        return subTaskRepository.save(subTask);
    }

    /** Get all tasks for a user (with optional filtering)
     *
     * @param username
     * @param priority
     * @param dueDate
     * @param pageable
     * @return
     */
    public Page<Task> getUserTasks(String username, TaskPriority priority, LocalDate dueDate, Pageable pageable) {
        if (priority != null && dueDate != null) {
            return taskRepository.findByUser_UsernameAndPriorityAndDueDateAndIsDeletedFalse(username, priority, dueDate, pageable);
        } else if (priority != null) {
            return taskRepository.findByUser_UsernameAndPriorityAndIsDeletedFalse(username, priority, pageable);
        } else if (dueDate != null) {
            return taskRepository.findByUser_UsernameAndDueDateAndIsDeletedFalse(username, dueDate, pageable);
        } else {
            return taskRepository.findByUser_UsernameAndIsDeletedFalse(username, pageable);
        }
    }


    /** Get all sub-tasks for a task
     *
     * @param taskId
     * @return
     */
    public List<SubTask> getSubTasks(Long taskId) {
        return subTaskRepository.findByTaskIdAndIsDeletedFalse(taskId);
    }

    /** Update a task's due date or status
     *
     * @param taskId
     * @param taskDTO
     * @return
     */
    public Task updateTask(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (taskDTO.getDueDate() != null) {
            task.setDueDate(taskDTO.getDueDate());
        }
        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        }

        return taskRepository.save(task);
    }

    /** Update a sub-task's status
     *
     * @param subTaskId
     * @param isCompleted
     * @return
     */
    public SubTask updateSubTask(Long subTaskId, boolean isCompleted) {
        SubTask subTask = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new SubTaskNotFoundException("Sub-task not found"));

        subTask.setCompleted(isCompleted);
        return subTaskRepository.save(subTask);
    }

    /** Soft delete a task
     *
     * @param taskId
     */
    public Task deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        task.setDeleted(true);
        task.getSubTasks().forEach(subTask -> subTask.setDeleted(true)); // Soft delete sub-tasks
        return taskRepository.save(task);
    }

    /**Soft delete a sub-task
     **/
    public SubTask deleteSubTask(Long subTaskId)  {
        // Fetch the sub-task or throw a custom exception if not found
        SubTask subTask = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new SubTaskNotFoundException("Sub-task not found"));

        subTask.setDeleted(true);
        return subTaskRepository.save(subTask);
    }
}
