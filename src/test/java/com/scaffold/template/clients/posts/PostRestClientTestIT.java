package com.scaffold.template.clients.posts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRestClientTestIT {
    //NO HACEMOS MOCK PORQUE ES TEST DE INTEGRACIÓN
    @Autowired
    private PostRestClient postRestClient;
    //Test de integración: Puede fallar porque no da
    // resultado el assert, dependemos de terceros
    @Test
    void getPostsIntegrationTest(){
        ResponseEntity<PostDto[]> result = postRestClient.getPosts();
        assertNotNull(result);
        assertEquals(3, Objects.requireNonNull(result.getBody()).length);
    }
}