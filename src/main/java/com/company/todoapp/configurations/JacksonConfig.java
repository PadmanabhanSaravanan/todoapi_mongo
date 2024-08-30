package com.company.todoapp.configurations;

import com.company.todoapp.models.Todo;
import com.company.todoapp.serializers.TodoSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Todo.class, new TodoSerializer());
        mapper.registerModule(module);
        return mapper;
    }
}