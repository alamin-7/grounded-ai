package com.example.groundedai.service;

import com.example.groundedai.openai.OpenAiCompatibleClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChatService {
    private final OpenAiCompatibleClient client;

    public ChatService(OpenAiCompatibleClient client) {
        this.client = client;
    }
    public Mono<String> reply(String message) {
        return client.chat(message);
    }
}
