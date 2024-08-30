package com.company.todoapp.serializers;

import com.company.todoapp.models.Todo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TodoSerializer extends StdSerializer<Todo> {

    public TodoSerializer() {
        this(null);
    }

    public TodoSerializer(Class<Todo> t) {
        super(t);
    }

    @Override
    public void serialize(Todo todo, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", todo.getId());
        gen.writeStringField("title", todo.getTitle());
        gen.writeStringField("description", todo.getDescription());
        gen.writeBooleanField("status", todo.isStatus());
        gen.writeObjectField("targetDate", todo.getTargetDate());
        gen.writeEndObject();
    }
}
