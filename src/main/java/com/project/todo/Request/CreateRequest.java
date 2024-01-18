package com.project.todo.Request;

import com.project.todo.Entities.Enums.TaskStatus;
import lombok.Data;

@Data
public class CreateRequest {

    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
}
