package com.company.todoapp.dtos;

import java.util.Date;
import java.util.Objects;

public class TodoResponseDTO {
    private String id;
    private String title;
    private String description;
    private boolean status;
    private Date targetDate;

    // Default constructor
    public TodoResponseDTO() {
    }

    // Parameterized constructor
    public TodoResponseDTO(String id, String title, String description, boolean status, Date targetDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.targetDate = targetDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    // toString method
    @Override
    public String toString() {
        return "TodoResponseDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", targetDate=" + targetDate +
                '}';
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoResponseDTO that = (TodoResponseDTO) o;
        return status == that.status &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(targetDate, that.targetDate);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, targetDate);
    }
}
