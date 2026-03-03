package com.example.groundedai.openai;

import com.example.groundedai.common.config.AiProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.client.WebClient.builder;

@Component
public class OpenAiCompatibleClient {
    private final WebClient webClient;
    private final  AiProperties aiProperties;

    public OpenAiCompatibleClient(WebClient.Builder webClient, AiProperties aiProperties) {
        this.aiProperties = aiProperties;

        this.webClient = webClient
                .baseUrl(aiProperties.baseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> chat(String userMessage) {
        // OpenAI-compatible payload (many providers follow this shape)
        Map<String, Object> body = Map.of(
                "model", aiProperties.model(),
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", userMessage)
                ),
                "temperature", 0.2
        );

        return webClient.post()
                .uri("/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + aiProperties.apiKey())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(aiProperties.timeoutSeconds()))
                .map(resp -> {
                    // Extract: choices[0].message.content
                    var choices = (List<Map<String, Object>>) resp.get("choices");
                    if (choices == null || choices.isEmpty()) return "";
                    var message = (Map<String, Object>) choices.get(0).get("message");
                    if (message == null) return "";
                    Object content = message.get("content");
                    return content == null ? "" : content.toString();
                });
    }
}

