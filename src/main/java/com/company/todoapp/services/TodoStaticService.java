package com.company.todoapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.todoapp.models.Todo;

public class TodoStaticService implements ITodoService {

	// Static data store
	private static List<Todo> todos = new ArrayList<>();

	static {
		todos.add(new Todo(java.util.UUID.randomUUID().toString(), "Learn Java", "From Scratch", false, new Date()));
		todos.add(new Todo(java.util.UUID.randomUUID().toString(), "Learn Spring", "From Scratch", false, new Date()));
		todos.add(new Todo(java.util.UUID.randomUUID().toString(), "Learn Hibernate", "From Scratch", false, new Date()));
	}

	@Override
	public List<Todo> getAllTodos() {
		return todos;
	}

	@Override
	public Todo getById(String id) { // Changed to String
		for (Todo todo : todos) {
			if (todo.getId().equals(id)) {
				return todo;
			}
		}
		return null;
	}

	@Override
	public Todo addTodo(Todo todo) {
		todo.setId(java.util.UUID.randomUUID().toString()); // Generate a new UUID as a String
		todos.add(todo);
		return todo;
	}

	@Override
	public Todo updateTodo(String id, Todo todo) { // Changed to String
		Todo oldTodo = getById(id);
		if (oldTodo != null) {
			todos.remove(oldTodo);
			todo.setId(id); // Retain the original UUID as a String
			todos.add(todo);
		}
		return todo;
	}

	@Override
	public Todo deleteTodo(String id) { // Changed to String
		for (Todo todo : todos) {
			if (todo.getId().equals(id)) {
				todos.remove(todo);
				return todo;
			}
		}
		return null;
	}

	@Override
	public List<Todo> getByTitle(String title) {
		List<Todo> result = new ArrayList<>();
		for (Todo todo : todos) {
			if (todo.getTitle().equalsIgnoreCase(title)) {
				result.add(todo);
			}
		}
		return result;
	}
}
