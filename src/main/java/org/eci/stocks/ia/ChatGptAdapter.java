package org.eci.stocks.ia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class ChatGptAdapter implements IAAdapter {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${api.chatgpt.key}")
    private String apiKey;

    @Value("${api.chatgpt.url}")
    private String apiUrl;

    public ChatGptAdapter(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    private String buildRequestPayload(String input) throws JsonProcessingException {
        // Estructura el JSON para el endpoint /v1/chat/completions
        Map<String, Object> payload = Map.of(
                "model", "gpt-4o",
                "messages", List.of(
                        Map.of("role", "user", "content", input)
                ),
                "max_tokens", 1000,
                "temperature", 0.7
        );

        return objectMapper.writeValueAsString(payload);
    }

    @Override
    public String generateResponse(String input) throws JsonProcessingException {
        System.out.println("OpenIA");
        String requestBody = buildRequestPayload(input);

        String responseIa = webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> {
                        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return (String) message.get("content");
                    })
                    .block();

        return responseIa;
    }

    @Override
    public String getStatus() {
        return "general";
    }

}