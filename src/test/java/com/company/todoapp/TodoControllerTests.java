package com.company.todoapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;

import com.company.todoapp.models.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.todoapp.controllers.TodoController;
import com.company.todoapp.dtos.TodoResponseDTO;
import com.company.todoapp.exceptions.TodoNotFoundException;
import com.company.todoapp.services.ITodoService;

public class TodoControllerTests {

    private static final Logger log = LoggerFactory.getLogger(TodoControllerTests.class);
    @Mock
    private ITodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private Todo todo1;
    private Todo todo2;
    private TodoResponseDTO dto1;
    private TodoResponseDTO dto2;
    private List<Todo> todos;
    private List<TodoResponseDTO> dtoList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Use TodoMockDataTest to initialize mock data
        todo1 = TodoMockDataTest.createSampleTodo1();
        todo2 = TodoMockDataTest.createSampleTodo2();

        dto1 = TodoMockDataTest.convertToDTO(todo1);
        dto2 = TodoMockDataTest.convertToDTO(todo2);

        todos = List.of(todo1, todo2);
        dtoList = todos.stream()
                .map(TodoMockDataTest::convertToDTO)
                .collect(Collectors.toList());
    }

    @Test
    public void testGetAllTodos() {
        // Arrange
        when(todoService.getAllTodos()).thenReturn(todos);

        // Act
        List<TodoResponseDTO> result = todoController.getAllTodos().getBody();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dtoList, result);
    }

//    @Test
//    public void testGetByTodoId() {
//        // Arrange
//        when(todoService.getById("123")).thenReturn(todo1);
//
//        // Act
//        TodoResponseDTO result = todoController.getByTodoId("123").getBody();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(dto1.getId(), result.getId());
//    }
//
//    @Test
//    public void testGetByTodoTitle() {
//        // Arrange
//        when(todoService.getByTitle("Test Todo 1")).thenReturn(List.of(todo1));
//
//        // Act
//        List<TodoResponseDTO> result = todoController.getByTodoTitle("Test Todo 1").getBody();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(dto1, result.get(0));
//    }

    @Test
    public void testCreateTodo() {
        // Arrange
        when(todoService.addTodo(any(Todo.class))).thenReturn(todo1);

        // Act
        ResponseEntity<TodoResponseDTO> responseEntity = todoController.createTodo(todo1);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(dto1, responseEntity.getBody());
    }

    @Test
    public void testUpdateTodo() {
        // Arrange
        when(todoService.updateTodo(eq("123"), any(Todo.class))).thenReturn(todo1);

        // Act
        ResponseEntity<TodoResponseDTO> responseEntity = todoController.updateTodo("123", todo1);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto1, responseEntity.getBody());
    }

    @Test
    public void testDeleteTodo() {
        // Arrange
        when(todoService.deleteTodo("123")).thenReturn(todo1);

        // Act
        ResponseEntity<TodoResponseDTO> responseEntity = todoController.deleteTodo("123");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto1, responseEntity.getBody());
    }

    @Test
    public void testTodoNotFoundException() {
        // Arrange
        when(todoService.getById(any(String.class))).thenThrow(new TodoNotFoundException("Todo Not Found"));

        // Act & Assert
        assertThrows(TodoNotFoundException.class, () -> {
            todoController.getByTodoId("123");
        });
    }
}
