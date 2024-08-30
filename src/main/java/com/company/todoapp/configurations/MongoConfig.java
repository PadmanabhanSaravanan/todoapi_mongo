package com.company.todoapp.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "Todo";
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory mongoDbFactory, MongoCustomConversions mongoCustomConversions) {
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, mongoMappingContext());
        converter.setCustomConversions(mongoCustomConversions);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null)); // Disable _class field
        return converter;
    }

    @Bean
    public MongoMappingContext mongoMappingContext() {
        return new MongoMappingContext();
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017"); // Replace with your MongoDB URI
    }
}
