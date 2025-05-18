package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatVectorStoreService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Vector Store", order = 3)
@Route("/vector-store")
public class VectorStore extends Chatbot {

    public VectorStore(ChatVectorStoreService chatService) {
        super(chatService);
    }

}
