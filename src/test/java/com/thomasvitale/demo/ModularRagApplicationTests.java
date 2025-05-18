package com.thomasvitale.demo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ModularRagApplicationTests {

	@Autowired
	ChatClient.Builder chatClientBuilder;

	@Autowired
	VectorStore vectorStore;

	@Test
	void evaluateRelevancy() {
		String question = "What is Iorek's biggest dream?";

		RetrievalAugmentationAdvisor ragAdvisor = RetrievalAugmentationAdvisor.builder()
				.documentRetriever(VectorStoreDocumentRetriever.builder()
						.vectorStore(vectorStore)
						.similarityThreshold(0.5)
						.topK(3)
						.build())
				.build();

		ChatResponse chatResponse = chatClientBuilder.build().prompt()
				.advisors(ragAdvisor)
				.user(question)
				.call()
				.chatResponse();

		assertThat(chatResponse).isNotNull();

		EvaluationRequest evaluationRequest = new EvaluationRequest(question,
				chatResponse.getMetadata().get(RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT),
				chatResponse.getResult().getOutput().getText());

		RelevancyEvaluator evaluator = new RelevancyEvaluator(chatClientBuilder.clone());

		EvaluationResponse evaluationResponse = evaluator.evaluate(evaluationRequest);

		assertThat(evaluationResponse.isPass()).isTrue();
	}

}
