package com.project.todo.Entities;

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

@Document
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@CompositeQueryIndex(fields = {"id", "username desc"})
public class ToDoUser {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @EqualsAndHashCode.Include
    private String id;

    @QueryIndexed
    private String username;

    private String password;

    public ToDoUser(String username, String password){
        this.username = username;
        this.password = password;

    }
}
