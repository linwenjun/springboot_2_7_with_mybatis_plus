package com.example.demo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@SpringBootTest
@Slf4j
class DemoApplicationTest {

    TestThing tt;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void should_greeting() {
        RestAssuredMockMvc
                .given()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    void should_add_user() {
        File content = new File("src/test/resources/fixtures/create-user.json");
        RestAssuredMockMvc
                .given()
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(content)
                .post("/users")
                .then()
                .statusCode(200);
    }

    @Test
    void parallel() {
        List<String> debugList = new ArrayList<>();

        List<Map<String, Integer>> values = List.of(
                Map.of("a", 1),
                Map.of("a", 2),
                Map.of("a", 3)
        );

        List<Map<String, Integer>> result = values.stream().parallel().peek(e -> {
            debugList.add(e.toString());
            log.trace(Thread.currentThread().getName());
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }).collect(Collectors.toList());
    }

    @Test
    void testOptional() {
tt = new TestThing();
        Optional<String> s = Optional.ofNullable(tt)
                .map(a -> a.name(1))
                .filter(item -> "CAT".equals(item));

        log.trace("result : " +  s.isPresent());
    }



    public static class TestThing {
        String name(int i) {
            if(i < 0) {
                return "DOG";
            }
            return "CAT";
        }
    }
}