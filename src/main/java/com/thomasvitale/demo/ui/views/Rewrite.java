package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatRewriteService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Rewrite", order = 5)
@Route("/rewrite")
public class Rewrite extends Chatbot {

    public Rewrite(ChatRewriteService chatService) {
        super(chatService);
    }

}
