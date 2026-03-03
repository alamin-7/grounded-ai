package com.example.groundedai.controller;

import com.example.groundedai.payload.request.ChatRequest;
import com.example.groundedai.payload.response.ChatResponse;
import com.example.groundedai.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ChatResponse> chat(@Valid @RequestBody ChatRequest req) {
        return service.reply(req.message())
                .map(ChatResponse::new);
    }

}
