package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatSearchEngineService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Search Engine", order = 2)
@Route("/search-engine")
public class SearchEngine extends Chatbot {

    public SearchEngine(ChatSearchEngineService chatService) {
        super(chatService);
    }

}
