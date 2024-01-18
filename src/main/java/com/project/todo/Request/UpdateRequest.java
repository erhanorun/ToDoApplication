package com.project.todo.Request;

import com.project.todo.Entities.Enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateRequest {

    private String taskName;
    private String description;
    private TaskStatus taskStatus;
}
