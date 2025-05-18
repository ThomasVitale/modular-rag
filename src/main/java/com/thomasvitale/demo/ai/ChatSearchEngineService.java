package com.thomasvitale.demo.ai;

import com.thomasvitale.demo.components.SearchEngineDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ChatSearchEngineService implements AiService {

    private final String CONVERSATION_ID = UUID.randomUUID().toString();

    private final ChatClient chatClient;

    public ChatSearchEngineService(ChatClient.Builder chatClientBuilder, RestClient.Builder restClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .queryTransformers(RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClientBuilder.clone())
                                .targetSearchSystem("web search engine")
                                .build())
                        .documentRetriever(SearchEngineDocumentRetriever.builder()
                                .restClientBuilder(restClientBuilder)
                                .maxResults(10)
                                .build())
                        .queryAugmenter(ContextualQueryAugmenter.builder()
                                .allowEmptyContext(false)
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
