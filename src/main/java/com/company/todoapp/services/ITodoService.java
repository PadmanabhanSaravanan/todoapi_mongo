package com.company.todoapp.services;

import java.util.List;

import com.company.todoapp.models.Todo;

public interface ITodoService {

	public List<Todo> getAllTodos();

	public Todo getById(String id); // Changed to String

	public List<Todo> getByTitle(String title);

	public Todo addTodo(Todo todo);

	public Todo updateTodo(String id, Todo todo); // Changed to String

	public Todo deleteTodo(String id); // Changed to String
}
