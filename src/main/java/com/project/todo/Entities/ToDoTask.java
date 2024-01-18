package com.project.todo.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.todo.Entities.Enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.CompositeQueryIndex;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@CompositeQueryIndex(fields = {"taskId", "taskName desc"})
public class ToDoTask {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @EqualsAndHashCode.Include
    private String taskId;

    @NotNull
    @QueryIndexed
    private String taskName;

    @NotNull
    @Size(min = 20, message = "Please create a description of the task in at least 20 characters!")
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime updatedTime = LocalDateTime.now();

    @NotNull
    private TaskStatus taskStatus;
}
