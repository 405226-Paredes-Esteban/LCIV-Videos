package com.scaffold.template.clients.posts;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostRestClient {
    private static final String RESILIENCE4J_INSTANCE_NAME = "postCircuitBreaker";
    private static final String FALLBACK_METHOD = "fallback";

    @Autowired
    RestTemplate restTemplate;
    String baseUrl = "https://my-json-server.typicode.com/TUP-UTN-FRC-LCIII/fake-apis/posts/";

    public ResponseEntity<PostDto[]> getPosts(){
        return restTemplate.getForEntity(baseUrl, PostDto[].class);
    }

    /*public ResponseEntity<PostDto> getPost(Long id){
        return restTemplate.getForEntity(baseUrl +"/"+ id, PostDto.class);
    }*/

    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<PostDto> getPost(Long id){
       return restTemplate.getForEntity(baseUrl + id, PostDto.class);
    }

    public ResponseEntity<String> fallback(Exception ex){
        return ResponseEntity.status(503).body("Response from Circuit Breaker: "+ ex.getMessage());
    }
}
