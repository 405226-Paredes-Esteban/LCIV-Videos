package com.scaffold.template.clients.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PostDto(Long id, String title) {
}
