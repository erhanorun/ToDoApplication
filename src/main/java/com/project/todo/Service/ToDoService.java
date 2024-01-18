package com.project.todo.Service;

import com.project.todo.Entities.Enums.TaskStatus;
import com.project.todo.Entities.ToDoTask;
import com.project.todo.Exceptions.RepetitiveTaskException;
import com.project.todo.Repository.ToDoRepository;
import com.project.todo.Request.CreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public void createTask(@RequestBody CreateRequest request) throws RepetitiveTaskException {
        ToDoTask createTask = new ToDoTask();
        createTask.setTaskName(request.getTaskName());
        createTask.setDescription(request.getTaskDescription());
        createTask.setTaskStatus(TaskStatus.IN_PROGRESS);

        if (toDoRepository.existsByTaskName(request.getTaskName())) {
            throw new RepetitiveTaskException(new Date(), "The task already exists!");
        }
        toDoRepository.save(createTask);
    }
}
