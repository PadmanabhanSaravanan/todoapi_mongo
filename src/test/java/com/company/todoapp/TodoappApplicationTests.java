package com.company.todoapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.todoapp.controllers.TodoController;
import com.company.todoapp.dtos.TodoResponseDTO;
import com.company.todoapp.exceptions.TodoNotFoundException;
import com.company.todoapp.models.Todo;
import com.company.todoapp.services.ITodoService;

public class TodoappApplicationTests {

    @Mock
    private ITodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Utility method to convert Todo to TodoResponseDTO
    private TodoResponseDTO convertToDTO(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isStatus(),
                todo.getTargetDate()
        );
    }

    @Test
    public void testGetAllTodos() {
        // Arrange
        List<Todo> todos = Arrays.asList(new Todo(), new Todo());
        List<TodoResponseDTO> dtoList = todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        when(todoService.getAllTodos()).thenReturn(todos);

        // Act
        List<TodoResponseDTO> result = todoController.getAllTodos().getBody();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dtoList, result);
    }

    @Test
    public void testGetByTodoId() {
        // Arrange
        String id = "123";
        Todo todo = new Todo();
        todo.setId(id);
        TodoResponseDTO dto = convertToDTO(todo);
        when(todoService.getById(id)).thenReturn(todo);

        // Act
        TodoResponseDTO result = todoController.getByTodoId(id).getBody();

        // Assert
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
    }

    @Test
    public void testGetByTodoTitle() {
        // Arrange
        String title = "Test";
        List<Todo> todos = Arrays.asList(new Todo(), new Todo());
        List<TodoResponseDTO> dtoList = todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        when(todoService.getByTitle(title)).thenReturn(todos);

        // Act
        List<TodoResponseDTO> result = todoController.getByTodoTitle(title).getBody();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dtoList, result);
    }

    @Test
    public void testCreateTodo() {
        // Arrange
        Todo todo = new Todo();
        TodoResponseDTO dto = convertToDTO(todo);
        when(todoService.addTodo(any(Todo.class))).thenReturn(todo);

        // Act
        ResponseEntity<TodoResponseDTO> responseEntity = todoController.createTodo(todo);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    public void testUpdateTodo() {
        // Arrange
        String id = "123";
        Todo todo = new Todo();
        todo.setId(id);
        TodoResponseDTO dto = convertToDTO(todo);
        when(todoService.updateTodo(eq(id), any(Todo.class))).thenReturn(todo);

        // Act
        ResponseEntity<TodoResponseDTO> responseEntity = todoController.updateTodo(id, todo);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    public void testDeleteTodo() {
        // Arrange
        String id = "123";
        Todo todo = new Todo();
        todo.setId(id);
        TodoResponseDTO dto = convertToDTO(todo);
        when(todoService.deleteTodo(id)).thenReturn(todo);

        // Act
        ResponseEntity<TodoResponseDTO> responseEntity = todoController.deleteTodo(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    public void testTodoNotFoundException() {
        // Arrange
        String id = "123";
        when(todoService.getById(any(String.class))).thenThrow(new TodoNotFoundException("Todo Not Found"));

        // Act & Assert
        assertThrows(TodoNotFoundException.class, () -> {
            todoController.getByTodoId(id);
        });
    }
}
