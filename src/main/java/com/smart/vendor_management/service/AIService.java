package com.smart.vendor_management.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getRecommendation(String productName,
                                    int currentStock,
                                    int predictedDemand) {

        String prompt = """
                You are an inventory management assistant.

                Product: %s
                Current Stock: %d
                Predicted Demand for next 7 days: %d

                Analyze this inventory situation and provide a short
                recommendation about whether the business should reorder
                the product.

                Explain the reason in simple language.
                """.formatted(
                productName,
                currentStock,
                predictedDemand
        );

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}