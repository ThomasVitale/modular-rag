package com.thomasvitale.demo.data;

import ai.docling.serve.api.DoclingServeApi;
import io.arconia.ai.document.docling.DoclingDocumentReader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
class DoclingIngestionPipeline {

    private static final Logger logger = LoggerFactory.getLogger(DoclingIngestionPipeline.class);

    private final DoclingServeApi doclingServeApi;

    private final VectorStore vectorStore;

    @Value("classpath:documents/story1.md")
    Resource file1;

    @Value("classpath:documents/story2.pdf")
    Resource file2;

    DoclingIngestionPipeline(DoclingServeApi doclingServeApi, VectorStore vectorStore) {
        this.doclingServeApi = doclingServeApi;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    void run() {
        vectorStore.delete(new FilterExpressionBuilder().eq("demo", "true").build());

        logger.info("Parsing and chunking files as Documents with Docling");

        List<Document> storyDocuments = DoclingDocumentReader.builder()
                .doclingServeApi(doclingServeApi)
                .files(file1, file2)
                .metadata(Map.of(
                        "demo", "true",
                        "topic", "story"
                ))
                .build()
                .get();

        List<Document> arconiaDocuments = DoclingDocumentReader.builder()
                .doclingServeApi(doclingServeApi)
                .urls(
                        "https://www.thomasvitale.com/rag-docling-java-spring-ai/",
                        "https://www.thomasvitale.com/ai-document-processing-docling-java-arconia-spring-boot/"
                )
                .metadata(Map.of(
                        "demo", "true",
                        "topic", "arconia"
                ))
                .build()
                .get();

        logger.info("Creating and storing Embeddings from Documents");

        List<Document> documents = Stream.concat(storyDocuments.stream(), arconiaDocuments.stream()).toList();
        vectorStore.add(documents);
    }

}
