package ru.nspk.webflux.base;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

public class BaseContainerTest {
    private static final MySQLContainer<?> MYSQL_CONTAINER =
            new MySQLContainer<>("mysql:5.6").withInitScript("schema.sql");

    static {
        MYSQL_CONTAINER.start();

        var containerDelegate = new JdbcDatabaseDelegate(MYSQL_CONTAINER, "");
        ScriptUtils.runInitScript(containerDelegate, "data.sql");
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.r2dbc.url",
                () ->
                        "r2dbc:mysql://"
                                + MYSQL_CONTAINER.getHost()
                                + ":"
                                + MYSQL_CONTAINER.getFirstMappedPort()
                                + "/"
                                + MYSQL_CONTAINER.getDatabaseName());
        registry.add("spring.r2dbc.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.r2dbc.password", MYSQL_CONTAINER::getPassword);
    }
}
