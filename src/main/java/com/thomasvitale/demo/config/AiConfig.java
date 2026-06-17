package com.thomasvitale.demo.config;

import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AiConfig {

    @Bean
    ChatClientBuilderCustomizer chatClientCustomizer(ChatMemory chatMemory) {
        return builder -> builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .build());
    }

}
