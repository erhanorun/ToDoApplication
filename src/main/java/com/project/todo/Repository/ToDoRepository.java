package com.project.todo.Repository;

import com.project.todo.Entities.ToDoTask;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends CouchbaseRepository<ToDoTask, String> {
    Optional<ToDoTask> findByTaskId(String taskId);
    boolean existsByTaskName(String taskName);
    Optional<ToDoTask> findByTaskName(@NotNull String taskName);
}
