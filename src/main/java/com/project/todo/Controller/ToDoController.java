package com.project.todo.Controller;

import com.project.todo.Entities.ToDoTask;
import com.project.todo.Exceptions.CheckTaskIsPresent;
import com.project.todo.Exceptions.RepetitiveTaskException;
import com.project.todo.Repository.ToDoRepository;
import com.project.todo.Request.CreateRequest;
import com.project.todo.Request.UpdateRequest;
import com.project.todo.Service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;
    private final ToDoRepository toDoRepository;

    @PostMapping("/createTask")
    public ResponseEntity<CreateRequest> createTask(@RequestBody CreateRequest request)
            throws RepetitiveTaskException {
        toDoService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("/taskList")
    public ResponseEntity<List<ToDoTask>> getTasks() {
        List<ToDoTask> tasklist = toDoRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tasklist);
    }

    @PutMapping("/update/{taskName}")
    public ResponseEntity<ToDoTask> update(@PathVariable String taskName, @RequestBody UpdateRequest updateRequest) {
        ToDoTask updateModel = toDoRepository.findByTaskName(taskName)
                .orElseThrow(() -> new CheckTaskIsPresent("Task not exist with taskName: " + taskName));
        updateModel.setTaskStatus(updateRequest.getTaskStatus());
        updateModel.setDescription(updateRequest.getDescription());
        updateModel.setTaskName(updateRequest.getTaskName());
        toDoRepository.save(updateModel);
        return ResponseEntity.ok(updateModel);
    }

    @DeleteMapping("/delete/{taskName}")
    public void deleteTask(@PathVariable(value = "taskName") String taskName)
            throws CheckTaskIsPresent {
        ToDoTask toDoTask = toDoRepository.findByTaskName(taskName)
                .orElseThrow(() -> new CheckTaskIsPresent("The task not found for this taskName :: " + taskName));
        toDoRepository.delete(toDoTask);
    }
}
