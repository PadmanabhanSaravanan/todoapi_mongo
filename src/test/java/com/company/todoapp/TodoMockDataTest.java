package com.company.todoapp;

import java.util.Date;

import com.company.todoapp.models.Todo;
import com.company.todoapp.dtos.TodoResponseDTO;

public class TodoMockDataTest {

    // Method to create a sample Todo object
    public static Todo createSampleTodo1() {
        Todo todo = new Todo();
        todo.setId("123");
        todo.setTitle("Test Todo 1");
        todo.setDescription("Description 1");
        todo.setStatus(true);
        todo.setTargetDate(new Date()); // Current date
        return todo;
    }

    // Method to create another sample Todo object
    public static Todo createSampleTodo2() {
        Todo todo = new Todo();
        todo.setId("456");
        todo.setTitle("Test Todo 2");
        todo.setDescription("Description 2");
        todo.setStatus(false);
        todo.setTargetDate(new Date(System.currentTimeMillis() + 86400000L)); // One day later
        return todo;
    }

    // Method to convert a Todo object to TodoResponseDTO
    public static TodoResponseDTO convertToDTO(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isStatus(),
                todo.getTargetDate()
        );
    }
}
