package com.company.todoapp.repository.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.company.todoapp.models.Todo;

@Repository
public interface TodoMongoRepository extends MongoRepository<Todo, String> { // Changed to String
    Optional<Todo> findById(String id);
    List<Todo> findByTitle(String title);
}
