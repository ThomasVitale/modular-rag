package com.thomasvitale.demo.ai;

import com.thomasvitale.demo.components.CompressionDocumentPostProcessor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ChatPostProcessService implements AiService {

    private final String CONVERSATION_ID = UUID.randomUUID().toString();

    private final ChatClient chatClient;

    public ChatPostProcessService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .similarityThreshold(0.5)
                                .topK(3)
                                .build())
                        .documentPostProcessors(CompressionDocumentPostProcessor.builder()
                                .chatClientBuilder(chatClientBuilder.clone())
                                .build())
                        .build())
                .build();
    }

    @Override
    public Flux<String> chat(String input) {
        return chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, CONVERSATION_ID))
                .user(input)
                .stream()
                .content();
    }

}
