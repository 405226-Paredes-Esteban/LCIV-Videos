package com.scaffold.template.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientConfig {
    private static final int TIME_OUT = 1000;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //return new RestTemplate();
        return builder
                .setConnectTimeout(Duration.ofMillis(TIME_OUT))
                .setReadTimeout(Duration.ofMillis(TIME_OUT))
                .build();
    }

    @Bean
    public WebClient webClient() {
        //Importamos el HttpClient de Netty para darle la info del timeout que querramos utilizar
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIME_OUT)
                .responseTimeout(Duration.ofMillis(TIME_OUT))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIME_OUT, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(TIME_OUT, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .baseUrl("https://my-json-server.typicode.com//TUP-UTN-FRC-LCIII/fake-apis")
                .defaultCookie("myCookieKey","myCookieKeyValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
