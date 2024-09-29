package com.scaffold.template.clients.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProfileRestClient {
    @Autowired
    private WebClient webClient;

    String baseResourceUrl = "https://my-json-server.typicode.com/TUP-UTN-FRC-LCIII/fake-apis/profile/";

    //Prog. Reactiva tiene Mono y Flux
    //Hay paralelismo en prog reactiva por lo que si necesitamos ejecutar dos metodos que no dependen entre si se puede
    //En programación Reactiva:
    //Mono es cuando necesitamos capturar un elemento
    //Flux es para muchos elementos
    public Mono<ResponseEntity<Profile>> getProfile() {
        return this.webClient
                .get()
                .uri(baseResourceUrl)
                .retrieve()
                .toEntity(Profile.class);
    }
}
