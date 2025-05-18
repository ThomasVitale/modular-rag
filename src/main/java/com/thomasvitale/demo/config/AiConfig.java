package com.thomasvitale.demo.config;

import org.springframework.ai.chat.client.ChatClientCustomizer;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AiConfig {

    @Bean
    ChatClientCustomizer chatClientCustomizer(ChatMemory chatMemory) {
        return builder -> builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(UUID.randomUUID().toString())
                        .build());
    }

}
