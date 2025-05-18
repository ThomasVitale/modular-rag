package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Chat", order = 1)
@Route("")
public class Chat extends Chatbot {

    public Chat(ChatService chatService) {
        super(chatService);
    }

}
