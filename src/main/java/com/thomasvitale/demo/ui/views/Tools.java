package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatToolService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Tools", order = 10)
@Route("/tools")
public class Tools extends Chatbot {

    public Tools(ChatToolService aiService) {
        super(aiService);
    }

}
