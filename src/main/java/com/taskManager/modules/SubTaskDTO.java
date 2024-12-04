package com.taskManager.modules;

import lombok.Data;

@Data
public class SubTaskDTO {
    private String title;
    private Long taskId; // Parent Task ID
}
