package com.company.todoapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.todoapp.dtos.TodoResponseDTO;
import com.company.todoapp.exceptions.TodoNotFoundException;
import com.company.todoapp.models.Todo;
import com.company.todoapp.services.ITodoService;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

	@Autowired
	ITodoService todoSvc;

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

	// Utility method to convert List<Todo> to List<TodoResponseDTO>
	private List<TodoResponseDTO> convertToDTOList(List<Todo> todos) {
		return todos.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@GetMapping
	public ResponseEntity<List<TodoResponseDTO>> getAllTodos() {
		List<Todo> todos = todoSvc.getAllTodos();
		if (todos.isEmpty()) {
			throw new TodoNotFoundException("Todo Not Found");
		}
		return new ResponseEntity<>(convertToDTOList(todos), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TodoResponseDTO> getByTodoId(@PathVariable String id) {
		Todo todo = todoSvc.getById(id);
		if (todo == null) {
			throw new TodoNotFoundException("Todo Not Found");
		}
		return new ResponseEntity<>(convertToDTO(todo), HttpStatus.OK);
	}

	@GetMapping("/title/{title}")
	public ResponseEntity<List<TodoResponseDTO>> getByTodoTitle(@PathVariable String title) {
		List<Todo> todos = todoSvc.getByTitle(title);
		if (todos == null || todos.isEmpty()) {
			throw new TodoNotFoundException("Todo Not Found");
		}
		return new ResponseEntity<>(convertToDTOList(todos), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<TodoResponseDTO> createTodo(@RequestBody Todo todo) {
		Todo newTodo = todoSvc.addTodo(todo);
		return new ResponseEntity<>(convertToDTO(newTodo), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable String id, @RequestBody Todo todo) {
		Todo updatedTodo = todoSvc.updateTodo(id, todo);
		if (updatedTodo != null) {
			return new ResponseEntity<>(convertToDTO(updatedTodo), HttpStatus.OK);
		} else {
			throw new TodoNotFoundException("Todo Not Found");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<TodoResponseDTO> deleteTodo(@PathVariable String id) {
		Todo todo = todoSvc.deleteTodo(id);
		if (todo != null) {
			return new ResponseEntity<>(convertToDTO(todo), HttpStatus.OK);
		} else {
			throw new TodoNotFoundException("Todo Not Found");
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
