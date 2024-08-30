package com.company.todoapp.services.repository;

import java.util.List;
import java.util.Optional;

import com.company.todoapp.exceptions.TodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.todoapp.models.Todo;
import com.company.todoapp.repository.mongodb.TodoMongoRepository;
import com.company.todoapp.services.ITodoService;

@Service
public class TodoMongoDBService implements ITodoService {

    @Autowired
    private TodoMongoRepository todoMongoRepo;

    @Override
    public List<Todo> getAllTodos() {
        return todoMongoRepo.findAll();
    }

    @Override
    public Todo getById(String id) { // Changed to String
        Optional<Todo> todo = todoMongoRepo.findById(id);
        return todo.orElse(null);
    }

    @Override
    public List<Todo> getByTitle(String title) {
        var todo = todoMongoRepo.findByTitle(title);
        if (todo == null) {
            throw new TodoNotFoundException("Todo with title '" + title + "' not found");
        }
        return todo;
    }

    @Override
    public Todo addTodo(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(java.util.UUID.randomUUID().toString()); // Generate a new UUID as a String if not already set
        }
        return todoMongoRepo.save(todo);
    }

    @Override
    public Todo updateTodo(String id, Todo todo) { // Changed to String
        Optional<Todo> existingTodo = todoMongoRepo.findById(id);
        if (existingTodo.isPresent()) {
            Todo updateTodo = existingTodo.get();
            updateTodo.setTitle(todo.getTitle());
            updateTodo.setDescription(todo.getDescription());
            updateTodo.setStatus(todo.isStatus());
            updateTodo.setTargetDate(todo.getTargetDate());
            // Update any other fields you want to modify
            return todoMongoRepo.save(updateTodo);
        }
        return null;
    }

    @Override
    public Todo deleteTodo(String id) { // Changed to String
        Optional<Todo> existingTodo = todoMongoRepo.findById(id);
        if (existingTodo.isPresent()) {
            todoMongoRepo.deleteById(id);
            return existingTodo.get();
        }
        return null;
    }
}
