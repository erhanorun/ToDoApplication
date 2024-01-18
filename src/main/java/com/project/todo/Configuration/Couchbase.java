package com.project.todo.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import static com.project.todo.Configuration.CouchbaseConstants.*;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.project.todo"})
public class Couchbase extends AbstractCouchbaseConfiguration {
    @Override
    public String getConnectionString() {
        return NODE_LIST;
    }

    @Override
    public String getUserName() {
        return BUCKET_USERNAME;
    }

    @Override
    public String getPassword() {
        return BUCKET_PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
