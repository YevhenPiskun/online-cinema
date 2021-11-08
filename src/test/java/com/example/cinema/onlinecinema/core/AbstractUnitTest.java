package com.example.cinema.onlinecinema.core;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {"spring.jpa.properties.hibernate.hbm2ddl.auto=none", "spring.jpa.properties.hibernate.hbm2ddl.import_files="})
public abstract class AbstractUnitTest extends AbstractTest {
}
