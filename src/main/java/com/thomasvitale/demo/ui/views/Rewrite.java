package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatRewriteService;
import com.thomasvitale.demo.ui.components.StreamingChatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Rewrite", order = 6)
@Route("/rewrite")
public class Rewrite extends StreamingChatbot {

    public Rewrite(ChatRewriteService chatService) {
        super(chatService);
    }

}
