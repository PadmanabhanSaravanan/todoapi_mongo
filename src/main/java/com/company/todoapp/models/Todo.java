package com.company.todoapp.models;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.RepresentationModel;

@Document(collection = "todos")
@TypeAlias("Todo")
public class Todo {

	@Id
	private String id; // Changed to String

	@Field("title")
	private String title;

	@Field("description")
	private String description;

	@Field("status")
	private boolean status;

	@Field("targetDate")
	private Date targetDate;

	// Constructors
	public Todo() {
		super();
	}

	public Todo(String id, String title, String description, boolean status, Date targetDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.targetDate = targetDate;
	}

	// Getters and Setters
	public String getId() {
		return id; // Changed to String
	}

	public void setId(String id) {
		this.id = id; // Changed to String
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	// hashCode, equals, and toString methods
	@Override
	public int hashCode() {
		return Objects.hash(description, id, status, targetDate, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& status == other.status && Objects.equals(targetDate, other.targetDate)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", targetDate=" + targetDate + "]";
	}
}
