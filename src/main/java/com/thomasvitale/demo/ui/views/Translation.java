package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatTranslationService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Translation", order = 4)
@Route("/translation")
public class Translation extends Chatbot {

    public Translation(ChatTranslationService chatService) {
        super(chatService);
    }

}
