package com.taskManager.controller;

import com.taskManager.authentication.JwtProvider;
import com.taskManager.modules.*;
import com.taskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtProvider jwtProvider;

    /** Create a new task
     *
     * @param taskDTO
     * @param token
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO, @RequestHeader("Authorization") String token) {
        System.out.println("token"+token);
        String username =  jwtProvider.getUsernameFromToakn(token);

        Task task = taskService.createTask(taskDTO, username);
        return ResponseEntity.ok(task);
    }

    /** Create a new sub-task
     *
     * @param subTaskDTO
     * @return
     */
    @PostMapping("/subtasks")
    public ResponseEntity<SubTask> createSubTask(@RequestBody SubTaskDTO subTaskDTO) {
        SubTask subTask = taskService.createSubTask(subTaskDTO);
        return ResponseEntity.ok(subTask);
    }


    /** Get all tasks for the authenticated user
     *
     * @param priority
     * @param dueDate
     * @param page
     * @param size
     * @param token
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Task>> getUserTasks( @RequestParam(required = false) TaskPriority priority,
                                                    @RequestParam(required = false) LocalDate dueDate,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestHeader("Authorization") String token) {

        String username = jwtProvider.getUsernameFromToakn(token);
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getUserTasks(username, priority, dueDate, pageable);
        return ResponseEntity.ok(tasks);
    }

    /** Get all sub-tasks for a task
     *
     * @param taskId
     * @return
     */
    @GetMapping("/subtasks/{taskId}")
    public ResponseEntity<List<SubTask>> getSubTasks(@PathVariable Long taskId) {
        List<SubTask> subTasks = taskService.getSubTasks(taskId);
        return ResponseEntity.ok(subTasks);
    }

    /** Update a task
     *
     * @param taskId
     * @param taskDTO
     * @return
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(taskId, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**Update a sub-task's status
     *
     * @param subTaskId
     * @param isCompleted
     * @return
     */
    @PutMapping("/subtasks/{subTaskId}")
    public ResponseEntity<SubTask> updateSubTask(@PathVariable Long subTaskId, @RequestParam boolean isCompleted) {
        SubTask updatedSubTask = taskService.updateSubTask(subTaskId, isCompleted);
        return ResponseEntity.ok(updatedSubTask);
    }

    /** soft Delete a task
     *
     * @param taskId
     * @return
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long taskId) {
        ;
        return ResponseEntity.ok(taskService.deleteTask(taskId));
    }

    /** Delete a sub-task
     *  soft delete
     * @param subTaskId
     * @return
     */
    @DeleteMapping("/subtasks/{subTaskId}")
    public ResponseEntity<SubTask> deleteSubTask(@PathVariable Long subTaskId) {

        return ResponseEntity.ok(taskService.deleteSubTask(subTaskId));
    }


}
