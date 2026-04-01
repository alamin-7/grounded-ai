package com.example.groundedai.service;

import com.example.groundedai.openai.OllamaClient;
import com.example.groundedai.openai.OpenAiCompatibleClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChatService {
    private final OpenAiCompatibleClient client;
    private final OllamaClient ollamaClient;

    public ChatService(OpenAiCompatibleClient client, OllamaClient ollamaClient) {
        this.client = client;
        this.ollamaClient = ollamaClient;
    }
    public Mono<String> reply(String message) {
        return client.chat(message);
    }
    public Mono<String> replyWithOllama(String message) {
        return ollamaClient.generate(message);
    }
}
