package com.example.groundedai.openai;

import com.example.groundedai.common.config.AiProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Component
public class OllamaClient {
    private final WebClient webClient;
    private final AiProperties aiProperties;

    public OllamaClient(WebClient.Builder builder, AiProperties aiProperties) {
        this.webClient = builder.baseUrl(aiProperties.baseUrl()).build();
        this.aiProperties = aiProperties;
    }

    public Mono<String> generate(String prompt) {

        Map<String, Object> body = Map.of(
                "model", aiProperties.model(),
                "prompt", prompt,
                "stream", false
        );

        return webClient.post()
                .uri("/api/generate")
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .map(error -> new RuntimeException("Ollama error: " + error))
                )
                .bodyToMono(Map.class)
                .map(resp -> resp.get("response").toString());
    }
}
