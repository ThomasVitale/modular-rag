package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatService;
import com.thomasvitale.demo.ui.components.StreamingChatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Chat", order = 2)
@Route("/chat")
public class Chat extends StreamingChatbot {

    public Chat(ChatService chatService) {
        super(chatService);
    }

}
