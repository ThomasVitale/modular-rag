package com.thomasvitale.demo.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.join.DocumentJoiner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConcatenationDocumentJoiner implements DocumentJoiner {

    private static final Logger logger = LoggerFactory.getLogger(org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner.class);

    @Override
    public List<Document> join(Map<Query, List<List<Document>>> documentsForQuery) {
        Assert.notNull(documentsForQuery, "documentsForQuery cannot be null");
        Assert.noNullElements(documentsForQuery.keySet(), "documentsForQuery cannot contain null keys");
        Assert.noNullElements(documentsForQuery.values(), "documentsForQuery cannot contain null values");

        logger.debug("Joining documents by concatenation");

        return new ArrayList<>(documentsForQuery.values()
                .stream()
                .flatMap(List::stream)
                .flatMap(List::stream)
                .collect(Collectors.toMap(Document::getId, Function.identity(), (existing, duplicate) -> existing))
                .values()
                .stream()
                .sorted(Comparator.comparingDouble((Document doc) -> doc.getScore() != null ? doc.getScore() : 0.0).reversed())
                .toList()
        );
    }

}
