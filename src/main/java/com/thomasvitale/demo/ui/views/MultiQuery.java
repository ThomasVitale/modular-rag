package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatMultiQueryService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Multi-Query", order = 7)
@Route("/multi-query")
public class MultiQuery extends Chatbot {

    public MultiQuery(ChatMultiQueryService chatService) {
        super(chatService);
    }

}
