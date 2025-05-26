package com.thomasvitale.demo.data;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class IngestionPipeline {

    private static final Logger logger = LoggerFactory.getLogger(IngestionPipeline.class);

    private final VectorStore vectorStore;

    @Value("classpath:documents/story1.md")
    Resource file1;

    @Value("classpath:documents/story2.md")
    Resource file2;

    IngestionPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    void run() {
        vectorStore.delete(new FilterExpressionBuilder().eq("demo", "true").build());

        List<Document> documents = new ArrayList<>();

        logger.info("Loading .md files as Documents");
        var markdownReader1 = new MarkdownDocumentReader(file1, MarkdownDocumentReaderConfig.builder()
                .withAdditionalMetadata("location", "North Pole")
                .withAdditionalMetadata("demo", "true")
                .build());
        documents.addAll(markdownReader1.get());
        var markdownReader2 = new MarkdownDocumentReader(file2, MarkdownDocumentReaderConfig.builder()
                .withAdditionalMetadata("location", "Italy")
                .withAdditionalMetadata("demo", "true")
                .build());
        documents.addAll(markdownReader2.get());

        logger.info("Splitting Documents into chunks");
        var tokenTextSplitter = TokenTextSplitter.builder().build();
        documents = tokenTextSplitter.split(documents);

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

}
