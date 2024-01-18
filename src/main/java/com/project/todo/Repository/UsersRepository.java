package com.project.todo.Repository;

import com.project.todo.Entities.ToDoUser;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsersRepository extends CouchbaseRepository<ToDoUser, String> {
    boolean existsByUsername(String username);
    Optional<ToDoUser> findByUsername(String username);
}
