package com.thomasvitale.demo.data;

import ai.docling.api.serve.DoclingServeApi;
import ai.docling.api.serve.convert.request.ConvertDocumentRequest;
import ai.docling.api.serve.convert.request.source.FileSource;
import ai.docling.api.serve.convert.response.ConvertDocumentResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class DoclingDocumentReader implements DocumentReader {

    private final DoclingServeApi doclingServeApi;
    private final Map<String, Object> metadata;
    private final Resource file;

    public DoclingDocumentReader(DoclingServeApi doclingServeApi, Map<String, Object> metadata, Resource file) {
        this.doclingServeApi = doclingServeApi;
        this.metadata = metadata;
        this.file = file;
    }

    @Override
    public List<Document> get() {
        String base64File;
        try {
            base64File = Base64.getEncoder().encodeToString(file.getContentAsByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConvertDocumentResponse response = doclingServeApi
                .convertSource(ConvertDocumentRequest.builder()
                        .source(FileSource.builder()
                                .filename(file.getFilename() != null ? file.getFilename() : "file")
                                .base64String(base64File)
                                .build())
                        .build());

        var mdFile = new ByteArrayResource(response.getDocument().getMarkdownContent().getBytes(StandardCharsets.UTF_8));

        return new MarkdownDocumentReader(mdFile, MarkdownDocumentReaderConfig.builder()
                .withAdditionalMetadata(metadata)
                .build()).get();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DoclingServeApi doclingServeApi;
        private Map<String, Object> metadata;
        private Resource file;

        private Builder() {}

        public Builder doclingServeApi(DoclingServeApi doclingServeApi) {
            this.doclingServeApi = doclingServeApi;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder file(Resource file) {
            this.file = file;
            return this;
        }

        public DoclingDocumentReader build() {
            return new DoclingDocumentReader(doclingServeApi, metadata, file);
        }

    }

}
