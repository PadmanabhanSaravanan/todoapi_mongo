package com.company.todoapp;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

import com.company.todoapp.models.Todo;
import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void testDefaultConstructor() {
        Todo todo = new Todo();
        assertNotNull(todo);
        assertNull(todo.getId());
        assertNull(todo.getTitle());
        assertNull(todo.getDescription());
        assertFalse(todo.isStatus());
        assertNull(todo.getTargetDate());
    }

    @Test
    public void testParameterizedConstructor() {
        Date date = new Date();
        Todo todo = new Todo("1", "Test Title", "Test Description", true, date);

        assertEquals("1", todo.getId());
        assertEquals("Test Title", todo.getTitle());
        assertEquals("Test Description", todo.getDescription());
        assertTrue(todo.isStatus());
        assertEquals(date, todo.getTargetDate());
    }

    @Test
    public void testGettersAndSetters() {
        Todo todo = new Todo();
        Date date = new Date();

        todo.setId("2");
        todo.setTitle("New Title");
        todo.setDescription("New Description");
        todo.setStatus(false);
        todo.setTargetDate(date);

        assertEquals("2", todo.getId());
        assertEquals("New Title", todo.getTitle());
        assertEquals("New Description", todo.getDescription());
        assertFalse(todo.isStatus());
        assertEquals(date, todo.getTargetDate());
    }

    @Test
    public void testHashCode() {
        Date date = new Date();
        Todo todo1 = new Todo("1", "Title", "Description", true, date);
        Todo todo2 = new Todo("1", "Title", "Description", true, date);

        assertEquals(todo1.hashCode(), todo2.hashCode());
    }

    @Test
    public void testEquals() {
        Date date = new Date();
        Todo todo1 = new Todo("1", "Title", "Description", true, date);
        Todo todo2 = new Todo("1", "Title", "Description", true, date);
        Todo todo3 = new Todo("2", "Different Title", "Different Description", false, date);

        assertTrue(todo1.equals(todo2));
        assertFalse(todo1.equals(todo3));
        assertFalse(todo1.equals(null));
        assertFalse(todo1.equals(new Object()));
    }

    @Test
    public void testToString() {
        Date date = new Date();
        Todo todo = new Todo("1", "Title", "Description", true, date);

        String expectedToString = "Todo [id=1, title=Title, description=Description, status=true, targetDate=" + date + "]";
        assertEquals(expectedToString, todo.toString());
    }
}
