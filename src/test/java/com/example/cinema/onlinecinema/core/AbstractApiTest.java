package com.example.cinema.onlinecinema.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.jpa.properties.hibernate.hbm2ddl.auto=none"})
public abstract class AbstractApiTest extends AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

}
