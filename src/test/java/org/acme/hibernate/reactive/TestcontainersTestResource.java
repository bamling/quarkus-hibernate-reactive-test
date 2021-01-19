package org.acme.hibernate.reactive;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.HashMap;
import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;

@SuppressWarnings("rawtypes")
public class TestcontainersTestResource implements QuarkusTestResourceLifecycleManager {

    private static final String POSTGRES_IMAGE = "postgres:11.6-alpine";

    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(POSTGRES_IMAGE);

    @Override
    public Map<String, String> start() {
        postgreSQLContainer.start();

        return new HashMap<>() {{
            put("quarkus.datasource.username", postgreSQLContainer.getUsername());
            put("quarkus.datasource.password", postgreSQLContainer.getPassword());
            put("quarkus.datasource.reactive.url", getReactiveUrl());
        }};
    }

    @Override
    public void stop() {
        postgreSQLContainer.stop();
    }

    private String getReactiveUrl() {
        return postgreSQLContainer.getJdbcUrl().replace("jdbc", "vertx-reactive");
    }

}
