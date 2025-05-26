package com.thomasvitale.demo.ai;

import com.thomasvitale.demo.components.SearchEngineDocumentRetriever;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Tools {

    private final ChatClient.Builder chatClientBuilder;
    private final RestClient.Builder restClientBuilder;
    private final VectorStore vectorStore;

    Tools(ChatClient.Builder chatClientBuilder, RestClient.Builder restClientBuilder, VectorStore vectorStore) {
        this.chatClientBuilder = chatClientBuilder;
        this.restClientBuilder = restClientBuilder;
        this.vectorStore = vectorStore;
    }

    @Tool(description = "Retrieve information about stories taking place in the world of Iorek and Pingu")
    @Nullable
    String iorekPinguRetriever(String query) {
        return chatClientBuilder.clone().build().prompt()
                .advisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(VectorStoreDocumentRetriever.builder()
                                .filterExpression(new FilterExpressionBuilder().eq("location", "North Pole").build())
                                .vectorStore(vectorStore)
                                .similarityThreshold(0.5)
                                .topK(3)
                                .build())
                        .build())
                .user(query)
                .call()
                .content();
    }

    @Tool(description = "Retrieve information about stories taking place in the world of Lucio and Balosso")
    @Nullable
    String lucioBalossoRetriever(String query) {
        return chatClientBuilder.clone().build().prompt()
                .advisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(VectorStoreDocumentRetriever.builder()
                                .filterExpression(new FilterExpressionBuilder().eq("location", "Italy").build())
                                .vectorStore(vectorStore)
                                .similarityThreshold(0.5)
                                .topK(3)
                                .build())
                        .build())
                .user(query)
                .call()
                .content();
    }

    @Tool(description = "Retrieve information by searching the web")
    @Nullable
    String webSearchRetriever(String query) {
        return chatClientBuilder.clone().build().prompt()
                .advisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(SearchEngineDocumentRetriever.builder()
                                .restClientBuilder(restClientBuilder)
                                .maxResults(10)
                                .build())
                        .build())
                .user(query)
                .call()
                .content();
    }

}
