package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatSearchEngineService;
import com.thomasvitale.demo.ui.components.StreamingChatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Search Engine", order = 3)
@Route("/search-engine")
public class SearchEngine extends StreamingChatbot {

    public SearchEngine(ChatSearchEngineService chatService) {
        super(chatService);
    }

}
