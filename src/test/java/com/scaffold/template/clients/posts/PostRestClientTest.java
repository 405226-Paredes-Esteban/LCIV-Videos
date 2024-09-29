package com.scaffold.template.clients.posts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostRestClientTest {
    @MockBean
    private RestTemplate restTemplate;

    //Lo levantamos como Spy para poder usar los metodos como estan definidos
    @SpyBean
    private PostRestClient postRestClient;

    @Test
    void getPostsTest() {
        PostDto postDto = new PostDto(10L, "Test 1");
        PostDto postDto2 = new PostDto(20L, "Test 2");
        PostDto[] arrayPosts = {postDto, postDto2};
        when(restTemplate.getForEntity("https://my-json-server.typicode.com/TUP-UTN-FRC-LCIII/fake-apis/posts/", PostDto[].class))
                .thenReturn(ResponseEntity.ok(arrayPosts));
        ResponseEntity<PostDto[]> result = postRestClient.getPosts();
        assertEquals(2, Objects.requireNonNull(result.getBody()).length);
        assertEquals(postDto.id(), result.getBody()[0].id());
        assertEquals(postDto2.id(), result.getBody()[1].id());
    }

    @Test
    void getPostTest() {
        PostDto postDto = new PostDto(10L, "Test 1");
        when(restTemplate.getForEntity("https://my-json-server.typicode.com/TUP-UTN-FRC-LCIII/fake-apis/posts/",PostDto.class))
            .thenReturn(ResponseEntity.ok(postDto));
        ResponseEntity<PostDto> result = postRestClient.getPost(10L);
        assertEquals(10L, Objects.requireNonNull(result.getBody().id()));
        assertEquals("Test 1", result.getBody().title());
    }
}