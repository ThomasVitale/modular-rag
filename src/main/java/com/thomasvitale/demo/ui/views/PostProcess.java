package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.ChatPostProcessService;
import com.thomasvitale.demo.ui.components.StreamingChatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Post-Process", order = 7)
@Route("/post-process")
public class PostProcess extends StreamingChatbot {

    public PostProcess(ChatPostProcessService chatService) {
        super(chatService);
    }

}
