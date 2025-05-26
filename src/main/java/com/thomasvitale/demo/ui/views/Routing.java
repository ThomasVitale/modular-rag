package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatRoutingService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Routing", order = 9)
@Route("/routing")
public class Routing extends Chatbot {

    public Routing(ChatRoutingService aiService) {
        super(aiService);
    }

}
