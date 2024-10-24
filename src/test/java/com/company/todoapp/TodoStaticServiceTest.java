package com.company.todoapp;

import com.company.todoapp.models.Todo;
import com.company.todoapp.services.TodoStaticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoStaticServiceTest {

    private TodoStaticService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoStaticService();
    }

    @Test
    void testGetAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        assertNotNull(todos, "Todos list should not be null");
        assertTrue(todos.size() > 0, "There should be some todos in the list");
    }

    @Test
    void testGetById() {
        List<Todo> todos = todoService.getAllTodos();
        String todoId = todos.get(0).getId();
        Todo foundTodo = todoService.getById(todoId);

        assertNotNull(foundTodo, "Todo with the given ID should be found");
        assertEquals(todoId, foundTodo.getId(), "The ID should match the requested ID");
    }

    @Test
    void testGetByIdNotFound() {
        Todo todo = todoService.getById(UUID.randomUUID().toString());
        assertNull(todo, "Todo should be null if ID is not found");
    }

    @Test
    void testAddTodo() {
        Todo newTodo = new Todo();
        newTodo.setTitle("New Task");
        newTodo.setDescription("Test the add functionality");

        Todo addedTodo = todoService.addTodo(newTodo);

        assertNotNull(addedTodo.getId(), "Newly added todo should have a generated ID");
        assertEquals("New Task", addedTodo.getTitle(), "The title should match the input");
        assertTrue(todoService.getAllTodos().contains(addedTodo), "The new todo should be in the list");
    }

    @Test
    void testUpdateTodo() {
        Todo originalTodo = todoService.getAllTodos().get(0);
        String originalId = originalTodo.getId();

        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Task");
        updatedTodo.setDescription("Updated description");


        todoService.updateTodo(originalId, updatedTodo);

        Todo foundTodo = todoService.getById(originalId);
        assertNotNull(foundTodo, "The todo should still exist after update");
        assertEquals("Updated Task", foundTodo.getTitle(), "The title should be updated");
        assertEquals("Updated description", foundTodo.getDescription(), "The description should be updated");
    }

    @Test
    void testUpdateTodoNotFound() {
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Non-existing Task");
        updatedTodo.setDescription("This todo does not exist");

        Todo result = todoService.updateTodo(UUID.randomUUID().toString(), updatedTodo);
        assertNull(result.getId(), "If todo does not exist, it should not be updated");
    }

    @Test
    void testDeleteTodo() {
        Todo todoToDelete = todoService.getAllTodos().get(0);
        String idToDelete = todoToDelete.getId();

        Todo deletedTodo = todoService.deleteTodo(idToDelete);

        assertNotNull(deletedTodo, "Deleted todo should not be null");
        assertEquals(idToDelete, deletedTodo.getId(), "Deleted todo ID should match the requested ID");
        assertFalse(todoService.getAllTodos().contains(deletedTodo), "Deleted todo should no longer be in the list");
    }

    @Test
    void testDeleteTodoNotFound() {
        Todo result = todoService.deleteTodo(UUID.randomUUID().toString());
        assertNull(result, "If todo does not exist, delete operation should return null");
    }

    @Test
    void testGetByTitleNotFound() {
        List<Todo> todosWithTitle = todoService.getByTitle("Non-existing title");
        assertTrue(todosWithTitle.isEmpty(), "If no todos match the title, the list should be empty");
    }
}
