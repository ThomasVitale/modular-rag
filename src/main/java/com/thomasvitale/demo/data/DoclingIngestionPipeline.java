package com.thomasvitale.demo.data;

import ai.docling.api.serve.DoclingServeApi;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        List<Document> documents = new ArrayList<>();

        logger.info("Loading files as Documents with Docling");
        var doclingReader1 = DoclingDocumentReader.builder()
            .file(file1)
            .doclingServeApi(doclingServeApi)
            .metadata(Map.of("location", "North Pole"))
            .build();
        documents.addAll(doclingReader1.get());

        var doclingReader2 = DoclingDocumentReader.builder()
            .file(file2)
            .doclingServeApi(doclingServeApi)
            .metadata(Map.of("location", "Italy"))
            .build();
        documents.addAll(doclingReader2.get());

        logger.info("Splitting Documents into chunks");
        var tokenTextSplitter = TokenTextSplitter.builder().build();
        documents = tokenTextSplitter.split(documents);

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

}
